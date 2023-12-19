package com.example.marvelapp.framework.remote

import com.example.marvelapp.framework.network.MarvelApi
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.matheus.core.data.repository.CharactersRemoteDataSource

class RetrofitCharactersDataSource(
    private val marvelApi: MarvelApi
) : CharactersRemoteDataSource<DataWrapperResponse> {

    override suspend fun fetchCharacters(queries: Map<String, String>): DataWrapperResponse {
        marvelApi.getCharacters(queries)
    }
}