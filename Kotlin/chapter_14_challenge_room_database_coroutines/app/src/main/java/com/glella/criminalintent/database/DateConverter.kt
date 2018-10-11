package com.glella.criminalintent.database

import android.arch.persistence.room.TypeConverter
import java.util.*


class DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return (if (date == null) null else date.time)!!.toLong()
    }
}