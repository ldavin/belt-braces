package io.ldavin.beltbraces.internal.transformation

import io.ldavin.beltbraces.internal.Property
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PropertyWriterTest {

    private val writer = PropertyWriter()

    @Test
    fun `transform a nullity assertion`() {
        // GIVEN
        val assertion = Property("tomato")

        // WHEN
        val result = writer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("\tassertThat(result.tomato).isNull()")
    }

    @Test
    fun `transform an equality assertion for a String`() {
        // GIVEN
        val assertion = Property("stuff", "result")

        // WHEN
        val result = writer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("\tassertThat(result.stuff).isEqualTo(\"result\")")
    }

    @Test
    fun `transform an equality assertion for a byte`() {
        // GIVEN
        val value: Byte = 1
        val assertion = Property("stuff", value)

        // WHEN
        val result = writer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("\tassertThat(result.stuff).isEqualTo(1)")
    }

    @Test
    fun `transform an equality assertion for a short`() {
        // GIVEN
        val value: Short = 1
        val assertion = Property("stuff", value)

        // WHEN
        val result = writer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("\tassertThat(result.stuff).isEqualTo(1)")
    }

    @Test
    fun `transform an equality assertion for a integer`() {
        // GIVEN
        val value: Int = 123
        val assertion = Property("stuff", value)

        // WHEN
        val result = writer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("\tassertThat(result.stuff).isEqualTo(123)")
    }

    @Test
    fun `transform an equality assertion for a long`() {
        // GIVEN
        val value: Long = 123
        val assertion = Property("stuff", value)

        // WHEN
        val result = writer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("\tassertThat(result.stuff).isEqualTo(123L)")
    }

    @Test
    fun `transform an equality assertion for a float`() {
        // GIVEN
        val value: Float = 1.1f
        val assertion = Property("stuff", value)

        // WHEN
        val result = writer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("\tassertThat(result.stuff).isEqualTo(1.1f)")
    }

    @Test
    fun `transform an equality assertion for a double`() {
        // GIVEN
        val value: Double = 1.1
        val assertion = Property("stuff", value)

        // WHEN
        val result = writer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("\tassertThat(result.stuff).isEqualTo(1.1)")
    }

    @Test
    fun `transform an equality assertion for a char`() {
        // GIVEN
        val value: Char = 'e'
        val assertion = Property("stuff", value)

        // WHEN
        val result = writer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("\tassertThat(result.stuff).isEqualTo('e')")
    }
}