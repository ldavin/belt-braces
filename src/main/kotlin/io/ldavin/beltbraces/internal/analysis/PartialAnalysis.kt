package io.ldavin.beltbraces.internal.analysis

import io.ldavin.beltbraces.internal.Constructor

internal data class PartialAnalysis(
    val preferredConstructor: Constructor?,
    val overrideEquals: Boolean,
    val isKotlinDataClass: Boolean
)