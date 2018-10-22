package com.glella.criminalintent


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.glella.criminalintent.database.CrimeBaseHelper
import com.glella.criminalintent.database.CrimeCursorWrapper
import com.glella.criminalintent.database.CrimeDbSchema
import java.util.*

object CrimeLab {
    var mCrimes = ArrayList<Crime>()
    var db: SQLiteDatabase

    val tablename = CrimeDbSchema.Crimetable.NAME
    val uuidColumn = CrimeDbSchema.Crimetable.Cols.UUID
    val titleColumn = CrimeDbSchema.Crimetable.Cols.TITLE
    val dateColumn = CrimeDbSchema.Crimetable.Cols.DATE
    val solvedColumn = CrimeDbSchema.Crimetable.Cols.SOLVED
    val suspectColumn = CrimeDbSchema.Crimetable.Cols.SUSPECT
    val suspectIDColumn = CrimeDbSchema.Crimetable.Cols.SUSPECTID
    val suspectPhoneColumn = CrimeDbSchema.Crimetable.Cols.SUSPECTPHONE

    init {
        val context: Context = ApplicationContextProvider.applicationContext()
        db = CrimeBaseHelper(context).writableDatabase
        mCrimes = getCrimes() as ArrayList<Crime>

    }

    fun getCrimes(): List<Crime> {
        val crimes = ArrayList<Crime>()

        val cursor = queryCrimes(null, null)

        try {
            cursor.moveToFirst()
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime())
                cursor.moveToNext()
            }
        } finally {
            cursor.close()
        }
        return crimes
    }


    fun getCrime(id: UUID): Crime? {
        val cursor = queryCrimes("$uuidColumn = ?", arrayOf(id.toString()))

        try {
            if (cursor.count == 0) {
                return null
            }

            cursor.moveToFirst()
            return cursor.getCrime()
        } finally {
            cursor.close()
        }
    }

    fun addCrime(c: Crime) {
        //mCrimes.add(c); //old line

        val values = getContentValues(c)
        db.insert(tablename, null, values)
    }

    fun updateCrime(crime: Crime) {
        val uuidString = crime.mId.toString()
        val values = getContentValues(crime)

        db.update(tablename, values, uuidColumn + " = ?", arrayOf<String>(uuidString))
    }

    private fun getContentValues(crime: Crime): ContentValues {
        val values = ContentValues()
        values.put(uuidColumn, crime.mId.toString())
        values.put(titleColumn, crime.mTitle)
        values.put(dateColumn, crime.mDate!!.getTime())
        values.put(solvedColumn, if (crime.mSolved) 1 else 0)
        values.put(suspectColumn, crime.mSuspect)
        values.put(suspectIDColumn, crime.mSuspectID)
        values.put(suspectPhoneColumn, crime.mSuspectPhone)

        return values
    }

    private fun queryCrimes(whereClause: String?, whereArgs: Array<String>?): CrimeCursorWrapper {
        val cursor = db.query(
                tablename, // having
                null // orderBy
                , // columns - null selects all columns
                whereClause,
                whereArgs, null, null, null
        )// groupBy
        return CrimeCursorWrapper(cursor)
    }

}