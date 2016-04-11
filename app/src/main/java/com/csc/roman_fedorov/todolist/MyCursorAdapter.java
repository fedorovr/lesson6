package com.csc.roman_fedorov.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by roman on 10.04.2016.
 */

public class MyCursorAdapter extends CursorAdapter {
    public MyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.todo_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTitle = (TextView) view.findViewById(R.id.todo_list_item_title);
        TextView tvDescription = (TextView) view.findViewById(R.id.todo_list_item_description);
        TextView tvTime = (TextView) view.findViewById(R.id.todo_list_item_time);

        CharSequence title = cursor.getString(cursor.getColumnIndexOrThrow(ToDoTable.COLUMN_TITLE));
        CharSequence description = cursor.getString(cursor.getColumnIndexOrThrow(ToDoTable.COLUMN_DESCRIPTION));
        CharSequence time = cursor.getString(cursor.getColumnIndexOrThrow(ToDoTable.COLUMN_TIME));
        int isDone = cursor.getInt(cursor.getColumnIndexOrThrow(ToDoTable.COLUMN_IS_DONE));
        if (isDone == 1) {
            tvTitle.setPaintFlags(tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvDescription.setPaintFlags(tvDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvTime.setPaintFlags(tvTime.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        tvTitle.setText(title);
        tvDescription.setText(description);
        tvTime.setText(time);
    }
}
