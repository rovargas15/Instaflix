package com.instaleap.presentation.movie.detail

import androidx.lifecycle.ViewModel
import com.instaleap.domain.usecase.GetMovieDetailById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
        private val getDetailUseCase: GetMovieDetailById,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) : ViewModel()
