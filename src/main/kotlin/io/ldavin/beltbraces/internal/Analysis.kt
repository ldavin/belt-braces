package io.ldavin.beltbraces.internal

internal data class Analysis(
    val properties: List<Property>,
    val overrideEquals: Boolean,
    val isKotlinDataClass: Boolean
)