package com.instaleap.data.remote.response

import com.instaleap.domain.model.ProductionCountry
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountryResponse(
    @SerialName("iso_3166_1") val iso31661: String,
    @SerialName("name") val name: String,
) {
    fun toDomain() =
        ProductionCountry(
            iso31661 = iso31661,
            name = name,
        )
}
