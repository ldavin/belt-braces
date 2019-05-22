package io.ldavin.beltbraces.internal.transformation

import io.ldavin.beltbraces.internal.Property

internal class PropertyWriter {

    fun transform(property: Property): String {

        val beginning = "assertThat(result.${property.name})"
        val end = when (property.expectedValue) {
            null -> "isNull()"
            else -> "isEqualTo(${formatValue(property.expectedValue)})"
        }

        return "\t$beginning.$end"
    }
}