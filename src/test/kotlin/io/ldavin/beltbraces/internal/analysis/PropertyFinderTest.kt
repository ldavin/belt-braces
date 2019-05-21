package io.ldavin.beltbraces.internal.analysis

import io.ldavin.beltbraces.fixture.*
import io.ldavin.beltbraces.internal.Property
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PropertyFinderTest {

    private val finder = PropertyFinder()

    @Test
    fun `Finder should return an empty list for an empty java class`() {
        // GIVEN
        val subject = JEmpty()

        // WHEN
        val result = finder.analyse(subject)

        // THEN
        assertThat(result).hasSize(0)
    }

    @Test
    fun `Finder should return one string equality assertion for a kotlin data class`() {
        // GIVEN
        val subject = KDCStringMember("value")

        // WHEN
        val result = finder.analyse(subject)

        // THEN
        assertThat(result).hasSize(1)
        assertThat(result[0]).isEqualTo(Property("mercury", "value"))
    }

    @Test
    fun `Finder should return one string equality assertion for a kotlin class`() {
        // GIVEN
        val subject = KStringMember("value")

        // WHEN
        val result = finder.analyse(subject)

        // THEN
        assertThat(result).hasSize(1)
        assertThat(result[0]).isEqualTo(Property("mercury", "value"))
    }

    @Test
    fun `Finder should return one string equality assertion for a java class`() {
        // GIVEN
        val subject = JStringMember("value")

        // WHEN
        val result = finder.analyse(subject)

        // THEN
        assertThat(result).hasSize(1)
        assertThat(result[0]).isEqualTo(Property("mercury", "value"))
    }

    @Test
    fun `Finder should return all primitive equality assertions for java class`() {
        // GIVEN
        val subject = JPrimitiveMembers(-1, -2, -3, -4, 1.1f, 1.2, true, 'd')

        // WHEN
        val result = finder.analyse(subject)

        // THEN
        assertThat(result).hasSize(8)

        val expectedByte: Byte = -1
        assertThat(result).contains(Property("theByte", expectedByte))

        val expectedShort: Short = -2
        assertThat(result).contains(Property("theShort", expectedShort))

        val expectedInt: Int = -3
        assertThat(result).contains(Property("theInt", expectedInt))

        val expectedLong: Long = -4
        assertThat(result).contains(Property("theLong", expectedLong))

        val expectedFloat: Float = 1.1f
        assertThat(result).contains(Property("theFloat", expectedFloat))

        val expectedDouble: Double = 1.2
        assertThat(result).contains(Property("theDouble", expectedDouble))

        assertThat(result).contains(Property("theBoolean", true))
        assertThat(result).contains(Property("theChar", 'd'))
    }
}