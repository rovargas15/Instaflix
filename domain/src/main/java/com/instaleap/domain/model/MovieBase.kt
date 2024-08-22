package com.instaleap.domain.model

data class MovieBase(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int,
)
