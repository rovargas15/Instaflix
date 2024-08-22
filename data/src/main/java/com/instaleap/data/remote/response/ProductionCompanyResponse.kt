package com.instaleap.data.remote.response

import com.instaleap.domain.model.ProductionCompany
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompanyResponse(
    @SerialName("id") val id: Int,
    @SerialName("logo_path") val logoPath: String?,
    @SerialName("name") val name: String,
    @SerialName("origin_country") val originCountry: String,
) {
    fun toDomain() = ProductionCompany(
        id = id,
        logoPath = logoPath,
        name = name,
        originCountry = originCountry,
    )
}
