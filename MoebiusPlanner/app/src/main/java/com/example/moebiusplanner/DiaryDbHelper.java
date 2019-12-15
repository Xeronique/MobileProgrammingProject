package com.example.moebiusplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DiaryDbHelper {

    private static final String DATABASE_NAME = "DiaryDB.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DataBaseHelper mDBHelper;
    private Context mContext;

    private class DataBaseHelper extends SQLiteOpenHelper{

        public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DiaryDB.CreateDB.SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DiaryDB.CreateDB.TABLENAME);
            onCreate(db);
        }
    }

    public DiaryDbHelper(Context context) {
        this.mContext = context;
    }

    public DiaryDbHelper open() throws SQLException {
        mDBHelper = new DataBaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create() {
        mDBHelper.onCreate(mDB);
    }

    public void close() {
        mDB.close();
    }

    public long insertDiary(int year, int month, int day, String title, String contents) {
        ContentValues values = new ContentValues();
        values.put(DiaryDB.CreateDB.YEAR, year);
        values.put(DiaryDB.CreateDB.MONTH, month);
        values.put(DiaryDB.CreateDB.DAY, day);
        values.put(DiaryDB.CreateDB.TITLE, title);
        values.put(DiaryDB.CreateDB.CONTENTS, contents);

        String selection = DiaryDB.CreateDB.YEAR + " = ? AND " + DiaryDB.CreateDB.MONTH + " = ? AND " + DiaryDB.CreateDB.DAY + " = ?";
        String[] selectionArgs = {
                "" + year,
                "" + month,
                "" + day
        };

        if (getDiary(year, month, day) == null)
            return mDB.insert(DiaryDB.CreateDB.TABLENAME, null, values);
        else
            return mDB.update(DiaryDB.CreateDB.TABLENAME, values, selection, selectionArgs);
    }

    public long deleteDiary(int year, int month, int day) {
        String selection = DiaryDB.CreateDB.YEAR + " = ? AND " + DiaryDB.CreateDB.MONTH + " = ? AND " + DiaryDB.CreateDB.DAY + " = ?";
        String[] selectionArgs = {
                "" + year,
                "" + month,
                "" + day
        };
        return mDB.delete(DiaryDB.CreateDB.TABLENAME, selection, selectionArgs);
    }

    public ArrayList getAllDiary() {
        ArrayList diaryList = new ArrayList<>();
        String[] projection = {
                DiaryDB.CreateDB.YEAR,
                DiaryDB.CreateDB.MONTH,
                DiaryDB.CreateDB.DAY
        };

        Cursor mCursor = mDB.query(DiaryDB.CreateDB.TABLENAME, projection, null, null, null, null, null);
        while (mCursor.moveToNext()) {
            HashMap<String, String> result = new HashMap<String, String>();
            result.put(DiaryDB.CreateDB.YEAR, mCursor.getString(mCursor.getColumnIndex(DiaryDB.CreateDB.YEAR)));
            result.put(DiaryDB.CreateDB.MONTH, mCursor.getString(mCursor.getColumnIndex(DiaryDB.CreateDB.MONTH)));
            result.put(DiaryDB.CreateDB.DAY, mCursor.getString(mCursor.getColumnIndex(DiaryDB.CreateDB.DAY)));
            diaryList.add(result);
        }
        mCursor.close();

        return diaryList;
    }

    public ArrayList getListofYear() {
        ArrayList yearList = new ArrayList<>();
        String[] projection = {
                DiaryDB.CreateDB.YEAR
        };
        String order = DiaryDB.CreateDB.YEAR + " ASC";
        Cursor mCursor = mDB.query(DiaryDB.CreateDB.TABLENAME, projection, null, null,
                DiaryDB.CreateDB.YEAR, null, order);

        while (mCursor.moveToNext()) {
            yearList.add(mCursor.getInt(mCursor.getColumnIndex(DiaryDB.CreateDB.YEAR)));
        }
        mCursor.close();

        return yearList;
    }

    public ArrayList getListofMonth(int year) {
        ArrayList monthList = new ArrayList<>();
        String[] projection = {
                DiaryDB.CreateDB.MONTH
        };
        String[] selectionArgs = {
                Integer.toString(year)
        };
        String order = DiaryDB.CreateDB.MONTH + " ASC";
        Cursor mCursor = mDB.query(DiaryDB.CreateDB.TABLENAME, projection, DiaryDB.CreateDB.YEAR + " = ?", selectionArgs,
                DiaryDB.CreateDB.MONTH, null, order);

        while (mCursor.moveToNext()) {
            monthList.add(mCursor.getInt(mCursor.getColumnIndex(DiaryDB.CreateDB.MONTH)));
        }
        mCursor.close();

        return monthList;
    }

    public ArrayList getListofDay(int year, int month) {
        ArrayList dayList = new ArrayList<>();
        String[] projection = {
                DiaryDB.CreateDB.DAY
        };
        String selection = DiaryDB.CreateDB.YEAR + " = ? AND " + DiaryDB.CreateDB.MONTH + " = ?";
        String[] selectionArgs = {
                Integer.toString(year),
                Integer.toString(month)
        };
        String order = DiaryDB.CreateDB.DAY + " ASC";
        Cursor mCursor = mDB.query(DiaryDB.CreateDB.TABLENAME, projection, selection, selectionArgs,
                null, null, order);

        while (mCursor.moveToNext()) {
            dayList.add(mCursor.getInt(mCursor.getColumnIndex(DiaryDB.CreateDB.DAY)));
        }
        mCursor.close();

        return dayList;
    }

    public HashMap<String, String> getDiary(int year, int month, int day) {
        String selection = DiaryDB.CreateDB.YEAR + " = ? AND " + DiaryDB.CreateDB.MONTH + " = ? AND " + DiaryDB.CreateDB.DAY + " = ?";
        String[] selectionArgs = {
                "" + year,
                "" + month,
                "" + day
        };
        Cursor mCursor = mDB.query(DiaryDB.CreateDB.TABLENAME, null, selection, selectionArgs,
                null, null, null);
        if(mCursor.moveToNext()) {
            HashMap<String, String> result = new HashMap<String, String>();
            result.put(DiaryDB.CreateDB.YEAR, mCursor.getString(mCursor.getColumnIndex(DiaryDB.CreateDB.YEAR)));
            result.put(DiaryDB.CreateDB.MONTH, mCursor.getString(mCursor.getColumnIndex(DiaryDB.CreateDB.MONTH)));
            result.put(DiaryDB.CreateDB.DAY, mCursor.getString(mCursor.getColumnIndex(DiaryDB.CreateDB.DAY)));
            result.put(DiaryDB.CreateDB.TITLE, mCursor.getString(mCursor.getColumnIndex(DiaryDB.CreateDB.TITLE)));
            result.put(DiaryDB.CreateDB.CONTENTS, mCursor.getString(mCursor.getColumnIndex(DiaryDB.CreateDB.CONTENTS)));
            mCursor.close();
            return result;
        } else  {
            mCursor.close();
            return null;
        }
    }
}
