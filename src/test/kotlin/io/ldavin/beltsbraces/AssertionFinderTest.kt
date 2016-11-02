package io.ldavin.beltsbraces

import io.ldavin.beltsbraces.Assertion.Type.EQUALITY
import io.ldavin.beltsbraces.fixture.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class AssertionFinderTest {

    @Test
    fun `Finder should return an empty list for an empty java class`() {
        // GIVEN

        val finder = AssertionFinder()
        val subject = JEmpty()

        // WHEN
        val result = finder.analyse(subject)

        // THEN
        assertThat(result).hasSize(0)
    }

    @Test
    fun `Finder should return one string equality assertion for a kotlin class`() {
        // GIVEN

        val finder = AssertionFinder()
        val subject = KStringMember("value")

        // WHEN
        val result = finder.analyse(subject)

        // THEN
        assertThat(result).hasSize(1)
        assertThat(result[0]).isEqualTo(Assertion("getMercury", EQUALITY, "value"))
    }

    @Test
    fun `Finder should return one string equality assertion for a java class`() {
        // GIVEN

        val finder = AssertionFinder()
        val subject = JStringMember("value")

        // WHEN
        val result = finder.analyse(subject)

        // THEN
        assertThat(result).hasSize(1)
        assertThat(result[0]).isEqualTo(Assertion("getMercury", EQUALITY, "value"))
    }

    @Test
    fun `Finder should return all primitive equality assertions for java class`() {
        // GIVEN

        val finder = AssertionFinder()
        val subject = JPrimitiveMembers(-1, -2, -3, -4, 1.1f, 1.2, true, 'd')


        // WHEN
        val result = finder.analyse(subject)

        // THEN
        assertThat(result).hasSize(8)

        val expectedByte: Byte = -1
        assertThat(result[0]).isEqualTo(Assertion("getTheByte", EQUALITY, expectedByte))

        val expectedShort: Short = -2
        assertThat(result[1]).isEqualTo(Assertion("getTheShort", EQUALITY, expectedShort))

        val expectedInt: Int = -3
        assertThat(result[2]).isEqualTo(Assertion("getTheInt", EQUALITY, expectedInt))

        val expectedLong: Long = -4
        assertThat(result[3]).isEqualTo(Assertion("getTheLong", EQUALITY, expectedLong))

        val expectedFloat: Float = 1.1f
        assertThat(result[4]).isEqualTo(Assertion("getTheFloat", EQUALITY, expectedFloat))

        val expectedDouble: Double = 1.2
        assertThat(result[5]).isEqualTo(Assertion("getTheDouble", EQUALITY, expectedDouble))

        assertThat(result[6]).isEqualTo(Assertion("getTheBoolean", EQUALITY, true))
        assertThat(result[7]).isEqualTo(Assertion("getTheChar", EQUALITY, 'd'))
    }


}