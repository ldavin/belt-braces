package io.ldavin.beltbraces.internal.transformation

import io.ldavin.beltbraces.internal.Constructor

internal class ConstructorWriter {

    fun transform(constructor: Constructor): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("\tval expected = ")
        stringBuilder.append(constructor.className)
        stringBuilder.append("(")

        if (constructor.parameters.size > 3) {
            stringBuilder.appendln()
            val parameters = constructor.parameters.map {
                "\t\t${it.name} = ${formatValue(it.correspondingProperty?.expectedValue)}"
            }
            stringBuilder.append(parameters.joinToString(separator = ",\n", postfix = "\n"))
            stringBuilder.append("\t")

        } else {
            val parameters = constructor.parameters.map { formatValue(it.correspondingProperty?.expectedValue) }
            stringBuilder.append(parameters.joinToString(separator = ", "))
        }

        stringBuilder.appendln(")")
        stringBuilder.append("\tassertThat(result).isEqualTo(expected)")

        if (constructor.unusedProperties.isNotEmpty()) {
            stringBuilder.appendln()
            stringBuilder.append(UNUSED_PROPERTIES)

            val details = constructor.unusedProperties.map { """"${it.name}" = ${formatValue(it.expectedValue)}""" }
            stringBuilder.append(details.joinToString(prefix = "(", separator = ", ", postfix = ")"))
        }

        if (constructor.parameters.any { it.correspondingProperty == null }) {
            stringBuilder.appendln()
            stringBuilder.append(NULL_PROPERTIES)

            val details = constructor.parameters.mapIndexed { i, it ->
                if (it.correspondingProperty != null) return@mapIndexed null
                """argument n°${i + 1} named "${it.name}""""
            }.filterNotNull()
            stringBuilder.append(details.joinToString(prefix = "(", separator = ", ", postfix = ")"))
        }

        return stringBuilder.toString()
    }

    companion object {
        internal const val UNUSED_PROPERTIES =
            "/!\\ This constructor does not accept some of the object's detected properties "
        internal const val NULL_PROPERTIES =
            "/!\\ This constructor takes arguments for which the value could not be guessed "
    }
}