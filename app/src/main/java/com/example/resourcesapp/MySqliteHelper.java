package com.example.resourcesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="users.db";
    private static final int DB_VERSION = 1;

    public MySqliteHelper(Context context){
        super(context,DATABASE_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
   String sql ="CREATE table users" +
           "(user_id integer primary key autoincrement not null," +
           " fullname VARCHAR(45), username VARCHAR(45)," +
           " password VARCHAR(45))";

   db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS users";
      db.execSQL(sql);
    }
}
