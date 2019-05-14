package io.ldavin.beltbraces

import com.googlecode.catchexception.CatchException
import com.googlecode.catchexception.CatchException.catchException
import io.ldavin.beltbraces.exception.NoAssertionFoundException
import io.ldavin.beltbraces.fixture.JEmpty
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BeltTest {

    @Test
    fun `Integration test with a simple class`() {
        // GIVEN
        val testObject = TestClass("Hi!")

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        val caughtException = caughtException()
        assertThat(caughtException.message).hasLineCount(3)
        assertThat(caughtException.message).contains("assertThat(result.getAttribute()).isEqualTo(\"Hi!\")")
    }

    @Test
    fun `Integration test with an empty class`() {
        // GIVEN
        val testObject = JEmpty()

        // WHEN
        catchException { BeltAndBraces.fasten(testObject) }

        // THEN
        assertThat(caughtException())
            .isExactlyInstanceOf(NoAssertionFoundException::class.java)
    }

    data class TestClass(val attribute: String)
}

fun caughtException(): Exception = CatchException.caughtException()