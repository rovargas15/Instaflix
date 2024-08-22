package com.instaleap.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.instaleap.data.local.dao.MovieDao
import com.instaleap.data.local.dao.TvDao
import com.instaleap.data.local.entity.MovieEntity
import com.instaleap.data.local.entity.TvEntity

@Database(entities = [MovieEntity::class, TvEntity::class], version = 1)
abstract class InstaflixDatabase : RoomDatabase(), DB {
    abstract fun movieDao(): MovieDao

    abstract fun tvDao(): TvDao

    override fun clearAllTables() {}

    companion object {
        const val DB_FILE_NAME = "movies_database.db"
    }
}

// temporal hack
interface DB {
    fun clearAllTables() {}
}
