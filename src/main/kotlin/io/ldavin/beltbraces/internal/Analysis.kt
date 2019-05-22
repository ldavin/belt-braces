package io.ldavin.beltbraces.internal

internal data class Analysis(
    val properties: List<Property>,
    val constructors: List<Constructor>,
    val preferredConstructor: Constructor?,
    val overrideEquals: Boolean,
    val isKotlinDataClass: Boolean
)