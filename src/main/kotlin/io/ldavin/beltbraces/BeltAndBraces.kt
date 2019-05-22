package io.ldavin.beltbraces

import io.ldavin.beltbraces.BeltAndBraces.Language.KOTLIN
import io.ldavin.beltbraces.exception.FastenYourSeatBeltException
import io.ldavin.beltbraces.internal.analysis.Analyser
import io.ldavin.beltbraces.internal.analysis.ConstructorFinder
import io.ldavin.beltbraces.internal.analysis.PartialAnalyser
import io.ldavin.beltbraces.internal.analysis.PropertyFinder
import io.ldavin.beltbraces.internal.transformation.PropertyWriter
import io.ldavin.beltbraces.internal.transformation.ConstructorWriter
import io.ldavin.beltbraces.internal.transformation.ExceptionWriter

object BeltAndBraces {

    /**
     * Language used to call the library
     *
     * Default is [Language.KOTLIN]
     */
    var language: Language = KOTLIN

    private val analyser = Analyser(PropertyFinder(), ConstructorFinder(), PartialAnalyser())
    private val writer = ExceptionWriter(PropertyWriter(), ConstructorWriter())

    fun fasten(testObject: Any) {
        val analysis = analyser.analyse(testObject)
        throw FastenYourSeatBeltException(writer.transformToMessage(analysis))
    }

    enum class Language { KOTLIN, JAVA }
}