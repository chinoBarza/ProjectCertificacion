package com.example.chinobarza.facebooklogin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.chinobarza.facebooklogin.utils.Constantes.USUARIO;

public class MyDatabase extends SQLiteOpenHelper {
    String query = "CREATE TABLE " + USUARIO + " (id integer primary key autoincrement, nombre text, pass text)";

    public MyDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
