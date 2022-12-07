package com.example.marvelapp.framework

import androidx.paging.PagingSource
import com.example.core.data.repository.CharacterRepository
import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.core.domain.model.Character
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(private val remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>) :
    CharacterRepository {

    override fun getCharacters(query: String): PagingSource<Int, Character> {
        return 
    }

}