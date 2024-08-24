package com.instaleap.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.instaleap.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(items: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE movieId = :id")
    fun getMovieById(id: Int): Flow<MovieEntity>

    @Query("SELECT * FROM MovieEntity WHERE isFavorite = 1")
    fun getAllAsFlow(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity")
    fun getAll(): Flow<List<MovieEntity>>

    @Query("UPDATE MovieEntity SET isFavorite=:isFavorite WHERE movieId = :id")
    suspend fun updateMovie(
        isFavorite: Boolean,
        id: Int,
    ): Int
}
