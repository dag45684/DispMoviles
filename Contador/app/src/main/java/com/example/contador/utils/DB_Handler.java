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
    private static final String SUMA_COL = "suma";
    private static final String AUTOSUMA_COL = "autosuma";
    private static final String OVEN_COL = "oven";
    private static final String NIVEL_CLICK_COL = "ClickLvl";
    private static final String NIVEL_AUTO_COL = "AutoLvl";
    private static final String NIVEL_OVEN_COL = "OvenLvl";
    private static final String COSTE_CLICK_COL = "CosteClick";
    private static final String COSTE_AUTO_COL = "CosteAuto";

    public DB_Handler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + PASS_COL + " TEXT,"
                + SCORE_COL + " TEXT,"
                + SUMA_COL + " TEXT,"
                + AUTOSUMA_COL + " TEXT,"
                + OVEN_COL + " TEXT,"
                + NIVEL_CLICK_COL + " TEXT,"
                + NIVEL_AUTO_COL + " TEXT,"
                + NIVEL_OVEN_COL + " TEXT,"
                + COSTE_CLICK_COL + " TEXT,"
                + COSTE_AUTO_COL + " TEXT)";

        db.execSQL(query);
    }

    public void addNewToDB(String name, String pass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(PASS_COL, pass);
        values.put(SCORE_COL, "0");
        values.put(SUMA_COL, "1");
        values.put(AUTOSUMA_COL, "1");
        values.put(OVEN_COL, "1000");
        values.put(NIVEL_CLICK_COL, "1");
        values.put(NIVEL_AUTO_COL, "1");
        values.put(NIVEL_OVEN_COL, "1");
        values.put(COSTE_CLICK_COL, "100");
        values.put(COSTE_AUTO_COL, "100");
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
        Cursor pointer = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + filter, null);

        ArrayList<String> data = new ArrayList<>();

        if (pointer.moveToFirst()) {
            do {
                String temp = pointer.getString(0) + " | "; //Id
                temp += pointer.getString(1)+ " | "; //User
                temp += pointer.getString(2)+ " | "; //Pass
                temp += pointer.getString(3)+ " | "; //Score
                temp += pointer.getString(4)+ " | "; //Suma
                temp += pointer.getString(5)+ " | "; //Autosuma
                temp += pointer.getString(6)+ " | "; //Oven
                temp += pointer.getString(7)+ " | "; //Nivel Click
                temp += pointer.getString(8)+ " | "; //Nivel Auto
                temp += pointer.getString(9)+ " | "; //Nivel oven
                temp += pointer.getString(10)+ " | "; //Coste Click
                temp += pointer.getString(11); //Coste auto
                data.add(temp);
            } while (pointer.moveToNext());
        }
        pointer.close();
        return data;
    }

    public ArrayList<String> rawQuery (String qry){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor pointer = db.rawQuery(qry, null);
        ArrayList<String> data = new ArrayList<>();
        if (pointer.moveToFirst()) {
            do {
                String temp = "";
                try{
                    temp += pointer.getString(0) + " | "; //Id
                    temp += pointer.getString(1)+ " | "; //User
                    temp += pointer.getString(2)+ " | "; //Pass
                    temp += pointer.getString(3)+ " | "; //Score
                    temp += pointer.getString(4)+ " | "; //Suma
                    temp += pointer.getString(5)+ " | "; //Autosuma
                    temp += pointer.getString(6)+ " | "; //Oven
                    temp += pointer.getString(7)+ " | "; //Nivel Click
                    temp += pointer.getString(8)+ " | "; //Nivel Auto
                    temp += pointer.getString(9)+ " | "; //Nivel oven
                    temp += pointer.getString(10)+ " | "; //Coste Click
                    temp += pointer.getString(11); //Coste auto
                }catch (Exception e){
                    data.add(temp);
                    break;
                }
                data.add(temp);
            } while (pointer.moveToNext());
        }
        pointer.close();
        return data;
    }

    public void rawUpdate(String qry) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery(qry, null);
    }

    public void delal (){
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery("DELETE FROM Jugadores", null);
    }

}