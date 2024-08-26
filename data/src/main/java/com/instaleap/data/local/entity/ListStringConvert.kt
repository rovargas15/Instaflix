package com.instaleap.data.local.entity

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListStringConvert {
    @TypeConverter
    fun fromStringArrayList(value: List<String>): String = Json.encodeToString(value)

    @TypeConverter
    fun toStringArrayList(value: String): List<String> =
        try {
            Json.decodeFromString(value)
        } catch (ignore: Exception) {
            arrayListOf()
        }
}
