package com.example.todo.data.db

import androidx.room.TypeConverter
import org.joda.time.LocalDate

internal class LocalDateConverter {
    @TypeConverter
    fun toDate(dateString: String?): LocalDate? {
        return dateString.let {
            LocalDate.parse(it)
        }
    }

    @TypeConverter
    fun toDateString(date: LocalDate?): String? {
        return date?.toString()
    }
}