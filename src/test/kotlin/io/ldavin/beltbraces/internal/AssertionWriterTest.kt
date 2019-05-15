package io.ldavin.beltbraces.internal

import com.googlecode.catchexception.CatchException.catchException
import io.ldavin.beltbraces.caughtException
import io.ldavin.beltbraces.exception.NoAssertionFoundException
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class AssertionWriterTest {

    @Test
    fun `Transform an equality assertion`() {
        // GIVEN
        val writer = AssertionWriter()

        // WHEN
        catchException { writer.transformToMessage(emptyList()) }

        // THEN
        assertThat(caughtException())
            .isExactlyInstanceOf(NoAssertionFoundException::class.java)
    }

}