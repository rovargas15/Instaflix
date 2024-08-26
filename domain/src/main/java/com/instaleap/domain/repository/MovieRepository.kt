package com.instaleap.domain.repository

import com.instaleap.domain.model.DataBase
import com.instaleap.domain.model.Movie
import com.instaleap.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(
        category: String,
        page: Int,
    ): Result<DataBase<Movie>>

    suspend fun getMovieById(id: Int): Flow<Movie>

    suspend fun getMovieDetailById(id: Int): Result<MovieDetail>

    suspend fun getSearchMovie(query: String): Result<DataBase<Movie>>

    suspend fun getFavoriteMovie(): Flow<List<Movie>>

    suspend fun updateMovie(movie: Movie)

    suspend fun getAllCache(): Flow<List<Movie>>
}
