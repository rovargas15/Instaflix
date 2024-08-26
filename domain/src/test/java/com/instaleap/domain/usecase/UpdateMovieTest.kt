package com.instaleap.domain.usecase

import com.instaleap.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UpdateMovieTest {
    private val repository: MovieRepository = mockk()
    private val getMovieById = UpdateMovie(repository)

    @Test
    fun `Give tv When invoke Then nothing`() =
        runBlocking {
            // Given
            coEvery { repository.updateMovie(any()) } just runs

            // When
            getMovieById.invoke(mockk())

            // Then
            coVerify { repository.updateMovie(any()) }
        }
}
