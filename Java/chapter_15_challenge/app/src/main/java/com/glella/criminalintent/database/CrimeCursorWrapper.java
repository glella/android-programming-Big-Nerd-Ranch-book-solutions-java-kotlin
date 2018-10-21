package com.glella.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.glella.criminalintent.Crime;
import com.glella.criminalintent.database.CrimeDbSchema.Crimetable;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(Crimetable.Cols.UUID));
        String title = getString(getColumnIndex(Crimetable.Cols.TITLE));
        long date = getLong(getColumnIndex(Crimetable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(Crimetable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(Crimetable.Cols.SUSPECT));
        String suspectID = getString(getColumnIndex(Crimetable.Cols.SUSPECTID));
        String suspectPhone = getString(getColumnIndex(Crimetable.Cols.SUSPECTPHONE));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        crime.setSuspect(suspect);
        crime.setSuspectID(suspectID);
        crime.setSuspectPhone(suspectPhone);

        return crime;
    }

}
