package io.ldavin.beltbraces

import com.googlecode.catchexception.CatchException.catchException
import io.ldavin.beltbraces.exception.NoAssertionFoundException
import io.ldavin.beltbraces.fixture.JEmpty
import io.ldavin.beltbraces.fixture.JStringMember
import io.ldavin.beltbraces.fixture.JStringMemberOverridingEquals
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class BeltTestJava {

    /*
     *  Integration tests of the library with its Java setting
     *  whether checking on Kotlin or Java classes
     */

    @Before
    fun setUp() {
        BeltAndBraces.language = BeltAndBraces.Language.JAVA
    }

    @Ignore
    @Test
    fun `BeltAndBraces Java should suggest an object equality assertion for a kotlin data class`() {
        data class SubjectClass(val attribute: String)

        // GIVEN
        val testObject = SubjectClass("Hi!")

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        val excerpts = listOf(
            "The object to check appears to be a Kotlin data class!",
            "Object equality style (preferred):",
            """SubjectClass expected = new SubjectClass("Hi!");""",
            "assertThat(result).isEqualTo(expected);",
            "If you prefer to assert it property-style:",
            """assertThat(result.getAttribute()).isEqualTo("Hi!");"""
        )
        assertThat(caughtException().message).containsSequence(excerpts)
    }

    @Ignore
    @Test
    fun `BeltAndBraces Java should suggest an object equality assertion for a large kotlin data class`() {
        data class SubjectClass(val mercury: String, val venus: String, val earth: String, val mars: String)

        // GIVEN
        val testObject = SubjectClass("Hot!", "Also hot", "Nice", "Red")

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        val excerpts = listOf(
            "The object to check appears to be a Kotlin data class!",
            "Object equality style (preferred):",
            "SubjectClass expected = new SubjectClass(",
            """"Hot!",""",
            """"Also hot",""",
            """"Nice",""",
            """"Red"""",
            ");",
            "assertThat(result).isEqualTo(expected);",
            "If you prefer to assert it property-style:",
            """assertThat(result.getMercury()).isEqualTo("Hot!");""",
            """assertThat(result.getVenus()).isEqualTo("Also hot");""",
            """assertThat(result.getEarth()).isEqualTo("Nice");""",
            """assertThat(result.getMars()).isEqualTo("Red");"""
        )
        assertThat(caughtException().message).containsSequence(excerpts)
    }

    @Ignore
    @Test
    fun `BeltAndBraces Java should suggest an object equality assertion for a kotlin class overriding equals`() {
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
            """SubjectClass expected = new SubjectClass("Trop bien !");""",
            "assertThat(result).isEqualTo(expected);",
            "If you prefer to assert it property-style:",
            """assertThat(result.getAttribute()).isEqualTo("Trop bien !");"""
        )
        assertThat(caughtException().message).containsSequence(excerpts)
    }

    @Ignore
    @Test
    fun `BeltAndBraces Java should suggest an object equality assertion for a java class overriding equals`() {
        // GIVEN
        val testObject = JStringMemberOverridingEquals("Super !")

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        val excerpts = listOf(
            "The object to check appears to implement the `equals()` method!",
            "If the `equals()` implementation is correct, assert it object-style (preferred):",
            """JStringMemberOverridingEquals expected = new JStringMemberOverridingEquals("Super !");""",
            "assertThat(result).isEqualTo(expected);",
            "If you prefer to assert it property-style:",
            """assertThat(result.getAttribute()).isEqualTo("Super !");"""
        )
        assertThat(caughtException().message).containsSequence(excerpts)
    }

    @Ignore
    @Test
    fun `BeltAndBraces Java should suggest property equality assertions for a kotlin class`() {
        class SubjectClass(val attribute: String)

        // GIVEN
        val testObject = SubjectClass("C't'incroyaaable")

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        val excerpts = listOf(
            "The object does not override `equals()` so it has to be checked field by field:",
            """assertThat(result.getAttribute()).isEqualTo("C't'incroyaaable");"""
        )
        assertThat(caughtException().message).containsSequence(excerpts)
    }

    @Ignore
    @Test
    fun `BeltAndBraces Java should suggest property equality assertions for a java class`() {
        // GIVEN
        val testObject = JStringMember("Ça m'fais bouger un tabernaacle")

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        val excerpts = listOf(
            "The object does not override `equals()` so it has to be checked field by field:",
            """assertThat(result.getMercury()).isEqualTo("Ça m'fais bouger un tabernaacle");"""
        )
        assertThat(caughtException().message).containsSequence(excerpts)
    }

    @Test
    fun `BeltAndBraces Java should find nothing to assert on an empty kotlin class`() {
        class EmptyClass

        // GIVEN
        val testObject = EmptyClass()

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        assertThat(caughtException()).isExactlyInstanceOf(NoAssertionFoundException::class.java)
    }

    @Test
    fun `BeltAndBraces Java should find nothing to assert on an empty java class`() {
        // GIVEN
        val testObject = JEmpty()

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        assertThat(caughtException()).isExactlyInstanceOf(NoAssertionFoundException::class.java)
    }

}