package com.noteapp.fursa.noteapp;

import android.content.ContentValues;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.noteapp.fursa.noteapp.db.NotesContract;

public class CreateNoteActivity extends AppCompatActivity {
    private static final String LOG_TAG = CreateNoteActivity.class.getSimpleName();

    private TextInputEditText titleEt;
    private TextInputEditText textEt;

    private TextInputLayout tilTitle;
    private TextInputLayout tilText;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleEt = (TextInputEditText) findViewById(R.id.title_et);
        textEt = (TextInputEditText) findViewById(R.id.text_et);

        tilTitle = (TextInputLayout) findViewById(R.id.title_til);
        tilText = (TextInputLayout) findViewById(R.id.text_til);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.create_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_save) {
            saveNote();
            return true;
        } else return false;
    }

    private void saveNote() {
        String title = titleEt.getText().toString().trim();
        String text = textEt.getText().toString().trim();

        boolean isCorrect = true;

        if(TextUtils.isEmpty(title)) {
            isCorrect = false;
            tilTitle.setError(getString(R.string.error_empty_field));
            tilTitle.setErrorEnabled(true);
        } else {
            tilTitle.setErrorEnabled(false);
        }

        if(TextUtils.isEmpty(text)) {
            isCorrect = false;
            tilText.setError(getString(R.string.error_empty_field));
            tilText.setErrorEnabled(true);
        } else {
            tilText.setErrorEnabled(false);
        }

        if(isCorrect) {
            long currentTimeMillis = System.currentTimeMillis();
            ContentValues cv = new ContentValues();
            cv.put(NotesContract.COLUMN_TITLE, title);
            cv.put(NotesContract.COLUMN_NOTE, text);
            cv.put(NotesContract.COLUMN_CREATED_TS, currentTimeMillis);
            cv.put(NotesContract.COLUMN_UPDATED_TS, currentTimeMillis);

            getContentResolver().insert(NotesContract.Notes.NOTES_URI, cv);
            finish();
        }
    }
}
