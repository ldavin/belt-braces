package io.ldavin.beltbraces.internal

import java.lang.reflect.Method

class AssertionFinder {

    private val javaObjectMethod = listOf("hashCode", "toString")

    fun analyse(subject: Any): List<Assertion> {

        val members = subject.javaClass.methods
        val equalityMembers = members.filter { it.isEqualityEligible() }

        return equalityMembers.map {
            Assertion(
                methodName = it.name,
                type = Assertion.Type.EQUALITY,
                expectedValue = it.invoke(subject)
            )
        }
    }

    fun Method.isEqualityEligible(): Boolean {
        val itHasNoParameters = this.parameterCount == 0

        val itReturnsVoid = this.returnType == Void.TYPE
        val itReturnsPrimitive = this.returnType.isPrimitive
        val itReturnsString = this.returnType == String::class.java

        val itIsObjectOverriddenMethod = javaObjectMethod.contains(this.name)
        val itIsKotlinComponentMethod = this.name.matches(Regex("component\\d+"))


        return itHasNoParameters
                && !itReturnsVoid
                && (itReturnsPrimitive || itReturnsString)
                && !itIsObjectOverriddenMethod
                && !itIsKotlinComponentMethod
    }

}