package com.matheus.core.data.repository

import androidx.paging.PagingSource
import com.matheus.core.domain.model.Character

interface CharactersRepository {

    fun getCharacters( query: String ) : PagingSource<Int, Character>

}