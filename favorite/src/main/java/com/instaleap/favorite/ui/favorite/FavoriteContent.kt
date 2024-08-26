package com.instaleap.favorite.ui.favorite

import com.instaleap.domain.model.Movie
import com.instaleap.domain.model.Tv

data class FavoriteContent(
    val movies: List<Movie>,
    val tvs: List<Tv>
)