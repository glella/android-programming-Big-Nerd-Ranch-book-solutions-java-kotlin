package com.glella.criminalintent

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.glella.criminalintent.database.AppDatabase

class ApplicationContextProvider : Application() {

    init {
        instance = this
    }


    companion object {


        var database: AppDatabase? = null

        private var instance: ApplicationContextProvider? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

        // Usage: Needed for SQLite from Object CrimeLab
        // val AppContext: Context = ApplicationContextProvider.applicationContext()
    }


    override fun onCreate() {
        super.onCreate()

        //ApplicationContextProvider.database = Room.databaseBuilder(this, AppDatabase::class.java, "crimeBase.db").build()
        // Should not allow queries in main thread, but as a quick test of Room works
        ApplicationContextProvider.database = Room.databaseBuilder(this, AppDatabase::class.java, "crimeBasev2.db").allowMainThreadQueries().build()




    }


}