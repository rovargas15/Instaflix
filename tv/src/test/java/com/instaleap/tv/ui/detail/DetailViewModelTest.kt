package com.instaleap.tv.ui.detail

import com.instaleap.core.route.Router
import com.instaleap.domain.model.Image
import com.instaleap.domain.model.Tv
import com.instaleap.domain.model.TvDetail
import com.instaleap.domain.usecase.GetImageByIdUseCase
import com.instaleap.domain.usecase.GetTvByIdUseCase
import com.instaleap.domain.usecase.GetTvDetailByIdUseCase
import com.instaleap.domain.usecase.UpdateTvUseCase
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
    lateinit var getDetailUseCase: GetTvDetailByIdUseCase

    @MockK
    lateinit var getTvByIdUseCase: GetTvByIdUseCase

    @MockK
    lateinit var updateTvUseCase: UpdateTvUseCase

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
                getTvByIdUseCase,
                updateTvUseCase,
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
            val tvId = 1
            val image = mockk<Image>()
            coEvery {
                getImageByIdUseCase.invoke(
                    tvId,
                    PATH_IMAGE,
                )
            } returns Result.success(image)
            val tvDetail = mockk<TvDetail>()
            coEvery { getDetailUseCase.invoke(tvId) } returns Result.success(tvDetail)
            val tv = mockk<Tv>()
            coEvery { getTvByIdUseCase.invoke(tvId) } returns flowOf(tv)

            viewModel.fetchData(Router.DetailTv(tvId, ""))

            Assert.assertEquals(viewModel.uiState.value.tvDetail, tvDetail)
            Assert.assertEquals(viewModel.uiState.value.image, image)
            Assert.assertEquals(viewModel.uiState.value.tv, tv)
        }

    @Test
    fun `when fetchImage is called and get image use case return error, update state with error`() =
        runTest {
            val tvId = 1
            coEvery {
                getImageByIdUseCase.invoke(
                    tvId,
                    any(),
                )
            } returns Result.failure(Exception())

            viewModel.fetchImage(tvId)

            Assert.assertEquals(viewModel.uiState.value.isError, true)
        }

    @Test
    fun `when fetchDetail is called and get detail use case return error, update state with error`() =
        runTest {
            val tvId = 1
            coEvery { getDetailUseCase.invoke(tvId) } returns Result.failure(Exception())

            viewModel.fetchDetail(tvId)

            Assert.assertEquals(viewModel.uiState.value.isError, true)
        }

    @Test
    fun `when fetchTv is called, get tv detail and update state`() =
        runTest {
            val tvId = 1
            val tv = mockk<Tv>()
            coEvery { getTvByIdUseCase.invoke(tvId) } returns flowOf(tv)

            viewModel.fetchTv(tvId)

            Assert.assertEquals(viewModel.uiState.value.tv, tv)
        }

    @Test
    fun `when updateTv is called, update tv`() =
        runTest {
            val tv = mockk<Tv>()
            every { tv.isFavorite } returns false

            coEvery { updateTvUseCase.invoke(tv.copy(isFavorite = true)) } just runs

            viewModel.update(tv)

            coVerify { updateTvUseCase.invoke(tv.copy(isFavorite = true)) }
        }

    @Test
    fun `when onUiEvent ToggleFavorite is called with favorite tv, update state with showDialog true`() =
        runTest {
            val tv = mockk<Tv>()
            every { tv.isFavorite } returns true

            viewModel.onUiEvent(DetailContract.UiEventDetail.ToggleFavorite(tv))

            Assert.assertTrue(viewModel.uiState.value.isShowDialog)
        }

    @Test
    fun `when onUiEvent ToggleFavorite is called with no favorite tv, update tv`() =
        runTest {
            val tv = mockk<Tv>()
            every { tv.isFavorite } returns false

            coEvery { updateTvUseCase.invoke(tv.copy(isFavorite = true)) } just runs

            viewModel.onUiEvent(DetailContract.UiEventDetail.ToggleFavorite(tv))

            coVerify { updateTvUseCase.invoke(tv.copy(isFavorite = true)) }
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
    fun `when onUiEvent RemoveFavorite is called, update tv and update state with showDialog false`() =
        runTest {
            val tv = mockk<Tv>()
            every { tv.isFavorite } returns false

            coEvery { updateTvUseCase.invoke(tv.copy(isFavorite = true)) } just runs

            viewModel.onUiEvent(DetailContract.UiEventDetail.RemoveFavorite(tv))

            coVerify { updateTvUseCase.invoke(tv.copy(isFavorite = true)) }
            Assert.assertEquals(viewModel.uiState.value.isShowDialog, false)
        }

    @Test
    fun `when onUiEvent DismissDialog is called, update state with showDialog false`() =
        runTest {
            viewModel.onUiEvent(DetailContract.UiEventDetail.DismissDialog)

            Assert.assertEquals(viewModel.uiState.value.isShowDialog, false)
        }
}
