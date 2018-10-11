package com.glella.criminalintent.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.glella.criminalintent.Crime

@Database(entities = [(Crime::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun crimesListDAO(): CrimesListDAO
}
