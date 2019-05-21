# Belt & Braces

[![Build Status](https://travis-ci.org/ldavin/belt-braces.svg?branch=master)](https://travis-ci.org/ldavin/belt-braces)
[![Code coverage](https://codecov.io/gh/ldavin/belt-braces/branch/master/graph/badge.svg)](https://codecov.io/gh/ldavin/belt-braces)

A time-saver when adding tests on existing code.

This library is currently a work in progress.
Its aim is to allow dedicated developers to quickly add tests on uncovered parts of an existing codebase before modifying it.

## Why
When writing tests for already implemented features, developers often want to secure a few use cases and may end up writing tests as such:

```kotlin
@Test
fun `existingMethod keeps working in this particular case`() {
    // GIVEN
    val subject = ExistingClass(…)
    
    // WHEN
    val result = subject.existingMethod(…)
    
    // THEN
    val expected = ExpectedResult(…)
    assertThat(result).isEqualTo(expected)
    // or
    assertThat(result.someProperty).isEqualTo("something")
    assertThat(result.someOtherProperty).isEqualTo("something else")
    …
}
```

Writing these tests is tedious. Coming up with the correct `expected` object or all the property assertions in the upper example is the most boring part.

## How
The aim of this library is to speed-up this process by allowing the developer to call a helper method when it's time to do the assertions on the result.

```kotlin
@Test
fun `existingMethod keeps working in this particular case`() {
    // GIVEN
    val subject = ExistingClass(…)
    
    // WHEN
    val result = subject.existingMethod(…)
    
    // THEN
    BeltAndBraces.fasten(result)
}
```

The `BeltAndBraces.fasten(result)` call will throw an exception with bits of code that can be copy/pasted to assert the object's value.

This is the currently targeted output for this exception:

```
io.ldavin.beltbraces.exception.FastenYourSeatBeltException: 

The object to check appears to be a Kotlin data class!
Object equality style (preferred):
    val expected = SomeClass("Hi", "there!")
    assertThat(result).isEqualTo(expected)

If you prefer to assert it property-style:
    assertThat(result.attribute).isEqualTo("Hi")
    assertThat(result.otherAttribute).isEqualTo("there!")
```

You can then use the given output to bootstrap the assertions and save time.

## Roadmap
For now, the library prints property-style assertions for every member found that returns a primitive or non-null `String` type (except for `equals()` and `hashcode()` methods).

Final goals are much more ambitious and have been specified as executable tests in the files [BeltTest.kt](src/test/kotlin/io/ldavin/beltbraces/BeltTest.kt) and [BeltTestJava.kt](src/test/kotlin/io/ldavin/beltbraces/BeltTestJava.kt).

- [x] POC finding some members to assert things on and printing simple "assertThat(…).isEqualTo(…)" assertions
- [x] Exhaustive property assertions for an object not overriding `equals()`
- [ ] Object equality assertions for a kotlin class when a suiting constructor is found
- [ ] Object equality assertions for a java class when a suiting constructor is found (unfortunately unlikely because of parameter names lost at compilation)
- [ ] Publish library on maven central or jcenter
- [ ] Java syntax support
- [ ] Recursive support (eg: if a data-class property returns another data-class)
- [ ] Support for assertion frameworks other than assertJ and Truth?
