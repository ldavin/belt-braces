package io.ldavin.beltbraces.internal

internal data class Constructor(val parameters: List<Parameter>, val unusedProperties: List<Property>) {
    data class Parameter(val name: String, val correspondingProperty: Property?)
}