package io.ldavin.beltbraces

class AssertionTransformer {

    fun transform(assertion: Assertion): String {

        val beginning = "assertThat(result.${assertion.methodName}())"
        val end = when (assertion.type) {
            Assertion.Type.EQUALITY -> "isEqualTo(${formatValue(assertion.expectedValue)})"
            Assertion.Type.NULLITY -> "isNull()"
        }

        return "$beginning.$end"
    }

    private fun formatValue(value: Any?): String {
        return when (value) {
            is String -> "\"$value\""
            is Byte -> "(byte) $value"
            is Short -> "(short) $value"
            is Float -> "${value}f"

            else -> {
                value.toString()
            }
        }
    }
}