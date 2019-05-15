package io.ldavin.beltbraces.internal

data class Assertion(val methodName: String, val type: Type, val expectedValue: Any? = null) {

    enum class Type {
        EQUALITY,
        NULLITY
    }
}