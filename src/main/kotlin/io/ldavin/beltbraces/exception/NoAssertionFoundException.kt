package io.ldavin.beltbraces.exception

class NoAssertionFoundException : Exception() {
    override val message: String?
        get() = "No assertions found for your object"
}