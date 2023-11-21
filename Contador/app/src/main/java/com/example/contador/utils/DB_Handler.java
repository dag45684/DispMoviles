package com.example.contador.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DB_Handler extends SQLiteOpenHelper {

    private static final String DB_NAME = "clickergame";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "jugadores";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "nombre";
    private static final String PASS_COL = "pasword";
    private static final String SCORE_COL = "score";

    public DB_Handler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + PASS_COL + " TEXT,"
                + SCORE_COL + " TEXT )";

        db.execSQL(query);
    }

    public void addToDB(String courseName, String courseDuration, String courseDescription, String courseTracks) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, courseName);
        values.put(PASS_COL, courseDuration);
        values.put(SCORE_COL, courseDescription);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<String> readFromDB(String filter)
    {
        filter = filter == null ? "1=1" : filter;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + filter, null);

        ArrayList<String> data = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                data.add(cursorCourses.getString(1) + " | " +
                        cursorCourses.getString(2)+ " | " +
                        cursorCourses.getString(3)+ " | " +
                        cursorCourses.getString(4));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return data;
    }

    public ArrayList<String> rawQuery (String qry){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery(qry, null);
        ArrayList<String> data = new ArrayList<>();
        if (cursorCourses.moveToFirst()) {
            do {
                data.add(cursorCourses.getString(1) + " | " +
                        cursorCourses.getString(2)+ " | " +
                        cursorCourses.getString(3)+ " | " +
                        cursorCourses.getString(4));
            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return data;
    }

    public void updateCourse(String originalCourseName, String courseName, String courseDescription,
                             String courseTracks, String courseDuration) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, courseName);
        values.put(DURATION_COL, courseDuration);
        values.put(DESCRIPTION_COL, courseDescription);
        values.put(TRACKS_COL, courseTracks);
        db.update(TABLE_NAME, values, "name=?", new String[]{originalCourseName});
        db.close();
    }

}