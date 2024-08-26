package com.instaleap.domain.usecase

import com.instaleap.domain.model.Movie
import com.instaleap.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetFavoriteMovieUseCaseTest {
    private val repository: MovieRepository = mockk()
    private val getFavoriteMovieUseCase = GetFavoriteMovieUseCase(repository)

    @Test
    fun `Give success When invoke Then return movie`() =
        runBlocking {
            // Given
            val movieList = mockk<List<Movie>>()
            coEvery { repository.getFavoriteMovie() } returns flowOf(movieList)

            // When

            val result = getFavoriteMovieUseCase.invoke()

            // Then
            assertEquals(movieList, result.firstOrNull())
            coVerify { repository.getFavoriteMovie() }
        }
}
