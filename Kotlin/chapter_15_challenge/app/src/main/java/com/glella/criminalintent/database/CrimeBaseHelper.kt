package com.glella.criminalintent.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper

class CrimeBaseHelper (context: Context) : ManagedSQLiteOpenHelper(context, CrimeDbSchema.Crimetable.DATABASE_NAME,null, CrimeDbSchema.Crimetable.VERSION ) {

    companion object {
        private var instance: CrimeBaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): CrimeBaseHelper {
            if (instance == null) {
                instance = CrimeBaseHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table " + CrimeDbSchema.Crimetable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CrimeDbSchema.Crimetable.Cols.UUID + ", " +
                CrimeDbSchema.Crimetable.Cols.TITLE + ", " +
                CrimeDbSchema.Crimetable.Cols.DATE + ", " +
                CrimeDbSchema.Crimetable.Cols.SOLVED + ", " +
                CrimeDbSchema.Crimetable.Cols.SUSPECT + ", " +
                CrimeDbSchema.Crimetable.Cols.SUSPECTID + ", " +
                CrimeDbSchema.Crimetable.Cols.SUSPECTPHONE +
                ")"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual

    }

}


// Access property for Context
val Context.database: CrimeBaseHelper
    get() = CrimeBaseHelper.getInstance(applicationContext)


