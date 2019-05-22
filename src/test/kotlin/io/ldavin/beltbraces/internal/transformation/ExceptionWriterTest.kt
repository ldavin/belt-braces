package io.ldavin.beltbraces.internal.transformation

import com.googlecode.catchexception.CatchException.catchException
import com.nhaarman.mockito_kotlin.given
import io.ldavin.beltbraces.caughtException
import io.ldavin.beltbraces.exception.NoAssertionFoundException
import io.ldavin.beltbraces.internal.TestFixtures.analysis
import io.ldavin.beltbraces.internal.TestFixtures.constructor
import io.ldavin.beltbraces.internal.TestFixtures.emptyAnalysis
import io.ldavin.beltbraces.internal.TestFixtures.property
import io.ldavin.beltbraces.internal.transformation.ExceptionWriter.Companion.DOES_NOT_OVERRIDE_EQUALS
import io.ldavin.beltbraces.internal.transformation.ExceptionWriter.Companion.LOOKS_LIKE_DATA_CLASS
import io.ldavin.beltbraces.internal.transformation.ExceptionWriter.Companion.OBJECT_EQUALITY_ASSERTION
import io.ldavin.beltbraces.internal.transformation.ExceptionWriter.Companion.PROPERTY_ASSERTIONS_FALLBACK
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class ExceptionWriterTest {

    @Mock
    internal lateinit var propertyWriter: PropertyWriter
    @Mock
    internal lateinit var constructorWriter: ConstructorWriter
    @InjectMocks
    internal lateinit var writer: ExceptionWriter

    @Test
    fun `transformToMessage throws an exception if no properties are found`() {
        // GIVEN
        val analysis = emptyAnalysis()

        // WHEN
        catchException { writer.transformToMessage(analysis) }

        // THEN
        assertThat(caughtException()).isExactlyInstanceOf(NoAssertionFoundException::class.java)
    }

    @Test
    fun `transformToMessage a property-only analysis`() {
        // GIVEN
        val property1 = property(name = "1")
        val property2 = property(name = "2")
        val analysis = analysis(listOf(property1, property2), overrideEquals = false, isKotlinDataClass = false)

        given(propertyWriter.transform(property1)).willReturn("assertion1")
        given(propertyWriter.transform(property2)).willReturn("assertion2")

        // WHEN
        val result = writer.transformToMessage(analysis)

        // THEN
        val expectedMessage = """
            $DOES_NOT_OVERRIDE_EQUALS
            assertion1
            assertion2""".trimIndent()

        assertThat(result).isEqualTo(expectedMessage)
    }

    @Test
    fun `transformToMessage a kotlin data-class`() {
        // GIVEN
        val property1 = property(name = "1")
        val property2 = property(name = "2")
        val constructor = constructor()
        val analysis = analysis(
            listOf(property1, property2),
            preferredConstructor = constructor,
            overrideEquals = true,
            isKotlinDataClass = true
        )

        given(propertyWriter.transform(property1)).willReturn("assertion1")
        given(propertyWriter.transform(property2)).willReturn("assertion2")
        given(constructorWriter.transform(constructor)).willReturn("object equality")

        // WHEN
        val result = writer.transformToMessage(analysis)

        // THEN
        val expectedMessage = """
            $LOOKS_LIKE_DATA_CLASS
            $OBJECT_EQUALITY_ASSERTION
            object equality

            $PROPERTY_ASSERTIONS_FALLBACK
            assertion1
            assertion2""".trimIndent()

        assertThat(result).isEqualTo(expectedMessage)
    }
}