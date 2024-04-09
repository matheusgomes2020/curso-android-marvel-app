package com.example.marvelapp.framework

import com.example.marvelapp.framework.paging.CharactersPagingSource
import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.core.data.repository.CharactersRepository
import com.example.core.domain.model.Comic
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource
) : CharactersRepository{
    override fun getCharacters(query: String): CharactersPagingSource {
        return CharactersPagingSource( remoteDataSource, query )
    }

    override suspend fun getComics(characterId: Int): List<Comic> {
        return remoteDataSource.fetchComics(characterId)
    }
}