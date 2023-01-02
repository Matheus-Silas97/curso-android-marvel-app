package com.example.core.usecase

import androidx.paging.PagingConfig
import com.example.core.data.repository.CharacterRepository
import com.matheussilas97.testing.MainCoroutineRule
import com.matheussilas97.testing.model.CharacterFactory
import com.matheussilas97.testing.pagingsource.PagingSourceFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var repository: CharacterRepository

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    private val hero = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)

    private val fakePagingSource = PagingSourceFactory().create(listOf(hero))

    @Before
    fun setUp() {
        getCharactersUseCase = GetCharactersUseCaseImpl(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runBlockingTest {
            whenever(repository.getCharacters("")).thenReturn(fakePagingSource)


            val result = getCharactersUseCase.invoke(
                GetCharactersUseCase.GetCharactersParams(
                    "",
                    PagingConfig(20)
                )
            )

            verify(repository).getCharacters("")

            assertNotNull(result.first())
        }

}