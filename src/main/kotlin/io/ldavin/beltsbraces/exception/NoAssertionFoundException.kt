package io.ldavin.beltsbraces.exception

class NoAssertionFoundException : Exception() {
    override val message: String?
        get() = "No assertions found for your object"
}