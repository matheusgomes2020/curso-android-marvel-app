package com.example.marvelapp.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.core.domain.model.Comic
import com.example.core.domain.model.Event
import com.example.core.usecase.GetCharacterCategoriesUseCase
import com.example.core.usecase.base.ResultStatus
import com.example.marvelapp.R
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.example.testing.model.ComicFactory
import com.example.testing.model.EventFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var instantExecutableRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var getCharactersCategoryUseCase: GetCharacterCategoriesUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<DetailViewModel.UiState>

    private val character = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val events = listOf(EventFactory().create(EventFactory.FakeEvent.FakeEvent1))

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(getCharactersCategoryUseCase)
        detailViewModel.uiState.observeForever(uiStateObserver)
    }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns success`() {
        runTest {
            // Arrange
            whenever(getCharactersCategoryUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            comics to events
                        )
                    )
                )

            // Act
            detailViewModel.getCharacterCategories(character.id)

            // Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Success>())


            val uiStateSuccess = detailViewModel.uiState.value as DetailViewModel.UiState.Success
            val categoriesParenList = uiStateSuccess.detailParentList

            assertEquals(2, categoriesParenList.size)
            assertEquals(
                R.string.details_comics_category,
                categoriesParenList[0].categoryStringResId
            )
            assertEquals(
                R.string.details_events_category,
                categoriesParenList[1].categoryStringResId
            )
        }
    }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only comics`() =
        runTest {
            // Arrange
            whenever(getCharactersCategoryUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            comics to emptyList()
                        )
                    )
                )

            // Act
            detailViewModel.getCharacterCategories(character.id)

            // Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Success>())


            val uiStateSuccess = detailViewModel.uiState.value as DetailViewModel.UiState.Success
            val categoriesParenList = uiStateSuccess.detailParentList

            assertEquals(1, categoriesParenList.size)
            assertEquals(
                R.string.details_comics_category,
                categoriesParenList[0].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only events`() =
        runTest {
            // Arrange
            whenever(getCharactersCategoryUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            emptyList<Comic>() to events
                        )
                    )
                )

            // Act
            detailViewModel.getCharacterCategories(character.id)

            // Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Success>())


            val uiStateSuccess = detailViewModel.uiState.value as DetailViewModel.UiState.Success
            val categoriesParenList = uiStateSuccess.detailParentList

            assertEquals(1, categoriesParenList.size)
            assertEquals(
                R.string.details_events_category,
                categoriesParenList[0].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Empty from UiState when get character categories returns an empty result list`() =
        runTest {
            // Arrange
            whenever(getCharactersCategoryUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            emptyList<Comic>() to emptyList()
                        )
                    )
                )

            // Act
            detailViewModel.getCharacterCategories(character.id)

            // Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Empty>())
        }

    @Test fun `should notify uiState with Error from UiState when get character categories returns an exception`() =
        runTest {
            // Arrange
            whenever(getCharactersCategoryUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Error(Throwable())
                    )
                )

            // Act
            detailViewModel.getCharacterCategories(character.id)

            // Assert
            verify(uiStateObserver).onChanged(isA<DetailViewModel.UiState.Error>())
        }

}



// Arrange - Preparar os dados e fazer os mocks necessários.

// Act - Aonde vai executar a função que vai ser testada.

// Assert - Aonde vai fazer as verificações, validações e assercões com o Mockito e JUnit.

