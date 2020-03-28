package com.example.myownnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Menu;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "notes.db";
    public static final String TABLE_NAME = "notes_table";
    public static final String ID = "ID";
    public static final String ASSUNTO = "ASSUNTO";
    public static final String DESCRICAO = "DESCRICAO";
    public static final String DATA = "DATA";
    List<MenuNote> list = new ArrayList<>();

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "(" + "" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "" + ASSUNTO + " TEXT," +
            "" + DESCRICAO + " TEXT," +
            "" + DATA + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addNote(String assunto, String descricao, String data) {
        Calendar calendar = Calendar.getInstance();
        data = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ASSUNTO, assunto);
        values.put(DESCRICAO, descricao);
        values.put(DATA, data);


        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public List<MenuNote> getEntriesFromDb(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        String query = "SELECT * FROM " + TABLE_NAME;

        cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                MenuNote menu = new MenuNote(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                list.add(menu);
            }while(cursor.moveToNext());
        }

        return list;
    }

    public boolean editNote(int id, String assunto, String descricao){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(ASSUNTO, assunto);
        content.put(DESCRICAO, descricao);
        db.update(TABLE_NAME, content, ID + "=" + id , null);
        db.close();


        return true;
    }


    public boolean deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=" + id, null);
        db.close();

        return true;
    }



}
