package com.instaleap.data.local.entity

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListStringConvert {
    @TypeConverter
    fun fromStringArrayList(value: List<String>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String): List<String> {
        return try {
            Json.decodeFromString(value)
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}
