package com.instaleap.favorite.ui.favorite

import com.instaleap.core.route.Router
import com.instaleap.domain.model.Movie
import com.instaleap.domain.model.Tv
import com.instaleap.domain.usecase.GetFavoriteMovieUseCase
import com.instaleap.domain.usecase.GetFavoriteTvUseCase
import com.instaleap.favorite.ui.favorite.FavoriteContract.Effect
import com.instaleap.favorite.ui.favorite.FavoriteContract.UiEvent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {
    private val getFavoriteMovieUseCase = mockk<GetFavoriteMovieUseCase>()
    private val getFavoriteTvUseCase = mockk<GetFavoriteTvUseCase>()
    private val coroutineDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: FavoriteViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(coroutineDispatcher)
        viewModel =
            FavoriteViewModel(
                getFavoriteMovieUseCase,
                getFavoriteTvUseCase,
                coroutineDispatcher,
            )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchData should update state with favorite movies and tvs`() =
        runTest {
            val movies = listOf<Movie>(mockk())
            val tvs = listOf<Tv>(mockk())

            coEvery { getFavoriteMovieUseCase.invoke() } returns flowOf(movies)
            coEvery { getFavoriteTvUseCase.invoke() } returns flowOf(tvs)

            viewModel.fetchData()

            Assert.assertEquals(
                viewModel.uiState.value,
                FavoriteContract.UiState(
                    isLoading = false,
                    listMovies = movies,
                    listTvs = tvs,
                ),
            )
        }

    @Test
    fun `onUiEvent Navigate should send Effect Navigate`() =
        runTest {
            val router: Router = mockk()
            viewModel.onUiEvent(UiEvent.Navigate(router))

            assertEquals(Effect.Navigate(router), viewModel.effects.firstOrNull())
        }

    @Test
    fun `initialState should return UiState with default values`() {
        val initialState = viewModel.initialState()
        assertEquals(FavoriteContract.UiState(), initialState)
    }

    @Test
    fun `updateState should update the state`() =
        runTest {
            viewModel.updateState { copy(isLoading = true) }

            assertEquals(
                FavoriteContract.UiState(isLoading = true),
                viewModel.uiState.value,
            )
        }
}
