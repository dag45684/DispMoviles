package com.example.contador.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/*
DB DATA LOADING:
Returns: ArrayList of each row separated by " | "

on readFromDB(filter) or data corresponds to:
id: .get(n).split(" | ")[0]
name: .get(n).split(" | ")[1]
pass: .get(n).split(" | ")[2]
score: .get(n).split(" | ")[3]
suma: .get(n).split(" | ")[4]
autosuma: .get(n).split(" | ")[5]
oven: .get(n).split(" | ")[6]
cliclvl: .get(n).split(" | ")[7]
autolvl: .get(n).split(" | ")[8]
ovenlvl: .get(n).split(" | ")[9]
costeclick: .get(n).split(" | ")[10]
costeauto: .get(n).split(" | ")[11]

Pd: Algunos metodos son una forma un poco vaga o poco elegantes de hacerlos,
pero no tenia mucho tiempo para dejarlo tod funcionando e investigar la API de
sqlite.

 */

public class DB_Handler extends SQLiteOpenHelper {

    private static final String DB_NAME = "clickergame";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "jugadores";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "nombre";
    private static final String PASS_COL = "password";
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

    //Crea la tabla solo si no existe al instanciar una clase de DB_Handler.
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

    //Por algun motivo da problemas, asi que utilizo el siguiente.
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

    //TODO: Encriptar passwd
    //Inserta un nuevo player con settings default, nombre y passwd.
    public void newEntry(String name, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(String.format("INSERT INTO Jugadores (nombre," +
                " password, score," +
                " suma, autosuma, oven," +
                " Clicklvl, Autolvl, Ovenlvl," +
                " CosteClick, CosteAuto)" +
                " VALUES ('%s', '%s', '0', '1', " +
                "'1', '1000', '1', '1', '1', " +
                "'100', '100')", name, pass));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Lee todos los elementos de la db dado un filtro opcional y los devuelve en un unico string formateado
    //para el parseo.
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

    //Dada una query que solo devuelva un elemento, lo devuelve.
    public String bitFromDB(String qry){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor pointer = db.rawQuery(qry, null);
        ArrayList<String> data = new ArrayList<>();
        if (pointer.moveToFirst()) {
            try {
                int temp = pointer.getInt(0);
                pointer.close();
                return Integer.toString(temp);//Id
            }catch (Exception e){return null;}
        }
        return null;
    }

    //Raw query que devuelve desde * hasta 1 solo elemento.
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

    //En realidad acepta cualquier sentencia SQL, pero no devuelve nada, por lo que su funcion es
    //updatear y deletear elementos, o alterar propiedades.
    public void rawUpdate(String qry) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(qry);
    }

    //debugging purposes o reseteo de la dbb
    public void delal (){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE Jugadores");
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

}