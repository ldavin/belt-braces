package io.ldavin.beltbraces.internal.analysis

import io.ldavin.beltbraces.internal.Property
import kotlin.reflect.KCallable
import kotlin.reflect.full.functions
import kotlin.reflect.full.valueParameters

internal class PropertyFinder {

    private val ignoredObjectMethods = listOf("hashCode", "toString")

    fun analyse(subject: Any): List<Property> =
        if (subject.isKotlinClass()) {
            subject::class.members
        } else {
            subject::class.functions
        }
            .filter { it.isEligible(subject) }
            .map {
                Property(
                    name = cleanPropertyName(it.name, subject),
                    expectedValue = it.call(subject)
                )
            }

    private fun cleanPropertyName(original: String, subject: Any): String {
        if (subject.isKotlinClass()) return original

        if (original.startsWith("get")) {
            return original.removePrefix("get").decapitalize()
        }

        return original
    }

    private fun Any.isKotlinClass() =
        javaClass.declaredAnnotations.any { it.annotationClass.qualifiedName == Metadata::class.qualifiedName }

    private fun Any.isKotlinDataClass() = this::class.isData

    private fun <R> KCallable<R>.isEligible(subject: Any): Boolean {
        val itHasNoParameters = valueParameters.isEmpty()
        val itReturnsVoid = returnType.toString() == Unit::class.qualifiedName
        val itIsInTheIgnoreList = ignoredObjectMethods.contains(name)
        val itIsDataClassComponent = subject.isKotlinDataClass() && name.matches("""component\d+""".toRegex())

        return itHasNoParameters
                && !itReturnsVoid
                && !itIsInTheIgnoreList
                && !itIsDataClassComponent
    }

}
