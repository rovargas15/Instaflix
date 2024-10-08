package com.instaleap.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.instaleap.domain.model.Tv

@Entity
data class TvEntity(
    @PrimaryKey val id: Int,
    val adult: Boolean,
    val backdropPath: String?,
    val firstAirDate: String,
    @field:TypeConverters(GenreConvert::class) val genreIds: List<Int>,
    val name: String,
    @field:TypeConverters(ListStringConvert::class) val originCountry: List<String>,
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
    fun toDomain() =
        Tv(
            id = id,
            adult = adult,
            backdropPath = backdropPath ?: "",
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
            category = category,
        )
}
