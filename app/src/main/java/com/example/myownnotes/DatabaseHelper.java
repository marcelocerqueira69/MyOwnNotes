package com.example.myownnotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "notes.db";
    public static final String TABLE_NAME = "notes_table";
    public static final String ID = "ID";
    public static final String ASSUNTO = "ASSUNTO";
    public static final String LOCAL = "LOCAL";
    public static final String DESCRICAO = "DESCRIÇÃO";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        SQLiteDatabase db = this.getReadableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, ASSUNTO TEXT, LOCAL TEXT, DESCRICAO TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
