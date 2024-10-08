package com.instaleap.data.remote.response

import com.instaleap.domain.model.Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    @SerialName("backdrops") val backdrops: List<PosterResponse>,
    @SerialName("id") val id: Int,
    @SerialName("logos") val logos: List<PosterResponse>,
    @SerialName("posters") val posters: List<PosterResponse>,
) {
    @Serializable
    data class PosterResponse(
        @SerialName("aspect_ratio") val aspectRatio: Double?,
        @SerialName("file_path") val filePath: String,
        @SerialName("height") val height: Int?,
        @SerialName("iso_639_1") val iso6391: String?,
        @SerialName("vote_average") val voteAverage: Double?,
        @SerialName("vote_count") val voteCount: Int?,
        @SerialName("width") val width: Int?,
    ) {
        fun toDomain() =
            Image.Poster(
                aspectRatio = aspectRatio,
                filePath = filePath,
                height = height,
                iso6391 = iso6391,
                voteAverage = voteAverage,
                voteCount = voteCount,
                width = width,
            )
    }

    fun toDomain() =
        Image(
            backdrops = backdrops.map { it.toDomain() },
            id = id,
            logos = logos.map { it.toDomain() },
            posters = posters.map { it.toDomain() },
        )
}
