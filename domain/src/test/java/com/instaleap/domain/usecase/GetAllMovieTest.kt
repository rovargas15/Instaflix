package com.instaleap.domain.usecase

import com.instaleap.domain.model.Movie
import com.instaleap.domain.repository.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAllMovieTest {
    @MockK
    private lateinit var repository: MovieRepository

    @InjectMockKs
    private lateinit var getAllMovie: GetAllMovie

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Give success When invoke Then return movie list`() =
        runBlocking {
            // Given
            val movieList = mockk<List<Movie>>()
            coEvery { repository.getAllCache() } returns flowOf(movieList)

            // When

            val result = getAllMovie.invoke()

            // Then
            assertEquals(movieList, result.firstOrNull())
            coVerify { repository.getAllCache() }
        }
}
