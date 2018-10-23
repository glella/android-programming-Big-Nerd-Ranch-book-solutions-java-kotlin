package com.glella.criminalintent.database

import android.database.Cursor
import android.database.CursorWrapper
import com.glella.criminalintent.Crime
import java.util.*


class CrimeCursorWrapper (cursor : Cursor): CursorWrapper(cursor) {

    fun getCrime(): Crime {
        val uuidString = getString(getColumnIndex(CrimeDbSchema.Crimetable.Cols.UUID))
        val title = getString(getColumnIndex(CrimeDbSchema.Crimetable.Cols.TITLE))
        val date = getLong(getColumnIndex(CrimeDbSchema.Crimetable.Cols.DATE))
        val isSolved = getInt(getColumnIndex(CrimeDbSchema.Crimetable.Cols.SOLVED))
        val suspect = getString(getColumnIndex(CrimeDbSchema.Crimetable.Cols.SUSPECT))
        val suspectID = getString(getColumnIndex(CrimeDbSchema.Crimetable.Cols.SUSPECTID))
        val suspectPhone = getString(getColumnIndex(CrimeDbSchema.Crimetable.Cols.SUSPECTPHONE))

        val crime = Crime()
        crime.mTitle = title
        crime.mDate = Date(date)
        crime.mSolved = (isSolved != 0)
        crime.mSuspect = suspect
        crime.mSuspectID = suspectID
        crime.mSuspectPhone = suspectPhone

        return crime
    }

}