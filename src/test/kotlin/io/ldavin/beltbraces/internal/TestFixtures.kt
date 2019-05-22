package io.ldavin.beltbraces.internal

import io.ldavin.beltbraces.internal.Constructor.Parameter
import io.ldavin.beltbraces.internal.analysis.PartialAnalysis

internal object TestFixtures {

    fun property(
        name: String = "method",
        expectedValue: Any? = "value"
    ): Property = Property(name, expectedValue)

    fun parameter(
        name: String = "dog",
        property: Property? = property("dog")
    ): Parameter = Parameter(name, property)

    fun constructor(
        className: String = "Pet",
        parameters: List<Parameter> = listOf(parameter(name = "cat", property = property(name = "cat"))),
        unusedProperties: List<Property> = emptyList()
    ): Constructor = Constructor(className, parameters, unusedProperties)

    fun partialAnalysis(
        preferredConstructor: Constructor? = constructor(),
        overrideEquals: Boolean = true,
        isKotlinDataClass: Boolean = false
    ) = PartialAnalysis(preferredConstructor, overrideEquals, isKotlinDataClass)

    fun analysis(
        properties: List<Property> = listOf(property("cat")),
        constructors: List<Constructor> = listOf(constructor()),
        preferredConstructor: Constructor? = constructor(),
        overrideEquals: Boolean = true,
        isKotlinDataClass: Boolean = false
    ) = Analysis(properties, constructors, preferredConstructor, overrideEquals, isKotlinDataClass)

    fun emptyAnalysis() = analysis(
        properties = emptyList(),
        constructors = emptyList()
    )
}