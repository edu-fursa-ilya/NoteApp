package com.noteapp.fursa.noteapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context c) {
        super(c, NotesContract.DB_NAME, null, NotesContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String query: NotesContract.CREATE_DATABASE_QUERIES) {
            db.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
