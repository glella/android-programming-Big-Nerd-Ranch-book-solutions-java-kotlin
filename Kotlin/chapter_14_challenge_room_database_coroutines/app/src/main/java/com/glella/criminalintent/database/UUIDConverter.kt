package com.glella.criminalintent.database

import android.arch.persistence.room.TypeConverter
import java.util.*

class UUIDConverter {

    @TypeConverter
    fun toString(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun toUUID(uuidString: String): UUID {
        return UUID.fromString(uuidString)
    }
}