package io.ldavin.beltbraces.internal.transformation

import io.ldavin.beltbraces.internal.TestFixtures.constructor
import io.ldavin.beltbraces.internal.TestFixtures.parameter
import io.ldavin.beltbraces.internal.TestFixtures.property
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ConstructorWriterTest {

    private val writer = ConstructorWriter()

    @Test
    fun `transform writes a short instantiation and an assertion`() {
        // GIVEN
        val constructor = constructor(
            className = "Friends",
            unusedProperties = emptyList(),
            parameters = listOf(
                parameter(name = "dog", property = property("dog", "rex")),
                parameter(name = "cat", property = property("cat", "moka"))
            )
        )

        // WHEN
        val result = writer.transform(constructor)

        // THEN
        assertThat(result).isEqualTo(
            "\tval expected = Friends(\"rex\", \"moka\")\n" +
                    "\tassertThat(result).isEqualTo(expected)"
        )
    }

    @Test
    fun `transform writes a long instantiation and an assertion`() {
        // GIVEN
        val constructor = constructor(
            className = "BestFriends",
            unusedProperties = emptyList(),
            parameters = listOf(
                parameter(name = "dog", property = property("dog", "rex")),
                parameter(name = "cat", property = property("cat", "moka")),
                parameter(name = "rabbit", property = property("rabbit", "pinpin")),
                parameter(name = "fish", property = property("fish", "nemo"))
            )
        )

        // WHEN
        val result = writer.transform(constructor)

        // THEN
        assertThat(result).isEqualTo(
            "\tval expected = BestFriends(\n" +
                    "\t\tdog = \"rex\",\n" +
                    "\t\tcat = \"moka\",\n" +
                    "\t\trabbit = \"pinpin\",\n" +
                    "\t\tfish = \"nemo\"\n" +
                    "\t)\n" +
                    "\tassertThat(result).isEqualTo(expected)"
        )
    }

    @Test
    fun `transform adds a bottom message if there are unused properties`() {
        // GIVEN
        val constructor = constructor(
            className = "Friends",
            unusedProperties = listOf(property("dog", "rex")),
            parameters = listOf(parameter(name = "cat", property = property("cat", "moka")))
        )

        // WHEN
        val result = writer.transform(constructor)

        // THEN
        assertThat(result.lines().last())
            .isEqualTo(
                "/!\\ This constructor does not accept some of the object's " +
                        """detected properties ("dog" = "rex")"""
            )
    }

    @Test
    fun `transform adds a bottom message if there are null parameters`() {
        // GIVEN
        val constructor = constructor(
            className = "Friends",
            unusedProperties = emptyList(),
            parameters = listOf(parameter(name = "dog", property = null))
        )

        // WHEN
        val result = writer.transform(constructor)

        // THEN
        assertThat(result.lines().last())
            .isEqualTo(
                "/!\\ This constructor takes arguments for which the value could not be guessed " +
                        """(argument n°1 named "dog")"""
            )
    }
}