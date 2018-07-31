package com.noteapp.fursa.noteapp.ui;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noteapp.fursa.noteapp.R;
import com.noteapp.fursa.noteapp.db.NotesContract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotesAdapter extends CursorRecyclerAdapter<NotesAdapter.NotesViewHolder> {
    public NotesAdapter(Cursor cursor) {
        super(cursor);
    }

    @Override
    public void onBindViewHolder(NotesAdapter.NotesViewHolder viewHolder, Cursor cursor) {
        int titleColumnIndex = cursor.getColumnIndexOrThrow(NotesContract.COLUMN_TITLE);
        String title = cursor.getString(titleColumnIndex);
        viewHolder.tvTitle.setText(title);
        int dateColumnIndex = cursor.getColumnIndexOrThrow(NotesContract.COLUMN_UPDATED_TS);
        long dateUpdatedTs = cursor.getLong(dateColumnIndex);
        Date date = new Date(dateUpdatedTs);
        viewHolder.tvDate.setText(viewHolder.SDF.format(date));
    }

    @NonNull
    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_note, parent, false);
        return new NotesViewHolder(view);
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

        private final TextView tvTitle;
        private final TextView tvDate;


        public NotesViewHolder(View itemView) {
            super(itemView);

            this.tvTitle = itemView.findViewById(R.id.title_tv);
            this.tvDate = itemView.findViewById(R.id.date_tv);
        }
    }
}
