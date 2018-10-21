package com.glella.criminalintent

import android.app.Application
import android.content.Context

class ApplicationContextProvider : Application() {

    init {
        instance = this
    }


    companion object {
        private var instance: ApplicationContextProvider? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }


    override fun onCreate() {
        super.onCreate()

        // Usage:
        // val AppContext: Context = ApplicationContextProvider.applicationContext()

    }


}