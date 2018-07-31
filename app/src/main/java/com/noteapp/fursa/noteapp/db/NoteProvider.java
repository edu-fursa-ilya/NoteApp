package com.noteapp.fursa.noteapp.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class NoteProvider extends ContentProvider {
    public static final int NOTES = 1;
    public static final int NOTE = 2;

    private DatabaseHelper db;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(NotesContract.AUTHORITY, "notes", NOTES);
        URI_MATCHER.addURI(NotesContract.AUTHORITY, "note/#", NOTE);

    }

    @Override
    public boolean onCreate() {
        db = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = db.getReadableDatabase();
        switch (URI_MATCHER.match(uri)) {
            case NOTES:
                if(TextUtils.isEmpty(sortOrder)) {
                    sortOrder = NotesContract.COLUMN_UPDATED_TS + " DESC";
                }

                return database.query(NotesContract.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

            case NOTE:
                String id = uri.getLastPathSegment();

                if(TextUtils.isEmpty(selection)) {
                    selection = NotesContract.Notes._ID + " = ?";
                    selectionArgs = new String[]{id};
                } else {
                    selection = selection + " AND " + NotesContract.Notes._ID + " = ?";
                    String[] newSelectionArgs = new String[selectionArgs.length + 1];
                    System.arraycopy(selectionArgs, 0, newSelectionArgs, 0, selectionArgs.length);
                    newSelectionArgs[newSelectionArgs.length - 1] = id;
                    selectionArgs = newSelectionArgs;
                }
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case NOTES:
                return NotesContract.Notes.URI_TYPE_NOTE_DIR;
            case NOTE:
                return NotesContract.Notes.URI_TYPE_NOTE_ITEM;
            default: return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase database = db.getWritableDatabase();
        switch (URI_MATCHER.match(uri)) {
            case NOTES:
                long rowId = database.insert(NotesContract.TABLE_NAME, null, values);

                if(rowId > 0) {
                    Uri noteUri = ContentUris.withAppendedId(NotesContract.Notes.NOTES_URI, rowId);
                    getContext().getContentResolver().notifyChange(uri, null);
                    return noteUri;
                }
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
