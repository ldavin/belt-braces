package io.ldavin.beltbraces.internal.transformation

import io.ldavin.beltbraces.exception.FastenYourSeatBeltException
import io.ldavin.beltbraces.exception.NoAssertionFoundException
import io.ldavin.beltbraces.internal.Analysis

internal class ExceptionWriter(private val assertionWriter: AssertionWriter) {

    fun transformToMessage(analysis: Analysis): String {

        if (analysis.properties.isEmpty()) {
            throw NoAssertionFoundException()
        }

        val propertyAssertions = analysis.properties.map { assertionWriter.transform(it) }
        val message = propertyAssertions.joinToString(
            prefix = "$DOES_NOT_OVERRIDE_EQUALS:\n",
            separator = "\n"
        )
        throw FastenYourSeatBeltException(message)
    }

    companion object {
        internal const val DOES_NOT_OVERRIDE_EQUALS =
            "The object does not override `equals()` so it has to be checked field by field"
    }
}