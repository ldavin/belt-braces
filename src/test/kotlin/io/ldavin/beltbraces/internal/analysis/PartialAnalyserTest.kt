package io.ldavin.beltbraces.internal.analysis

import io.ldavin.beltbraces.fixture.JEmpty
import io.ldavin.beltbraces.fixture.JStringMemberOverridingEquals
import io.ldavin.beltbraces.internal.Constructor
import io.ldavin.beltbraces.internal.Property
import io.ldavin.beltbraces.internal.TestFixtures.constructor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PartialAnalyserTest {

    private val analyser = PartialAnalyser()

    @Test
    fun `analyse a dumb kotlin object`() {
        class Subject

        // GIVEN
        val subject = Subject()

        // WHEN
        val result = analyser.analyse(subject, emptyList())

        // THEN
        assertThat(result).isEqualTo(PartialAnalysis(null, overrideEquals = false, isKotlinDataClass = false))
    }

    @Test
    fun `analyse a dumb java object`() {
        // GIVEN
        val subject = JEmpty()

        // WHEN
        val result = analyser.analyse(subject, emptyList())

        // THEN
        assertThat(result).isEqualTo(PartialAnalysis(null, overrideEquals = false, isKotlinDataClass = false))
    }

    @Test
    fun `analyse a kotlin data class`() {
        data class Subject(val prop: String)

        // GIVEN
        val subject = Subject("blah")
        val property = Property("prop", "blah")
        val parameter = Constructor.Parameter("prop", property)
        val constructor = constructor(parameters = listOf(parameter))

        // WHEN
        val result = analyser.analyse(subject, listOf(constructor))

        // THEN
        assertThat(result).isEqualTo(PartialAnalysis(constructor, overrideEquals = true, isKotlinDataClass = true))
    }

    @Test
    fun `analyse a java class overriding equals`() {
        // GIVEN
        val subject = JStringMemberOverridingEquals("blah")
        val property = Property("venus", "blah")
        val parameter = Constructor.Parameter("venus", property)
        val constructor = constructor(parameters = listOf(parameter))

        // WHEN
        val result = analyser.analyse(subject, listOf(constructor))

        // THEN
        assertThat(result).isEqualTo(PartialAnalysis(constructor, overrideEquals = true, isKotlinDataClass = false))
    }

    @Test
    fun `analyse with multiple constructors returns the one with the least unused properties`() {
        class Subject

        // GIVEN
        val subject = Subject()

        val property1 = Property("venus", "blah")
        val property2 = Property("mars", "blahblah")
        val parameter1 = Constructor.Parameter("venus", property1)
        val parameter2 = Constructor.Parameter("mars", property1)
        val constructor1 = constructor(parameters = listOf(parameter1, parameter2))
        val constructor2 = constructor(parameters = listOf(parameter1), unusedProperties = listOf(property2))

        // WHEN
        val result = analyser.analyse(subject, listOf(constructor1, constructor2))

        // THEN
        assertThat(result).isEqualTo(PartialAnalysis(constructor1, false, false))
    }
}