package com.csc.roman_fedorov.todolist;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        final int position = getIntent().getIntExtra(MainActivity.CLICKED_POSITION, 0) + 1;

        Button confirmButton = (Button) findViewById(R.id.detail_button_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues updateValues = new ContentValues();
                updateValues.put(ToDoTable.COLUMN_IS_DONE, 1);
                getContentResolver().update(MainActivity.ENTRIES_URI, updateValues, " _ID = ? ",
                        new String[]{String.valueOf(position)});

                Intent intent = new Intent(DetailTaskActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", getResources().getConfiguration().locale);
        final Calendar calendar = Calendar.getInstance();
        final EditText timeEditText = (EditText) findViewById(R.id.detail_edit_time);
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DetailTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        timeEditText.setText(dateFormatter.format(newDate.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(Uri.withAppendedPath(MainActivity.ENTRIES_ID_URI,
                    String.valueOf(position)), null, " _ID = ? ", new String[]{String.valueOf(position)}, null);
            cursor.moveToNext();

            String title = cursor.getString(cursor.getColumnIndex(ToDoTable.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(ToDoTable.COLUMN_DESCRIPTION));
            String time = cursor.getString(cursor.getColumnIndex(ToDoTable.COLUMN_TIME));

            EditText titleEdit = (EditText) findViewById(R.id.detail_edit_title);
            titleEdit.setText(title);
            EditText descriptionEdit = (EditText) findViewById(R.id.detail_edit_description);
            descriptionEdit.setText(description);
            EditText timeEdit = (EditText) findViewById(R.id.detail_edit_time);
            timeEdit.setText(time);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
