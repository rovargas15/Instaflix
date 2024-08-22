package com.instaleap.data.remote.response

import com.instaleap.domain.model.MovieDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguageResponse(
    @SerialName("english_name") val englishName: String,
    @SerialName("iso_639_1") val iso6391: String,
    @SerialName("name") val name: String,
) {
    fun toDomain() = MovieDetail.SpokenLanguage(
        englishName = englishName,
        iso6391 = iso6391,
        name = name,
    )
}