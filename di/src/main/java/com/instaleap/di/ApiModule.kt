package com.instaleap.di

import com.instaleap.data.remote.api.MovieApi
import com.instaleap.data.remote.client.client
import com.instaleap.data.remote.datasource.MovieDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideHttpClient() = client

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.Default


}