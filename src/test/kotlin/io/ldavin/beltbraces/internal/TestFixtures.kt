package io.ldavin.beltbraces.internal

internal object TestFixtures {

    fun property(
        name: String = "method",
        expectedValue: Any? = "value"
    ): Property = Property(name, expectedValue)

    fun analysis(
        properties: List<Property> = listOf(property("foo"), property("bar")),
        overrideEquals: Boolean = true,
        isKotlinDataClass: Boolean = false
    ) = Analysis(properties, overrideEquals, isKotlinDataClass)

    fun emptyAnalysis() = analysis(properties = emptyList())
}