package io.ldavin.beltsbraces

import io.ldavin.beltsbraces.exception.FastenYourSeatBeltException
import io.ldavin.beltsbraces.exception.NoAssertionFoundException
import io.ldavin.beltsbraces.fixture.JEmpty
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BeltsTest {

    @Test
    fun `Integration test with a simple class`() {
        // GIVEN
        val testObject = TestClass("Hi!")

        // WHEN
        var catched: FastenYourSeatBeltException? = null
        try {
            BeltsAndBraces.fasten(testObject)
        } catch(e: FastenYourSeatBeltException) {
            catched = e
        }

        // THEN
        assertThat(catched!!.message).hasLineCount(3)
        assertThat(catched.message).contains("assertThat(result.getAttribute()).isEqualTo(\"Hi!\")")
    }

    @Test(expected = NoAssertionFoundException::class)
    fun `Integration test with an empty class`() {
        // GIVEN
        val testObject = JEmpty()

        // WHEN
        BeltsAndBraces.fasten(testObject)
    }

    data class TestClass(val attribute: String)
}