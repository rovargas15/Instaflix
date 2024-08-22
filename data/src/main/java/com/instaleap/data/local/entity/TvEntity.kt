package com.instaleap.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.instaleap.domain.model.Tv

@Entity
data class TvEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val name: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val category: String,
    val isFavorite: Int,
) {

    fun toDomain() = Tv(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds.toList(),
        originalLanguage = originalLanguage,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        voteAverage = voteAverage,
        voteCount = voteCount,
        isFavorite = isFavorite == 1,
        name = name,
        firstAirDate = firstAirDate,
        originalName = originalName,
        originCountry = originCountry,
    )
}

fun Tv.toEntity() = TvEntity(
    id = id,
    category = "",
    adult = adult,
    backdropPath = backdropPath,
    genreIds = genreIds,
    originalLanguage = originalLanguage,
    originalName = originalName,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    voteAverage = voteAverage,
    voteCount = voteCount,
    isFavorite = if (isFavorite) 1 else 0,
    name = name,
    firstAirDate = firstAirDate,
    originCountry = originCountry,
)
