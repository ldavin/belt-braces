package io.ldavin.beltbraces.internal

import io.ldavin.beltbraces.internal.Assertion
import io.ldavin.beltbraces.internal.AssertionTransformer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class AssertionTransformerTest {

    @Test
    fun `Transform a nullity assertion`() {
        // GIVEN
        val transformer = AssertionTransformer()
        val assertion =
            Assertion("getTomato", Assertion.Type.NULLITY)

        // WHEN
        val result = transformer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("assertThat(result.getTomato()).isNull()")
    }

    @Test
    fun `Transform an equality assertion for a String`() {
        // GIVEN
        val transformer = AssertionTransformer()
        val assertion = Assertion(
            "getStuff",
            Assertion.Type.EQUALITY,
            "result"
        )

        // WHEN
        val result = transformer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("assertThat(result.getStuff()).isEqualTo(\"result\")")
    }

    @Test
    fun `Transform an equality assertion for a byte`() {
        // GIVEN
        val transformer = AssertionTransformer()
        val value: Byte = 1
        val assertion = Assertion(
            "getStuff",
            Assertion.Type.EQUALITY,
            value
        )

        // WHEN
        val result = transformer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("assertThat(result.getStuff()).isEqualTo((byte) 1)")
    }

    @Test
    fun `Transform an equality assertion for a short`() {
        // GIVEN
        val transformer = AssertionTransformer()
        val value: Short = 1
        val assertion = Assertion(
            "getStuff",
            Assertion.Type.EQUALITY,
            value
        )

        // WHEN
        val result = transformer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("assertThat(result.getStuff()).isEqualTo((short) 1)")
    }

    @Test
    fun `Transform an equality assertion for a integer`() {
        // GIVEN
        val transformer = AssertionTransformer()
        val value: Int = 123
        val assertion = Assertion(
            "getStuff",
            Assertion.Type.EQUALITY,
            value
        )

        // WHEN
        val result = transformer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("assertThat(result.getStuff()).isEqualTo(123)")
    }

    @Test
    fun `Transform an equality assertion for a long`() {
        // GIVEN
        val transformer = AssertionTransformer()
        val value: Long = 123
        val assertion = Assertion(
            "getStuff",
            Assertion.Type.EQUALITY,
            value
        )

        // WHEN
        val result = transformer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("assertThat(result.getStuff()).isEqualTo(123)")
    }

    @Test
    fun `Transform an equality assertion for a float`() {
        // GIVEN
        val transformer = AssertionTransformer()
        val value: Float = 1.1f
        val assertion = Assertion(
            "getStuff",
            Assertion.Type.EQUALITY,
            value
        )

        // WHEN
        val result = transformer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("assertThat(result.getStuff()).isEqualTo(1.1f)")
    }


    @Test
    fun `Transform an equality assertion for a double`() {
        // GIVEN
        val transformer = AssertionTransformer()
        val value: Double = 1.1
        val assertion = Assertion(
            "getStuff",
            Assertion.Type.EQUALITY,
            value
        )

        // WHEN
        val result = transformer.transform(assertion)

        // THEN
        assertThat(result).isEqualTo("assertThat(result.getStuff()).isEqualTo(1.1)")
    }
}