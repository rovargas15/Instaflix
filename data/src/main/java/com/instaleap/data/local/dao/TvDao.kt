package com.instaleap.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.instaleap.data.local.entity.MovieEntity
import com.instaleap.data.local.entity.TvEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(items: List<TvEntity>)

    @Query("SELECT * FROM TvEntity WHERE id = :id")
    fun getTvById(id: Int): Flow<TvEntity>

    @Query("SELECT * FROM TvEntity WHERE isFavorite = 1")
    fun getAllAsFlow(): Flow<List<TvEntity>>

    @Query("UPDATE TvEntity SET isFavorite=:isFavorite WHERE id = :id")
    suspend fun updateTv(isFavorite: Boolean, id: Int): Int
}
