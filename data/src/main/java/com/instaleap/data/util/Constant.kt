package com.instaleap.data.util

object Constant {
    object Api {
        const val MOVIE_BY_CATEGORY = "/movie/%s?language=es&page=%s"
        const val MOVIE_BY_ID = "/movie/%s?language=es"
        const val MOVIE_BY_QUERY = "/search/movie?query=%s?&language=es"

        const val TV_BY_CATEGORY = "/tv/%s?language=es&page=%s"
        const val TV_BY_ID = "/tv/%s?language=es"
        const val TV_BY_QUERY = "/search/tv?query=%s?&language=es"

        const val IMAGE_URL = "/%s/%s/images?language=es"
    }
}
