package io.ldavin.beltbraces.internal.analysis

import io.ldavin.beltbraces.internal.Analysis

internal class SubjectAnalyser(private val propertyFinder: PropertyFinder) {

    fun analyse(subject: Any): Analysis {
        val properties = propertyFinder.analyse(subject)

        // Not using the Kotlin Class for declared members because of strange behaviours on kotlin augmented basic types
        val directlyOverridesEquals = subject::class.java.declaredMethods.any { it.name == "equals" }
        val isKotlinDataClass = subject::class.isData

        return Analysis(properties, directlyOverridesEquals, isKotlinDataClass)
    }
}