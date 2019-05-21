package io.ldavin.beltbraces

import io.ldavin.beltbraces.BeltAndBraces.Language.KOTLIN
import io.ldavin.beltbraces.exception.FastenYourSeatBeltException
import io.ldavin.beltbraces.internal.analysis.PropertyFinder
import io.ldavin.beltbraces.internal.analysis.SubjectAnalyser
import io.ldavin.beltbraces.internal.transformation.AssertionWriter
import io.ldavin.beltbraces.internal.transformation.ExceptionWriter

object BeltAndBraces {

    /**
     * Language used to call the library
     *
     * Default is [Language.KOTLIN]
     */
    var language: Language = KOTLIN

    private val analyser = SubjectAnalyser(PropertyFinder())
    private val writer = ExceptionWriter(AssertionWriter())

    fun fasten(testObject: Any) {
        val analysis = analyser.analyse(testObject)
        throw FastenYourSeatBeltException(writer.transformToMessage(analysis))
    }

    enum class Language { KOTLIN, JAVA }
}