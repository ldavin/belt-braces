package io.ldavin.beltbraces

import java.lang.reflect.Method

class AssertionFinder {

    private val javaObjectMethod = listOf("hashCode", "toString")

    fun analyse(subject: Any): List<Assertion> {

        val members = subject.javaClass.methods
        val equalityMembers = members.filter { it.isEqualityEligible() }

        val equalityAssertions = equalityMembers.map {
            Assertion(it.name, Assertion.Type.EQUALITY, it.invoke(subject))
        }

        return equalityAssertions
    }

    fun Method.isEqualityEligible(): Boolean {
        val itHasNoParameters = this.parameterCount == 0

        val itReturnsVoid = this.returnType == Void.TYPE
        val itReturnsPrimitive = this.returnType.isPrimitive
        val itReturnsString = this.returnType == String::class.java

        val itIsObjectOverriddenMethod = javaObjectMethod.contains(this.name)
        val itIsKotlinComponentMethod = this.name.matches(Regex("component\\d+"))


        return itHasNoParameters
                && itReturnsVoid.not()
                && (itReturnsPrimitive || itReturnsString)
                && itIsObjectOverriddenMethod.not()
                && itIsKotlinComponentMethod.not()
    }

}