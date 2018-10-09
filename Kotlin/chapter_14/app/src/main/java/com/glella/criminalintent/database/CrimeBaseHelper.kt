package com.glella.criminalintent.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.glella.criminalintent.database.CrimeDbSchema.Crimetable.DATABASE_NAME
import org.jetbrains.anko.db.*

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
        db.createTable(DATABASE_NAME, true,
                "_id" to INTEGER + PRIMARY_KEY + UNIQUE + AUTOINCREMENT,
                CrimeDbSchema.Crimetable.Cols.UUID to TEXT,
                CrimeDbSchema.Crimetable.Cols.TITLE to TEXT,
                CrimeDbSchema.Crimetable.Cols.DATE to INTEGER,
                CrimeDbSchema.Crimetable.Cols.SOLVED to INTEGER)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(DATABASE_NAME, true)
        onCreate(db)
    }

}


// Access property for Context
val Context.database: CrimeBaseHelper
    get() = CrimeBaseHelper.getInstance(applicationContext)


