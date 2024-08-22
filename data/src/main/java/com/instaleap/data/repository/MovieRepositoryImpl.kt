package com.instaleap.data.repository

import com.instaleap.data.local.database.InstaflixDatabase
import com.instaleap.data.remote.datasource.MovieDataSource
import com.instaleap.data.remote.response.BaseResponse
import com.instaleap.data.remote.response.MovieResponse
import com.instaleap.data.util.BaseRepository
import com.instaleap.domain.model.DataBase
import com.instaleap.domain.model.Movie
import com.instaleap.domain.model.MovieDetail
import com.instaleap.domain.repository.MovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl @Inject constructor(
    private val dataSourceRemote: MovieDataSource,
    private val db: InstaflixDatabase,
) : BaseRepository(), MovieRepository {
    override suspend fun getMovies(category: String): Result<DataBase<Movie>> = launchResultSafe {
        saveMovies(dataSourceRemote.getMovies(category), category)
    }

    override suspend fun getMovieById(id: Int): Flow<Movie> =
        db.movieDao().getMovieById(id).map { resultsChange -> resultsChange.toDomain() }

    override suspend fun getMovieDetailById(id: Int): Result<MovieDetail> = launchResultSafe {
        dataSourceRemote.getMovieById(id).toDomain()
    }

    override suspend fun getSearchMovie(query: String): Result<DataBase<Movie>> = launchResultSafe {
        saveMovies(dataSourceRemote.getMoviesByQuery(query))
    }

    override suspend fun getFavoriteMovie(): Flow<List<Movie>> =
        db.movieDao().getAllAsFlow().map { resultsChange ->
            resultsChange.map { it.toDomain() }
        }

    override suspend fun updateMovie(movie: Movie) {
        db.movieDao().updateMovie(movie.isFavorite, movie.id)
    }

    override suspend fun getPaginatedMovies(
        category: String,
        pageSize: Int,
        page: Int,
    ): Result<DataBase<Movie>> = launchResultSafe {
        saveMovies(dataSourceRemote.getMovies(category, page), category)
    }

    private suspend fun saveMovies(
        response: BaseResponse<MovieResponse>,
        category: String = "",
    ): DataBase<Movie> {
        response.results.map { it.toEntity(category) }.also { movies ->
            db.movieDao().insertAll(movies)
        }
        return DataBase(
            page = response.page,
            results = response.results.map { it.toDomain() },
            totalPages = response.totalPages,
            totalResults = response.totalResults,
        )
    }
}
