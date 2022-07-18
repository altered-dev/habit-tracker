package me.altered.habittracker.data

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    private val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime) = value.format(formatter)

    @TypeConverter
    fun toLocalDateTime(value: String) = LocalDateTime.parse(value, formatter)
}