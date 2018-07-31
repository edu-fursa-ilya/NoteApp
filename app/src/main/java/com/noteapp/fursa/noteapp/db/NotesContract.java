package com.noteapp.fursa.noteapp.db;

import android.net.Uri;
import android.provider.BaseColumns;

import static android.provider.BaseColumns._ID;

public final class NotesContract {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "notes.db";
    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_CREATED_TS = "created_ts";
    public static final String COLUMN_UPDATED_TS = "updated_ts";

    //content provider
    public static final String AUTHORITY = "com.noteapp.fursa.contentprovider";
    public static final String URI = "content://" + AUTHORITY;


    private NotesContract() {}

    public static abstract class Notes implements BaseColumns {
        public static final Uri NOTES_URI = Uri.parse(NotesContract.URI + "/" + TABLE_NAME);
        public static final String URI_TYPE_NOTE_DIR = "vnd.android.cursor.dir/vnd.fursa.note";
        public static final String URI_TYPE_NOTE_ITEM = "vnd.android.cursor.item/vnd.fursa.note";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s" +
                        "(%s INTEGER PRIMARY KEY, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s INTEGER NOT NULL);",
                TABLE_NAME,
                _ID,
                COLUMN_TITLE,
                COLUMN_NOTE,
                COLUMN_CREATED_TS,
                COLUMN_UPDATED_TS);

        public static final String CREATE_UPDATED_TS_INDEX = String.format("CREATE INDEX updated_ts_index " +
                        "ON %s (%s);",
                TABLE_NAME,
                COLUMN_UPDATED_TS);
    }

    public static final String[] CREATE_DATABASE_QUERIES = {
            Notes.CREATE_TABLE,
            Notes.CREATE_UPDATED_TS_INDEX
    };

    public static final String[] LIST_PROJECTION = {
            _ID,
            COLUMN_TITLE,
            COLUMN_CREATED_TS,
            COLUMN_UPDATED_TS
    };
}
