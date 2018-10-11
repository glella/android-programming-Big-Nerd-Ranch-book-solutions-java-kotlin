package com.glella.criminalintent

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.glella.criminalintent.database.DateConverter
import com.glella.criminalintent.database.UUIDConverter
import java.util.*

@Entity(tableName = "crimes_table")
class Crime {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Long = 0

    @ColumnInfo(name="uuid")
    @TypeConverters(UUIDConverter::class)
    var mId: UUID = UUID.randomUUID()

    @ColumnInfo(name="title")
    var mTitle: String? = null

    @ColumnInfo(name="date")
    @TypeConverters(DateConverter::class)
    var mDate: Date? = Date()

    @ColumnInfo(name="solved")
    var mSolved: Boolean = false
}



