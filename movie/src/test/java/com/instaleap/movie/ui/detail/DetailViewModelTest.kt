package com.instaleap.movie.ui.detail

import com.instaleap.domain.model.Image
import com.instaleap.domain.model.Movie
import com.instaleap.domain.model.MovieDetail
import com.instaleap.domain.usecase.GetImageByIdUseCase
import com.instaleap.domain.usecase.GetMovieByIdUseCase
import com.instaleap.domain.usecase.GetMovieDetailByIdUseCase
import com.instaleap.domain.usecase.UpdateMovieUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {
    private val dispatcher = UnconfinedTestDispatcher()

    @MockK
    lateinit var getDetailUseCase: GetMovieDetailByIdUseCase

    @MockK
    lateinit var getMovieByIdUseCase: GetMovieByIdUseCase

    @MockK
    lateinit var updateMovieUseCase: UpdateMovieUseCase

    @MockK
    lateinit var getImageByIdUseCase: GetImageByIdUseCase

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        viewModel =
            DetailViewModel(
                getDetailUseCase,
                getMovieByIdUseCase,
                updateMovieUseCase,
                getImageByIdUseCase,
                dispatcher,
            )
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel is created, set initial state`() =
        runTest {
            val state = viewModel.uiState.value

            Assert.assertEquals(state, DetailContract.UiStateDetail())
        }

    @Test
    fun `when fetchData is called, get image and update state`() =
        runTest {
            val movieId = 1
            val image = mockk<Image>()
            coEvery {
                getImageByIdUseCase.invoke(
                    movieId,
                    PATH_IMAGE,
                )
            } returns Result.success(image)
            val movieDetail = mockk<MovieDetail>()
            coEvery { getDetailUseCase.invoke(movieId) } returns Result.success(movieDetail)
            val movie = mockk<Movie>()
            coEvery { getMovieByIdUseCase.invoke(movieId) } returns flowOf(movie)

            viewModel.fetchData(1)

            Assert.assertEquals(viewModel.uiState.value.movieDetail, movieDetail)
            Assert.assertEquals(viewModel.uiState.value.image, image)
            Assert.assertEquals(viewModel.uiState.value.movie, movie)
        }

    @Test
    fun `when fetchImage is called and get image use case return error, update state with error`() =
        runTest {
            val movieId = 1
            coEvery {
                getImageByIdUseCase.invoke(
                    movieId,
                    any(),
                )
            } returns Result.failure(Exception())

            viewModel.fetchImage(movieId)

            Assert.assertEquals(viewModel.uiState.value.isError, true)
        }

    @Test
    fun `when fetchDetail is called and get detail use case return error, update state with error`() =
        runTest {
            val movieId = 1
            coEvery { getDetailUseCase.invoke(movieId) } returns Result.failure(Exception())

            viewModel.fetchDetail(movieId)

            Assert.assertEquals(viewModel.uiState.value.isError, true)
        }

    @Test
    fun `when fetchMovie is called, get movie detail and update state`() =
        runTest {
            val movieId = 1
            val movie = mockk<Movie>()
            coEvery { getMovieByIdUseCase.invoke(movieId) } returns flowOf(movie)

            viewModel.fetchMovie(movieId)

            Assert.assertEquals(viewModel.uiState.value.movie, movie)
        }

    @Test
    fun `when updateMovie is called, update movie`() =
        runTest {
            val movie = mockk<Movie>()
            every { movie.isFavorite } returns false

            coEvery { updateMovieUseCase.invoke(movie.copy(isFavorite = true)) } just runs

            viewModel.updateMovie(movie)

            coVerify { updateMovieUseCase.invoke(movie.copy(isFavorite = true)) }
        }

    @Test
    fun `when onUiEvent ToggleFavorite is called with favorite movie, update state with showDialog true`() =
        runTest {
            val movie = mockk<Movie>()
            every { movie.isFavorite } returns true

            viewModel.onUiEvent(DetailContract.UiEventDetail.ToggleFavorite(movie))

            Assert.assertTrue(viewModel.uiState.value.isShowDialog)
        }

    @Test
    fun `when onUiEvent ToggleFavorite is called with no favorite movie, update movie`() =
        runTest {
            val movie = mockk<Movie>()
            every { movie.isFavorite } returns false

            coEvery { updateMovieUseCase.invoke(movie.copy(isFavorite = true)) } just runs

            viewModel.onUiEvent(DetailContract.UiEventDetail.ToggleFavorite(movie))

            coVerify { updateMovieUseCase.invoke(movie.copy(isFavorite = true)) }
        }

    @Test
    fun `when onUiEvent NavigateToBack is called, send effect NavigateToBack`() =
        runTest {
            viewModel.onUiEvent(DetailContract.UiEventDetail.NavigateToBack)

            Assert.assertEquals(
                DetailContract.EffectDetail.NavigateToBack,
                viewModel.effects.first(),
            )
        }

    @Test
    fun `when onUiEvent RemoveFavorite is called, update movie and update state with showDialog false`() =
        runTest {
            val movie = mockk<Movie>()
            every { movie.isFavorite } returns false

            coEvery { updateMovieUseCase.invoke(movie.copy(isFavorite = true)) } just runs

            viewModel.onUiEvent(DetailContract.UiEventDetail.RemoveFavorite(movie))

            coVerify { updateMovieUseCase.invoke(movie.copy(isFavorite = true)) }
            Assert.assertEquals(viewModel.uiState.value.isShowDialog, false)
        }

    @Test
    fun `when onUiEvent DismissDialog is called, update state with showDialog false`() =
        runTest {
            viewModel.onUiEvent(DetailContract.UiEventDetail.DismissDialog)

            Assert.assertEquals(viewModel.uiState.value.isShowDialog, false)
        }
}
