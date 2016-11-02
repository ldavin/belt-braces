package io.ldavin.beltsbraces

import io.ldavin.beltsbraces.exception.FastenYourSeatBeltException

object BeltsAndBraces {

    private val finder = AssertionFinder()
    private val writer = AssertionWriter()

    fun fasten(testObject: Any) {
        val assertions = finder.analyse(testObject)
        throw FastenYourSeatBeltException(writer.transformToMessage(assertions))
    }
}