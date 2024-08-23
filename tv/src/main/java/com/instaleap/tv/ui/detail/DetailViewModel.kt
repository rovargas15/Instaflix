package com.instaleap.tv.ui.detail

import androidx.lifecycle.ViewModel
import com.instaleap.domain.usecase.GetTvDetailById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
        private val getTvDetailById: GetTvDetailById,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) : ViewModel()
