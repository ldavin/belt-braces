package io.ldavin.beltsbraces

import io.ldavin.beltsbraces.exception.FastenYourSeatBeltException
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BeltsTest {

    @Test
    fun integrationTest() {
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

    data class TestClass(val attribute: String)
}