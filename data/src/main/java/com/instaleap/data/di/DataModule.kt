package com.instaleap.data.di

import android.content.Context
import androidx.room.Room
import com.instaleap.data.local.dao.MovieDao
import com.instaleap.data.local.dao.TvDao
import com.instaleap.data.local.database.InstaflixDatabase
import com.instaleap.data.local.database.InstaflixDatabase.Companion.DB_FILE_NAME
import com.instaleap.data.remote.client.client
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient = client

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun createDatabaseProvide(@ApplicationContext context: Context): InstaflixDatabase {
        val dbFile = context.getDatabasePath(DB_FILE_NAME)
        return Room.databaseBuilder(
            context = context,
            name = dbFile.absolutePath,
            klass = InstaflixDatabase::class.java
        ).build()
    }

    @Provides
    fun provideMovieDao(database: InstaflixDatabase): MovieDao = database.movieDao()

    @Provides
    fun provideTvDao(database: InstaflixDatabase): TvDao = database.tvDao()
}