package com.glella.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.glella.criminalintent.database.CrimeDbSchema.Crimetable;

public class CrimeBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Crimetable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                Crimetable.Cols.UUID + ", " +
                Crimetable.Cols.TITLE + ", " +
                Crimetable.Cols.DATE + ", " +
                Crimetable.Cols.SOLVED + ", " +
                Crimetable.Cols.SUSPECT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //
    }
}
