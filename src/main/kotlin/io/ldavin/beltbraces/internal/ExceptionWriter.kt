package io.ldavin.beltbraces.internal

import io.ldavin.beltbraces.exception.FastenYourSeatBeltException
import io.ldavin.beltbraces.exception.NoAssertionFoundException

internal class ExceptionWriter(private val assertionWriter: AssertionWriter) {

    fun transformToMessage(analysis: Analysis): String {

        if (analysis.properties.isEmpty()) {
            throw NoAssertionFoundException()
        }

        val propertyAssertions = analysis.properties.map { assertionWriter.transform(it) }
        val message = propertyAssertions.joinToString(
            prefix = "The object does not override `equals()` so it has to be checked field by field:\n",
            separator = "\n"
        )
        throw FastenYourSeatBeltException(message)
    }
}