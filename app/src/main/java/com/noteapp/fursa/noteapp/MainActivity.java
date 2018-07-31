package com.noteapp.fursa.noteapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.noteapp.fursa.noteapp.db.NotesContract;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insert();
        select();
    }

    private void insert() {
        ContentResolver resolver = getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put(NotesContract.COLUMN_TITLE, "Заголовок заметки");
        cv.put(NotesContract.COLUMN_NOTE, "Текст заметки");
        cv.put(NotesContract.COLUMN_CREATED_TS, System.currentTimeMillis());
        cv.put(NotesContract.COLUMN_UPDATED_TS, System.currentTimeMillis());
        Uri uri = resolver.insert(NotesContract.Notes.NOTES_URI, cv);

        Log.d(LOG_TAG, "URI = " + uri);
    }

    private void select() {
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(NotesContract.Notes.NOTES_URI, NotesContract.LIST_PROJECTION, null, null, null);
        Log.d(LOG_TAG, "Rows count = " + cursor.getCount());
        cursor.close();
    }
}
