package com.glella.criminalintent.database;

public class CrimeDbSchema {

    public static final class Crimetable {
        public static final String NAME = "crimes"; // Table name

        // Column names
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
        }


    }
}
