package io.ldavin.beltbraces.internal.analysis

import io.ldavin.beltbraces.internal.Analysis
import io.ldavin.beltbraces.internal.TestFixtures.constructor
import io.ldavin.beltbraces.internal.TestFixtures.partialAnalysis
import io.ldavin.beltbraces.internal.TestFixtures.property
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class AnalyserTest {

    @Mock
    internal lateinit var propertyFinder: PropertyFinder
    @Mock
    internal lateinit var constructorFinder: ConstructorFinder
    @Mock
    internal lateinit var partialAnalyser: PartialAnalyser
    @InjectMocks
    internal lateinit var analyser: Analyser

    @Test
    fun `analyse aggregates the data`() {
        class Subject

        // GIVEN
        val subject = Subject()

        val properties = listOf(property())
        val constructors = listOf(constructor())
        val partialAnalysis = partialAnalysis()
        given(propertyFinder.analyse(subject)).willReturn(properties)
        given(constructorFinder.analyse(subject, properties)).willReturn(constructors)
        given(partialAnalyser.analyse(subject, constructors)).willReturn(partialAnalysis)

        // WHEN
        val result = analyser.analyse(subject)

        // THEN
        assertThat(result).isEqualTo(
            Analysis(
                properties = properties,
                constructors = constructors,
                preferredConstructor = partialAnalysis.preferredConstructor,
                overrideEquals = partialAnalysis.overrideEquals,
                isKotlinDataClass = partialAnalysis.isKotlinDataClass
            )
        )
    }
}