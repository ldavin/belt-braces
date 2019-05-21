package io.ldavin.beltbraces.internal

import io.ldavin.beltbraces.internal.TestFixtures.property
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class SubjectAnalyserTest {

    @Mock
    internal lateinit var propertyFinder: PropertyFinder
    @InjectMocks
    internal lateinit var subjectAnalyser: SubjectAnalyser

    @Test
    fun `analyse an object`() {
        class Subject

        // GIVEN
        val subject = Subject()

        val properties = listOf(property())
        given(propertyFinder.analyse(subject)).willReturn(properties)

        // WHEN
        val result = subjectAnalyser.analyse(subject)

        // THEN
        assertThat(result).isEqualTo(Analysis(properties, overrideEquals = false, isKotlinDataClass = false))
    }

    @Test
    fun `analyse an object overriding equals`() {
        // GIVEN
        val subject = "a string subject"

        val properties = listOf(property())
        given(propertyFinder.analyse(subject)).willReturn(properties)

        // WHEN
        val result = subjectAnalyser.analyse(subject)

        // THEN
        assertThat(result).isEqualTo(Analysis(properties, overrideEquals = true, isKotlinDataClass = false))
    }

    @Test
    fun `analyse a data class object`() {
        data class Subject(val thing: String)
        // GIVEN
        val subject = Subject("stuff")

        val properties = listOf(property())
        given(propertyFinder.analyse(subject)).willReturn(properties)

        // WHEN
        val result = subjectAnalyser.analyse(subject)

        // THEN
        assertThat(result).isEqualTo(Analysis(properties, overrideEquals = true, isKotlinDataClass = true))
    }
}