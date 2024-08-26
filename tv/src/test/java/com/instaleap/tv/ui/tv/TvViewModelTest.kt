package com.instaleap.tv.ui.tv

import com.instaleap.core.route.Category
import com.instaleap.core.route.Router
import com.instaleap.domain.model.DataBase
import com.instaleap.domain.model.Tv
import com.instaleap.domain.usecase.GetAllTvUseCase
import com.instaleap.domain.usecase.GetTvByCategoryUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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
class TvViewModelTest {
    private val dispatcher = UnconfinedTestDispatcher()

    @MockK
    lateinit var getTvByCategoryUseCase: GetTvByCategoryUseCase

    @MockK
    lateinit var getAllTvUseCase: GetAllTvUseCase

    private lateinit var viewModel: TvViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(dispatcher)
        viewModel =
            TvViewModel(
                getTvByCategoryUseCase,
                getAllTvUseCase,
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

            Assert.assertEquals(state, TvContract.UiStateTv())
        }

    @Test
    fun `when fetchData is called, get tvs and update state`() =
        runTest {
            val dataBase =
                mockk<DataBase<Tv>> {
                    every { results } returns listOf(mockk(), mockk(), mockk())
                }
            coEvery { getTvByCategoryUseCase.invoke(Category.POPULAR) } returns
                Result.success(
                    dataBase,
                )
            coEvery { getTvByCategoryUseCase.invoke(Category.TOP_RATED) } returns
                Result.success(
                    dataBase,
                )
            coEvery { getTvByCategoryUseCase.invoke(Category.ON_THE_AIR) } returns
                Result.success(
                    dataBase,
                )

            viewModel.fetchData()

            Assert.assertEquals(viewModel.uiState.value.listPopular, dataBase.results)
            Assert.assertEquals(viewModel.uiState.value.listTopRated, dataBase.results)
            Assert.assertEquals(viewModel.uiState.value.listOnTheAir, dataBase.results)
        }

    @Test
    fun `when fetchData is called and get tv by category use case return error, update state with error`() =
        runTest {
            val tvPopular =
                mockk<Tv> {
                    every { category } returns Category.POPULAR
                }
            val tvTopRated =
                mockk<Tv> {
                    every { category } returns Category.TOP_RATED
                }

            val tvUpcoming =
                mockk<Tv> {
                    every { category } returns Category.ON_THE_AIR
                }

            coEvery { getTvByCategoryUseCase.invoke(Category.POPULAR) } returns
                Result.failure(
                    Exception(),
                )
            coEvery { getTvByCategoryUseCase.invoke(Category.TOP_RATED) } returns
                Result.failure(
                    Exception(),
                )
            coEvery { getTvByCategoryUseCase.invoke(Category.ON_THE_AIR) } returns
                Result.failure(
                    Exception(),
                )
            val tvs = listOf(tvPopular, tvTopRated, tvUpcoming)
            coEvery { getAllTvUseCase.invoke() } returns flowOf(tvs)

            viewModel.fetchData()

            Assert.assertFalse(viewModel.uiState.value.isError)
            Assert.assertEquals(viewModel.uiState.value.listPopular, listOf(tvPopular))
            Assert.assertEquals(viewModel.uiState.value.listTopRated, listOf(tvTopRated))
            Assert.assertEquals(viewModel.uiState.value.listOnTheAir, listOf(tvUpcoming))
        }

    @Test
    fun `when onUiEvent Navigate is called, send effect Navigate`() =
        runTest {
            val route = mockk<Router>()
            viewModel.onUiEvent(TvContract.UiEventTv.Navigate(route))
            Assert.assertEquals(
                TvContract.EffectTv.Navigate(route),
                viewModel.effects.first(),
            )
        }

    @Test
    fun `when onUiEvent Refresh is called, fetch data`() =
        runTest {
            val dataBase =
                mockk<DataBase<Tv>> {
                    every { results } returns listOf(mockk(), mockk(), mockk())
                }
            coEvery { getTvByCategoryUseCase.invoke(Category.POPULAR) } returns
                Result.success(
                    dataBase,
                )
            coEvery { getTvByCategoryUseCase.invoke(Category.TOP_RATED) } returns
                Result.success(
                    dataBase,
                )
            coEvery { getTvByCategoryUseCase.invoke(Category.ON_THE_AIR) } returns
                Result.success(
                    dataBase,
                )

            viewModel.onUiEvent(TvContract.UiEventTv.Refresh)

            Assert.assertEquals(viewModel.uiState.value.listPopular, dataBase.results)
            Assert.assertEquals(viewModel.uiState.value.listTopRated, dataBase.results)
            Assert.assertEquals(viewModel.uiState.value.listOnTheAir, dataBase.results)
        }
}
