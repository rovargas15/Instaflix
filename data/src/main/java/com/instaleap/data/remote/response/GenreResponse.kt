package com.instaleap.data.remote.response

import com.instaleap.domain.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
) {
    fun toDomain() = Genre(id = id, name = name)
}