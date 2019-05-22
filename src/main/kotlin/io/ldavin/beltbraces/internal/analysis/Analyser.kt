package io.ldavin.beltbraces.internal.analysis

import io.ldavin.beltbraces.internal.Analysis

internal class Analyser(
    private val propertyFinder: PropertyFinder,
    private val constructorFinder: ConstructorFinder,
    private val partialAnalyser: PartialAnalyser
) {

    fun analyse(subject: Any): Analysis {
        val properties = propertyFinder.analyse(subject)
        val constructors = constructorFinder.analyse(subject, properties)
        val partialAnalysis = partialAnalyser.analyse(subject, constructors)

        return Analysis(
            properties = properties,
            constructors = constructors,
            preferredConstructor = partialAnalysis.preferredConstructor,
            overrideEquals = partialAnalysis.overrideEquals,
            isKotlinDataClass = partialAnalysis.isKotlinDataClass
        )
    }
}