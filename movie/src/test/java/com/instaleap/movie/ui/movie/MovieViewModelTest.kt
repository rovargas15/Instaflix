package com.instaleap.movie.ui.movie

import com.instaleap.core.route.Category
import com.instaleap.core.route.Router
import com.instaleap.domain.model.DataBase
import com.instaleap.domain.model.Movie
import com.instaleap.domain.usecase.GetAllMovieUseCase
import com.instaleap.domain.usecase.GetMovieByCategoryUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
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
class MovieViewModelTest {
    private val dispatcher = UnconfinedTestDispatcher()

    @MockK
    lateinit var getMovieByCategoryUseCase: GetMovieByCategoryUseCase

    @MockK
    lateinit var getAllMovieUseCase: GetAllMovieUseCase

    private lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        viewModel =
            MovieViewModel(
                getMovieByCategoryUseCase,
                getAllMovieUseCase,
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

            Assert.assertEquals(state, MovieContract.UiStateMovie())
        }

    @Test
    fun `when fetchData is called, get movies and update state`() =
        runTest {
            val dataBase =
                mockk<DataBase<Movie>> {
                    every { results } returns listOf(mockk(), mockk(), mockk())
                }
            coEvery { getMovieByCategoryUseCase.invoke(Category.POPULAR) } returns
                Result.success(
                    dataBase,
                )
            coEvery { getMovieByCategoryUseCase.invoke(Category.TOP_RATED) } returns
                Result.success(
                    dataBase,
                )
            coEvery { getMovieByCategoryUseCase.invoke(Category.UPCOMING) } returns
                Result.success(
                    dataBase,
                )

            viewModel.fetchData()

            Assert.assertEquals(viewModel.uiState.value.listPopular, dataBase.results)
            Assert.assertEquals(viewModel.uiState.value.listTopRated, dataBase.results)
            Assert.assertEquals(viewModel.uiState.value.listUpcoming, dataBase.results)

            coVerify {
                getMovieByCategoryUseCase.invoke(Category.POPULAR)
                getMovieByCategoryUseCase.invoke(Category.TOP_RATED)
                getMovieByCategoryUseCase.invoke(Category.UPCOMING)
            }
        }

    @Test
    fun `when fetchData is called and get movie by category use case return error, update state with error`() =
        runTest {
            val moviePopular =
                mockk<Movie> {
                    every { category } returns Category.POPULAR
                }
            val movieTopRated =
                mockk<Movie> {
                    every { category } returns Category.TOP_RATED
                }

            val movieUpcoming =
                mockk<Movie> {
                    every { category } returns Category.UPCOMING
                }

            coEvery { getMovieByCategoryUseCase.invoke(Category.POPULAR) } returns
                Result.failure(
                    Exception(),
                )
            coEvery { getMovieByCategoryUseCase.invoke(Category.TOP_RATED) } returns
                Result.failure(
                    Exception(),
                )
            coEvery { getMovieByCategoryUseCase.invoke(Category.UPCOMING) } returns
                Result.failure(
                    Exception(),
                )
            val movies = listOf(moviePopular, movieTopRated, movieUpcoming)
            coEvery { getAllMovieUseCase.invoke() } returns flowOf(movies)

            viewModel.fetchData()

            Assert.assertEquals(viewModel.uiState.value.listPopular, listOf(moviePopular))
            Assert.assertEquals(viewModel.uiState.value.listTopRated, listOf(movieTopRated))
            Assert.assertEquals(viewModel.uiState.value.listUpcoming, listOf(movieUpcoming))

            coVerify {
                getMovieByCategoryUseCase.invoke(Category.POPULAR)
                getMovieByCategoryUseCase.invoke(Category.TOP_RATED)
                getMovieByCategoryUseCase.invoke(Category.UPCOMING)
                getAllMovieUseCase.invoke(Category.UPCOMING)
            }
        }

    @Test
    fun `when fetchData is called, data is not null`() =
        runTest {
            val viewmodel = spyk(viewModel)
            val uiState: MovieContract.UiStateMovie = mockk()
            every { viewmodel.uiState.value } returns uiState
            every { uiState.isEmpty() } returns false

            viewmodel.fetchData()

            confirmVerified(getMovieByCategoryUseCase, getAllMovieUseCase)
        }

    @Test
    fun `when onUiEvent Navigate is called, send effect Navigate`() =
        runTest {
            val route = mockk<Router>()
            viewModel.onUiEvent(MovieContract.UiEventMovie.Navigate(route))
            Assert.assertEquals(
                MovieContract.EffectMovie.Navigate(route),
                viewModel.effects.first(),
            )
        }

    @Test
    fun `when onUiEvent Refresh is called, fetch data`() =
        runTest {
            val dataBase =
                mockk<DataBase<Movie>> {
                    every { results } returns listOf(mockk(), mockk(), mockk())
                }
            coEvery { getMovieByCategoryUseCase.invoke(Category.POPULAR) } returns
                Result.success(
                    dataBase,
                )
            coEvery { getMovieByCategoryUseCase.invoke(Category.TOP_RATED) } returns
                Result.success(
                    dataBase,
                )
            coEvery { getMovieByCategoryUseCase.invoke(Category.UPCOMING) } returns
                Result.success(
                    dataBase,
                )

            viewModel.onUiEvent(MovieContract.UiEventMovie.Refresh)

            Assert.assertEquals(viewModel.uiState.value.listPopular, dataBase.results)
            Assert.assertEquals(viewModel.uiState.value.listTopRated, dataBase.results)
            Assert.assertEquals(viewModel.uiState.value.listUpcoming, dataBase.results)
        }

    @Test
    fun `when onUiEvent SnackBarDismissed is called, is error is false`() =
        runTest {
            viewModel.onUiEvent(MovieContract.UiEventMovie.SnackBarDismissed)
            Assert.assertFalse(viewModel.uiState.value.isError)
        }
}
