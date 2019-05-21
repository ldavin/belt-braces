package io.ldavin.beltbraces.fixture

class KStringMember(val mercury: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KStringMember

        if (mercury != other.mercury) return false

        return true
    }

    override fun hashCode(): Int {
        return mercury.hashCode()
    }
}