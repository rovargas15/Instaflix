package com.instaleap.domain.model

data class DataBase<R>(
    val page: Int,
    val results: List<R>,
    val totalPages: Int,
    val totalResults: Int,
)
