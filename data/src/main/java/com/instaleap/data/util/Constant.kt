package com.instaleap.data.util

object Constant {
    object Api {
        const val MOVIE_BY_CATEGORY = "/3/movie/%s?language=es&page=%s"
        const val MOVIE_BY_ID = "/3/movie/%s?language=es"
        const val MOVIE_BY_QUERY = "/3/search/movie?query=%s?&language=es"

        const val TV_BY_CATEGORY = "/3/tv/%s?language=es&page=%s"
        const val TV_BY_ID = "/3/tv/%s?language=es"
        const val TV_BY_QUERY = "/3/search/tv?query=%s?&language=es"

        const val IMAGE_URL = "/%s/%s/images?language=es"
    }
}
