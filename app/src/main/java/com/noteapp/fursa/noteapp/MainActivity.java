package com.noteapp.fursa.noteapp;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;

import com.noteapp.fursa.noteapp.db.NotesContract;
import com.noteapp.fursa.noteapp.ui.NotesAdapter;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private NotesAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insert();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.notes_rv);
        layoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NotesAdapter(null);
        recyclerView.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                NotesContract.Notes.NOTES_URI,
                NotesContract.LIST_PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(LOG_TAG, data.getCount() + " rows loaded");
        data.setNotificationUri(getContentResolver(), NotesContract.Notes.NOTES_URI);
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(LOG_TAG, "loader reset");
    }
}
