package io.ldavin.beltbraces.internal.transformation

import io.ldavin.beltbraces.exception.InternalException
import io.ldavin.beltbraces.exception.NoAssertionFoundException
import io.ldavin.beltbraces.internal.Analysis

internal class ExceptionWriter(
    private val propertyWriter: PropertyWriter,
    private val constructorWriter: ConstructorWriter
) {

    fun transformToMessage(analysis: Analysis): String {

        if (analysis.properties.isEmpty()) {
            throw NoAssertionFoundException()
        }

        if (analysis.isKotlinDataClass) {
            val stringBuilder = StringBuilder()
            stringBuilder.appendln(LOOKS_LIKE_DATA_CLASS)
            stringBuilder.appendln(OBJECT_EQUALITY_ASSERTION)

            val constructor = analysis.preferredConstructor ?: throw InternalException("")
            val equalityAssertion = constructorWriter.transform(constructor)
            stringBuilder.appendln(equalityAssertion)

            stringBuilder.appendln()
            val propertyAssertions = analysis.properties.map { propertyWriter.transform(it) }
            val fallbackPart = propertyAssertions.joinToString(
                prefix = "$PROPERTY_ASSERTIONS_FALLBACK\n",
                separator = "\n"
            )
            stringBuilder.append(fallbackPart)
            return stringBuilder.toString()
        }

        if (analysis.overrideEquals) {
            val stringBuilder = StringBuilder()
            stringBuilder.appendln(OVERRIDES_EQUALS)
            stringBuilder.appendln(EQUALS_WARNING)

            val constructor = analysis.preferredConstructor
            if (constructor == null) {
                stringBuilder.appendln(NO_CONSTRUCTOR_FOUND)
            } else {
                val equalityAssertion = constructorWriter.transform(constructor)
                stringBuilder.appendln(equalityAssertion)
            }

            stringBuilder.appendln()
            val propertyAssertions = analysis.properties.map { propertyWriter.transform(it) }
            val fallbackPart = propertyAssertions.joinToString(
                prefix = "$PROPERTY_ASSERTIONS_FALLBACK\n",
                separator = "\n"
            )
            stringBuilder.append(fallbackPart)
            return stringBuilder.toString()
        }

        val propertyAssertions = analysis.properties.map { propertyWriter.transform(it) }
        val message = propertyAssertions.joinToString(
            prefix = "$DOES_NOT_OVERRIDE_EQUALS\n",
            separator = "\n"
        )

        return message
    }

    companion object {
        internal const val LOOKS_LIKE_DATA_CLASS =
            "The object to check appears to be a Kotlin data class!"
        internal const val OBJECT_EQUALITY_ASSERTION =
            "Object equality style (preferred):"
        internal const val OVERRIDES_EQUALS =
            "The object to check appears to implement the `equals()` method!"
        internal const val EQUALS_WARNING =
            "If the `equals()` implementation is correct, assert it object-style (preferred):"
        internal const val DOES_NOT_OVERRIDE_EQUALS =
            "The object does not override `equals()` so it has to be checked field by field:"
        internal const val PROPERTY_ASSERTIONS_FALLBACK =
            "If you prefer to assert it property-style:"
        internal const val NO_CONSTRUCTOR_FOUND =
            "/!\\ No constructor automagically found :("
        internal const val LINE_BREAK = "\n"
    }
}

internal fun formatValue(value: Any?): String {
    return when (value) {
        is String -> "\"$value\""
        is Char -> "'$value'"
        is Long -> "${value}L"
        is Float -> "${value}f"

        is UnknownValue -> "???"

        else -> value.toString()
    }
}