package com.example.proyectoclicker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CrearBDLogin extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE Cuenta (Username TEXT PRIMARY KEY, Password TEXT, Iniciada INTEGER)";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    public CrearBDLogin (Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version){
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Cuenta");

        db.execSQL(sqlCreate);
    }
}
