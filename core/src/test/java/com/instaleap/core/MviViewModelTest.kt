package com.instaleap.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MviViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initialState should be set correctly`() =
        runTest {
            val viewModel = TestViewModel()
            assertEquals(viewModel.initialState(), viewModel.uiState.value)
        }

    @Test
    fun `updateState should update the uiState`() =
        runTest {
            val viewModel = TestViewModel()
            val newState = TestUiState(count = 10)
            viewModel.updateState { newState }
            assertEquals(newState, viewModel.uiState.value)
        }

    @Test
    fun `sendEffect should emit the effect`() =
        runTest {
            val viewModel = TestViewModel()
            val effect = TestEffect.Effect1
            viewModel.sendEffect(effect)
            Assert.assertEquals(effect, viewModel.effects.firstOrNull())
        }

    @Test
    fun `onUiEvent should update the uiState based on the event`() =
        runTest {
            val viewModel = TestViewModel()
            viewModel.onUiEvent(TestUiEvent.Event1)
            assertEquals(TestUiState(count = 1), viewModel.uiState.value)
        }

    // Test classes for UiState, UiEvent, and Effect
    data class TestUiState(
        val count: Int = 0,
    )

    sealed class TestUiEvent {
        object Event1 : TestUiEvent()
    }

    sealed class TestEffect {
        object Effect1 : TestEffect()
    }

    // Test ViewModel implementation
    class TestViewModel : MviViewModel<TestUiState, TestUiEvent, TestEffect>() {
        override fun initialState(): TestUiState = TestUiState()

        override fun onUiEvent(uiEvent: TestUiEvent) {
            when (uiEvent) {
                TestUiEvent.Event1 -> updateState { copy(count = count + 1) }
            }
        }
    }
}
