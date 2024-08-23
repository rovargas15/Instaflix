package com.instaleap.tv.di

import com.instaleap.domain.repository.TvRepository
import com.instaleap.domain.usecase.GetTvByCategory
import com.instaleap.tv.ui.tv.TvViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object TvModule {
    @Provides
    @ViewModelScoped
    fun getTvByCategoryProvider(repository: TvRepository) = GetTvByCategory(repository)

    @Provides
    @ViewModelScoped
    fun tvViewModelProvider(
        getTvByCategory: GetTvByCategory,
        coroutineDispatcher: CoroutineDispatcher,
    ) = TvViewModel(getTvByCategory, coroutineDispatcher)
}
