package io.ldavin.beltbraces

import io.ldavin.beltbraces.BeltAndBraces.Language.KOTLIN
import io.ldavin.beltbraces.exception.FastenYourSeatBeltException

object BeltAndBraces {

    /**
     * Language used to call the library
     *
     * Default is [Language.KOTLIN]
     */
    var language: Language = KOTLIN

    private val finder = AssertionFinder()
    private val writer = AssertionWriter()

    fun fasten(testObject: Any) {
        val assertions = finder.analyse(testObject)
        throw FastenYourSeatBeltException(writer.transformToMessage(assertions))
    }

    enum class Language { KOTLIN, JAVA }
}