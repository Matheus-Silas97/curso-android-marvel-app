package com.example.marvelapp.framework.paging

import androidx.paging.PagingSource
import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.core.domain.model.Character
import com.example.factory.response.DataWrapperResponseFactory
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.matheussilas97.testing.MainCoroutineRule
import com.matheussilas97.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharacterPagingSourceTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>

    private val dataWrapperResponseFactory = DataWrapperResponseFactory()

    private val characterFactory = CharacterFactory()

    private lateinit var charactersPagingSource: CharacterPagingSource

    @Before
    fun setUp() {
        charactersPagingSource = CharacterPagingSource(remoteDataSource, "")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return a success load result when load is called`() = runBlockingTest {
        // Arrange
        whenever(remoteDataSource.fetchCharacters(any())).thenReturn(dataWrapperResponseFactory.create())

        // Act
        val result = charactersPagingSource.load(
            PagingSource.LoadParams.Refresh(key = null, loadSize = 2, placeholdersEnabled = false)
        )

        // Assert
        val expected = listOf(
            characterFactory.create(CharacterFactory.Hero.ThreeDMan),
            characterFactory.create(CharacterFactory.Hero.ABomb)
        )
        assertEquals(
            PagingSource.LoadResult.Page(data = expected, prevKey = null, nextKey = 20),
            result
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return a error load result when load is called`() = runBlockingTest {
        // arrange
        val exception = java.lang.RuntimeException()
        whenever(remoteDataSource.fetchCharacters(any())).thenThrow(exception)

        //act
        val result = charactersPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                false
            )
        )

        // Assert
        assertEquals(PagingSource.LoadResult.Error<Int, Character>(exception), result)
    }


}