package io.ldavin.beltbraces.internal.analysis

import io.ldavin.beltbraces.internal.Constructor
import io.ldavin.beltbraces.internal.Constructor.Parameter
import io.ldavin.beltbraces.internal.Property
import kotlin.reflect.KParameter
import kotlin.reflect.full.valueParameters

internal class ConstructorFinder {

    fun analyse(subject: Any, properties: List<Property>): List<Constructor> =
        subject::class.constructors
            .map { kconstructor ->

                val parameters = kconstructor.valueParameters
                    .sortedBy(KParameter::index)
                    .map { kparameter ->
                        Parameter(kparameter.name!!, properties.find { it.name == kparameter.name })
                    }

                val usedProperties = parameters.mapNotNull { it.correspondingProperty }
                val unusedProperties = properties.filterNot { usedProperties.contains(it) }

                Constructor(parameters, unusedProperties)
            }
            .filter {
                it.unusedProperties.count() < properties.count()
            }
}