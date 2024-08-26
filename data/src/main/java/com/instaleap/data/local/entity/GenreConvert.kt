package com.instaleap.data.local.entity

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GenreConvert {
    @TypeConverter
    fun fromStringArrayList(value: List<Int>): String = Json.encodeToString(value)

    @TypeConverter
    fun toStringArrayList(value: String): List<Int> =
        try {
            Json.decodeFromString(value)
        } catch (ignore: Exception) {
            arrayListOf()
        }
}
