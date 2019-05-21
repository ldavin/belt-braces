package io.ldavin.beltbraces.internal

internal class AssertionWriter {

    fun transform(property: Property): String {

        val beginning = "assertThat(result.${property.name})"
        val end = when (property.expectedValue) {
            null -> "isNull()"
            else -> "isEqualTo(${formatValue(property.expectedValue)})"
        }

        return "\t$beginning.$end"
    }

    private fun formatValue(value: Any?): String {
        return when (value) {
            is String -> "\"$value\""
            is Char -> "'$value'"
            is Long -> "${value}L"
            is Float -> "${value}f"

            else -> value.toString()
        }
    }
}