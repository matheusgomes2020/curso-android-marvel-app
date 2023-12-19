package com.example.marvelapp.framework

import androidx.paging.PagingSource
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.marvelapp.framework.paging.CharactersPagingSource
import com.matheus.core.data.repository.CharactersRemoteDataSource
import com.matheus.core.data.repository.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>
) : CharactersRepository{
    override fun getCharacters(query: String): CharactersPagingSource {
        return CharactersPagingSource( remoteDataSource, query )
    }
}