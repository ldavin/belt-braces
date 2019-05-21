package io.ldavin.beltbraces.internal

import com.googlecode.catchexception.CatchException.catchException
import io.ldavin.beltbraces.caughtException
import io.ldavin.beltbraces.exception.NoAssertionFoundException
import io.ldavin.beltbraces.internal.TestFixtures.emptyAnalysis
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class ExceptionWriterTest {

    @Mock
    internal lateinit var assertionWriter: AssertionWriter
    @InjectMocks
    internal lateinit var writer: ExceptionWriter

    @Test
    fun `transformToMessage throws an exception if no properties are found`() {
        // GIVEN
        val analysis = emptyAnalysis()

        // WHEN
        catchException { writer.transformToMessage(analysis) }

        // THEN
        assertThat(caughtException()).isExactlyInstanceOf(NoAssertionFoundException::class.java)
    }
}