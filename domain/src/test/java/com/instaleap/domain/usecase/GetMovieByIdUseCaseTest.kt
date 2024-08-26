package com.instaleap.domain.usecase

import com.instaleap.domain.model.Movie
import com.instaleap.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetMovieByIdUseCaseTest {
    private val repository: MovieRepository = mockk()
    private val getMovieByIdUseCase = GetMovieByIdUseCase(repository)

    @Test
    fun `Give success When invoke Then return movie list`() =
        runBlocking {
            // Given
            coEvery { repository.getMovieById(1) } returns flowOf(mockk())

            // When
            val result = getMovieByIdUseCase.invoke(1)

            // Then
            assert(result.firstOrNull() is Movie)
            coVerify { repository.getMovieById(1) }
        }
}
