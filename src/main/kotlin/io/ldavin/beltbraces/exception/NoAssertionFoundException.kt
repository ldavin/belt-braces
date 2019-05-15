package io.ldavin.beltbraces.exception

class NoAssertionFoundException : Exception() {
    override val message: String
        get() = "No assertion found for your object"
}