package com.instaleap.data.remote.response

import com.instaleap.data.local.entity.TvEntity
import com.instaleap.domain.model.Tv
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvResponse(
    @SerialName("adult") val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("first_air_date") val firstAirDate: String,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("origin_country") val originCountry: List<String>,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_name") val originalName: String,
    @SerialName("overview") val overview: String,
    @SerialName("popularity") val popularity: Double,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
) {
    fun toEntity(category: String) =
        TvEntity(
            adult = adult,
            backdropPath = backdropPath ?: "",
            firstAirDate = firstAirDate,
            genreIds = genreIds,
            id = id,
            name = name,
            originCountry = originCountry,
            originalName = originalName,
            originalLanguage = originalLanguage,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            voteAverage = voteAverage,
            voteCount = voteCount,
            isFavorite = 0,
            category = category,
        )

    fun toDomain() =
        Tv(
            adult = adult,
            backdropPath = backdropPath ?: "",
            firstAirDate = firstAirDate,
            genreIds = genreIds,
            id = id,
            name = name,
            originCountry = originCountry,
            originalName = originalName,
            originalLanguage = originalLanguage,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            voteAverage = voteAverage,
            voteCount = voteCount,
            isFavorite = false,
            category = "",
        )
}
