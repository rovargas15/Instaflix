package com.instaleap.data.remote.response

import com.instaleap.domain.model.TvDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvDetailResponse(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("created_by")
    val createdBy: List<CreatedByResponse>,
    @SerialName("episode_run_time")
    val episodeRunTime: List<Int>,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("genres")
    val genres: List<GenreResponse>,
    @SerialName("homepage")
    val homepage: String,
    @SerialName("id")
    val id: Int,
    @SerialName("in_production")
    val inProduction: Boolean,
    @SerialName("languages")
    val languages: List<String>,
    @SerialName("last_air_date")
    val lastAirDate: String,
    @SerialName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAirResponse,
    @SerialName("name")
    val name: String,
    @SerialName("networks")
    val networks: List<NetworkResponse>,
    @SerialName("next_episode_to_air")
    val nextEpisodeToAir: NextEpisodeToAirResponse?,
    @SerialName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerialName("origin_country")
    val originCountry: List<String>,
    @SerialName("original_language")
    val originalLanguage: String,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyResponse>,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryResponse>,
    @SerialName("seasons")
    val seasons: List<SeasonResponse>,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageResponse>,
    @SerialName("status")
    val status: String,
    @SerialName("tagline")
    val tagline: String,
    @SerialName("type")
    val type: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
) {
    fun toDomain() =
        TvDetail(
            adult = adult,
            backdropPath = backdropPath,
            createdBy = createdBy.map { it.toDomain() },
            episodeRunTime = episodeRunTime,
            firstAirDate = firstAirDate,
            name = name,
            originCountry = originCountry,
            originalName = originalName,
            productionCompanies = productionCompanies.map { it.toDomain() },
            productionCountries = productionCountries.map { it.toDomain() },
            lastEpisodeToAir = lastEpisodeToAir.toDomain(),
            nextEpisodeToAir = nextEpisodeToAir?.toDomain(),
            numberOfEpisodes = numberOfEpisodes,
            originalLanguage = originalLanguage,
            id = id,
            type = type,
            numberOfSeasons = numberOfSeasons,
            spokenLanguages = spokenLanguages.map { it.toDomain() },
            genres = genres.map { it.toDomain() },
            status = status,
            homepage = homepage,
            networks = networks.map { it.toDomain() },
            overview = overview,
            seasons = seasons.map { it.toDomain() },
            tagline = tagline,
            languages = languages,
            voteCount = voteCount,
            inProduction = inProduction,
            popularity = popularity,
            posterPath = posterPath,
            lastAirDate = lastAirDate,
            voteAverage = voteAverage,
        )

    @Serializable
    data class CreatedByResponse(
        @SerialName("credit_id") val creditId: String,
        @SerialName("gender") val gender: Int,
        @SerialName("id") val id: Int,
        @SerialName("name") val name: String,
        @SerialName("original_name") val originalName: String,
    ) {
        fun toDomain() =
            TvDetail.CreatedBy(
                creditId = creditId,
                gender = gender,
                id = id,
                name = name,
                originalName = originalName,
            )
    }

    @Serializable
    data class LastEpisodeToAirResponse(
        @SerialName("air_date") val airDate: String,
        @SerialName("episode_number") val episodeNumber: Int,
        @SerialName("episode_type") val episodeType: String,
        @SerialName("id") val id: Int,
        @SerialName("name") val name: String,
        @SerialName("overview") val overview: String,
        @SerialName("production_code") val productionCode: String,
        @SerialName("runtime") val runtime: Int?,
        @SerialName("season_number") val seasonNumber: Int,
        @SerialName("show_id") val showId: Int,
        @SerialName("still_path") val stillPath: String?,
        @SerialName("vote_average") val voteAverage: Double,
        @SerialName("vote_count") val voteCount: Int,
    ) {
        fun toDomain() =
            TvDetail.LastEpisodeToAir(
                airDate = airDate,
                episodeNumber = episodeNumber,
                episodeType = episodeType,
                id = id,
                name = name,
                overview = overview,
                productionCode = productionCode,
                runtime = runtime,
                seasonNumber = seasonNumber,
                showId = showId,
                stillPath = stillPath,
                voteAverage = voteAverage,
                voteCount = voteCount,
            )
    }

    @Serializable
    data class NetworkResponse(
        @SerialName("id") val id: Int,
        @SerialName("logo_path") val logoPath: String,
        @SerialName("name") val name: String,
        @SerialName("origin_country") val originCountry: String,
    ) {
        fun toDomain() =
            TvDetail.Network(
                id = id,
                logoPath = logoPath,
                name = name,
                originCountry = originCountry,
            )
    }

    @Serializable
    data class NextEpisodeToAirResponse(
        @SerialName("air_date") val airDate: String,
        @SerialName("episode_number") val episodeNumber: Int,
        @SerialName("episode_type") val episodeType: String,
        @SerialName("id") val id: Int,
        @SerialName("name") val name: String,
        @SerialName("overview") val overview: String,
        @SerialName("production_code") val productionCode: String,
        @SerialName("runtime") val runtime: Int?,
        @SerialName("season_number") val seasonNumber: Int,
        @SerialName("show_id") val showId: Int,
        @SerialName("still_path") val stillPath: String?,
        @SerialName("vote_average") val voteAverage: Double,
        @SerialName("vote_count") val voteCount: Double,
    ) {
        fun toDomain() =
            TvDetail.NextEpisodeToAir(
                airDate = airDate,
                episodeNumber = episodeNumber,
                episodeType = episodeType,
                id = id,
                name = name,
                overview = overview,
                productionCode = productionCode,
                runtime = runtime,
                seasonNumber = seasonNumber,
                showId = showId,
                stillPath = stillPath,
                voteAverage = voteAverage,
                voteCount = voteCount,
            )
    }

    @Serializable
    data class SeasonResponse(
        @SerialName("air_date") val airDate: String,
        @SerialName("episode_count") val episodeCount: Int,
        @SerialName("id") val id: Int,
        @SerialName("name") val name: String,
        @SerialName("overview") val overview: String,
        @SerialName("poster_path") val posterPath: String,
        @SerialName("season_number") val seasonNumber: Int,
        @SerialName("vote_average") val voteAverage: Double,
    ) {
        fun toDomain() =
            TvDetail.Season(
                airDate = airDate,
                episodeCount = episodeCount,
                id = id,
                name = name,
                overview = overview,
                posterPath = posterPath,
                seasonNumber = seasonNumber,
                voteAverage = voteAverage,
            )
    }
}
