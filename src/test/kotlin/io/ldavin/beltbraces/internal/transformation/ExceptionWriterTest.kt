package io.ldavin.beltbraces.internal.transformation

import com.googlecode.catchexception.CatchException.catchException
import com.nhaarman.mockito_kotlin.given
import io.ldavin.beltbraces.caughtException
import io.ldavin.beltbraces.exception.FastenYourSeatBeltException
import io.ldavin.beltbraces.exception.NoAssertionFoundException
import io.ldavin.beltbraces.internal.TestFixtures.analysis
import io.ldavin.beltbraces.internal.TestFixtures.emptyAnalysis
import io.ldavin.beltbraces.internal.TestFixtures.property
import io.ldavin.beltbraces.internal.transformation.ExceptionWriter.Companion.DOES_NOT_OVERRIDE_EQUALS
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class ExceptionWriterTest {

    @Mock
    internal lateinit var assertionWriter: AssertionWriter
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

        given(assertionWriter.transform(property1)).willReturn("assertion1")
        given(assertionWriter.transform(property2)).willReturn("assertion2")

        // WHEN
        catchException { writer.transformToMessage(analysis) }

        // THEN
        val expectedMessage = """
            $DOES_NOT_OVERRIDE_EQUALS:
            assertion1
            assertion2""".trimIndent()

        assertThat(caughtException()).isExactlyInstanceOf(FastenYourSeatBeltException::class.java)
        assertThat(caughtException().message).isEqualTo(expectedMessage)
    }
}