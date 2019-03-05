package io.ldavin.beltbraces

import io.ldavin.beltbraces.exception.FastenYourSeatBeltException

object BeltAndBraces {

    private val finder = AssertionFinder()
    private val writer = AssertionWriter()

    fun fasten(testObject: Any) {
        val assertions = finder.analyse(testObject)
        throw FastenYourSeatBeltException(writer.transformToMessage(assertions))
    }
}