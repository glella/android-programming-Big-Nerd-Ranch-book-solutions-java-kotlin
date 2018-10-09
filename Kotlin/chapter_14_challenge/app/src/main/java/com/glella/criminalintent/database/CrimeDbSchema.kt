package com.glella.criminalintent.database

class CrimeDbSchema {

    object Crimetable {
        val NAME = "crimes" // Table name
        val VERSION = 1
        val DATABASE_NAME = "crimeBase.db"

        // Column names
        object Cols {
            val UUID = "uuid"
            val TITLE = "title"
            val DATE = "date"
            val SOLVED = "solved"
        }


    }
}