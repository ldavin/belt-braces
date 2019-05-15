package io.ldavin.beltbraces.internal

import io.ldavin.beltbraces.exception.FastenYourSeatBeltException
import io.ldavin.beltbraces.exception.NoAssertionFoundException

class AssertionWriter {

    val transformer = AssertionTransformer()

    fun transformToMessage(assertions: List<Assertion>): String {

        if (assertions.isEmpty()) {
            throw NoAssertionFoundException()
        }

        val transformedAssertions = assertions.map { transformer.transform(it) }
        val message = transformedAssertions
                .joinToString(prefix = "${assertions.size} assertions found!\n\n", separator = "\n")
        throw FastenYourSeatBeltException(message)
    }

}