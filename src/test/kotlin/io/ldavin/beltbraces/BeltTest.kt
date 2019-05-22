package io.ldavin.beltbraces

import com.googlecode.catchexception.CatchException
import com.googlecode.catchexception.CatchException.catchException
import io.ldavin.beltbraces.exception.NoAssertionFoundException
import io.ldavin.beltbraces.fixture.JEmpty
import io.ldavin.beltbraces.fixture.JStringMember
import io.ldavin.beltbraces.fixture.JStringMemberOverridingEquals
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test

class BeltTest {

    /*
     *  Integration tests of the library with its default setting (called from Kotlin code)
     *  whether checking on Kotlin or Java classes
     */

    @Test
    fun `BeltAndBraces should suggest an object equality assertion for a kotlin data class`() {
        data class SubjectClass(val attribute: String)

        // GIVEN
        val testObject = SubjectClass("Hi!")

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        val excerpts = listOf(
            "The object to check appears to be a Kotlin data class!",
            "Object equality style (preferred):",
            """val expected = SubjectClass("Hi!")""",
            "assertThat(result).isEqualTo(expected)",
            "If you prefer to assert it property-style:",
            """assertThat(result.attribute).isEqualTo("Hi!")"""
        )
        assertThat(caughtException().message).containsSubsequence(excerpts)
    }

    @Test
    fun `BeltAndBraces should suggest an object equality assertion with named arguments for a large kotlin data class`() {
        data class SubjectClass(val mercury: String, val venus: String, val earth: String, val mars: String)

        // GIVEN
        val testObject = SubjectClass("Hot!", "Also hot", "Nice", "Red")

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        val excerpts = listOf(
            "The object to check appears to be a Kotlin data class!",
            "Object equality style (preferred):",
            "val expected = SubjectClass(",
            """mercury = "Hot!",""",
            """venus = "Also hot",""",
            """earth = "Nice",""",
            """mars = "Red"""",
            ")",
            "assertThat(result).isEqualTo(expected)",
            "If you prefer to assert it property-style:",
            """assertThat(result.earth).isEqualTo("Nice")""",
            """assertThat(result.mars).isEqualTo("Red")""",
            """assertThat(result.mercury).isEqualTo("Hot!")""",
            """assertThat(result.venus).isEqualTo("Also hot")"""
        )
        assertThat(caughtException().message).containsSubsequence(excerpts)
    }

    @Test
    fun `BeltAndBraces should suggest an object equality assertion for a kotlin class overriding equals`() {
        class SubjectClass(val attribute: String) {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as SubjectClass
                if (attribute != other.attribute) return false

                return true
            }

            override fun hashCode() = attribute.hashCode()
        }

        // GIVEN
        val testObject = SubjectClass("Trop bien !")

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        val excerpts = listOf(
            "The object to check appears to implement the `equals()` method!",
            "If the `equals()` implementation is correct, assert it object-style (preferred):",
            """val expected = SubjectClass("Trop bien !")""",
            "assertThat(result).isEqualTo(expected)",
            "If you prefer to assert it property-style:",
            """assertThat(result.attribute).isEqualTo("Trop bien !")"""
        )
        assertThat(caughtException().message).containsSubsequence(excerpts)
    }

    @Test
    fun `BeltAndBraces should suggest an object equality assertion for a java class overriding equals`() {
        // GIVEN
        val testObject = JStringMemberOverridingEquals("Super !")

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        val excerpts = listOf(
            "The object to check appears to implement the `equals()` method!",
            "If the `equals()` implementation is correct, assert it object-style (preferred):",
            "/!\\ No constructor automagically found :(",
            "If you prefer to assert it property-style:",
            """assertThat(result.venus).isEqualTo("Super !")"""
        )
        assertThat(caughtException().message).containsSubsequence(excerpts)
    }

    @Test
    fun `BeltAndBraces should suggest property equality assertions for a kotlin class`() {
        class SubjectClass(val attribute: String)

        // GIVEN
        val testObject = SubjectClass("Génial")

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        val excerpts = listOf(
            "The object does not override `equals()` so it has to be checked field by field:",
            """assertThat(result.attribute).isEqualTo("Génial")"""
        )
        assertThat(caughtException().message).containsSubsequence(excerpts)
    }

    @Test
    fun `BeltAndBraces should suggest property equality assertions for a java class`() {
        // GIVEN
        val testObject = JStringMember("Superbe")

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        val excerpts = listOf(
            "The object does not override `equals()` so it has to be checked field by field:",
            """assertThat(result.mercury).isEqualTo("Superbe")"""
        )
        assertThat(caughtException().message).containsSubsequence(excerpts)
    }

    @Test
    fun `BeltAndBraces should find nothing to assert on an empty kotlin class`() {
        class EmptyClass

        // GIVEN
        val testObject = EmptyClass()

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        assertThat(caughtException()).isExactlyInstanceOf(NoAssertionFoundException::class.java)
    }

    @Test
    fun `BeltAndBraces should find nothing to assert on an empty java class`() {
        // GIVEN
        val testObject = JEmpty()

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        assertThat(caughtException()).isExactlyInstanceOf(NoAssertionFoundException::class.java)
    }

}

fun caughtException(): Exception = CatchException.caughtException()