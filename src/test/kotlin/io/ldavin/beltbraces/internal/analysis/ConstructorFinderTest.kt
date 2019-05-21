package io.ldavin.beltbraces.internal.analysis

import io.ldavin.beltbraces.internal.Constructor
import io.ldavin.beltbraces.internal.Constructor.Parameter
import io.ldavin.beltbraces.internal.TestFixtures.property
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ConstructorFinderTest {

    private val finder = ConstructorFinder()

    @Test
    fun `analyse returns a constructor that matches perfectly`() {
        data class Subject(val stuff: String, val things: Int)

        val property1 = property("stuff")
        val property2 = property("things")

        // GIVEN
        val subject = Subject("things", 898)

        // WHEN
        val constructors = finder.analyse(subject, listOf(property1, property2))

        // THEN
        val expectedConstructor = Constructor(
            listOf(
                Parameter("stuff", property1),
                Parameter("things", property2)
            ),
            emptyList()
        )

        assertThat(constructors).hasSize(1)
        assertThat(constructors.first()).isEqualTo(expectedConstructor)
    }

    @Test
    fun `analyse returns a constructor that matches`() {
        data class Subject(val stuff: String) {
            val things = 1017
        }

        val property1 = property("stuff")
        val property2 = property("things")

        // GIVEN
        val subject = Subject("things")

        // WHEN
        val constructors = finder.analyse(subject, listOf(property1, property2))

        // THEN
        val expectedConstructor = Constructor(
            listOf(Parameter("stuff", property1)),
            listOf(property2)
        )

        assertThat(constructors).hasSize(1)
        assertThat(constructors.first()).isEqualTo(expectedConstructor)
    }

    @Test
    fun `analyse returns no constructor if no property matches`() {
        class Subject(stuff: String) {
            val things = 1017
        }

        val property = property("things")

        // GIVEN
        val subject = Subject("stuff")

        // WHEN
        val constructors = finder.analyse(subject, listOf(property))

        // THEN
        assertThat(constructors).isEqualTo(emptyList<Constructor>())
    }
}