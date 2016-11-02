package io.ldavin.beltsbraces

import io.ldavin.beltsbraces.exception.NoAssertionFoundException
import org.junit.Test

class AssertionWriterTest {

    @Test(expected = NoAssertionFoundException::class)
    fun `Transform an equality assertion`() {
        // GIVEN
        val writer = AssertionWriter()

        // WHEN
        writer.transformToMessage(emptyList())
    }

}