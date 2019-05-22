package io.ldavin.beltbraces.internal.analysis

import io.ldavin.beltbraces.internal.Constructor
import kotlin.reflect.full.declaredFunctions

internal class PartialAnalyser {

    fun analyse(subject: Any, constructors: List<Constructor>): PartialAnalysis {
        val directlyOverridesEquals = subject::class.declaredFunctions.any { it.name == "equals" }
        val isKotlinDataClass = subject::class.isData

        val preferredConstructor = if (constructors.isEmpty()) {
            null
        } else {
            constructors.sortedBy { it.unusedProperties.size }.first()
        }

        return PartialAnalysis(preferredConstructor, directlyOverridesEquals, isKotlinDataClass)
    }
}