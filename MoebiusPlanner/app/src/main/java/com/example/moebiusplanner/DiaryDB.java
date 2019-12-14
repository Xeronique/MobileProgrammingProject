package com.example.moebiusplanner;

import android.provider.BaseColumns;

public final class DiaryDB {

    private DiaryDB() {}

    public static final class CreateDB implements BaseColumns{
        public static final String ID = "id";
        public static final String YEAR = "year";
        public static final String MONTH = "month";
        public static final String DAY = "day";
        public static final String TITLE = "title";
        public static final String CONTENTS = "contents";
        public static final String TABLENAME = "diaryTable";
        public static final String SQL_CREATE_ENTRIES = "create table if not exists " + TABLENAME
                + "(" + ID + " TEXT PRIMARY KEY, "
                + YEAR + " INTEGER NOT NULL , "
                + MONTH + " INTEGER NOT NULL, "
                + DAY + " INTEGER NOT NULL, "
                + TITLE + " TEXT , "
                + CONTENTS + " TEXT );";
    }
}
