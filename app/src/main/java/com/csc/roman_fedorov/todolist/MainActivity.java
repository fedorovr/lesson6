package com.csc.roman_fedorov.todolist;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String CLICKED_POSITION = "CLICKED_POSITION";

    public static final Uri ENTRIES_URI = Uri.withAppendedPath(ToDoTasksContentProvider.CONTENT_URI, "entries");
    public static final Uri ENTRIES_ID_URI = Uri.withAppendedPath(ToDoTasksContentProvider.CONTENT_URI, "entries/");

    private MyCursorAdapter adapter;

    public static final int TODO_LOADER_ID = 42;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", getResources().getConfiguration().locale);
        final Calendar calendar = Calendar.getInstance();
        final EditText timeEditText = (EditText) findViewById(R.id.add_task_time_et);
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        timeEditText.setText(dateFormatter.format(newDate.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button button = (Button) findViewById(R.id.add_task_button_create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titleEditText = (EditText) findViewById(R.id.add_task_title_et);
                String title = titleEditText.getText().toString();

                EditText descriptionEditText = (EditText) findViewById(R.id.add_task_desc_et);
                String description = descriptionEditText.getText().toString();

                EditText timeEditText = (EditText) findViewById(R.id.add_task_time_et);
                String time = timeEditText.getText().toString();

                if (time.isEmpty() || description.isEmpty() || title.isEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.empty_fields, Toast.LENGTH_LONG);
                } else {
                    ContentValues newValues = new ContentValues();
                    newValues.put(ToDoTable.COLUMN_TITLE, title);
                    newValues.put(ToDoTable.COLUMN_DESCRIPTION, description);
                    newValues.put(ToDoTable.COLUMN_IS_DONE, 0);
                    newValues.put(ToDoTable.COLUMN_TIME, time);
                    getContentResolver().insert(MainActivity.ENTRIES_URI, newValues);

                    titleEditText.setText("");
                    descriptionEditText.setText("");
                    timeEditText.setText("");
                }
            }
        });

        getSupportLoaderManager().initLoader(TODO_LOADER_ID, null, this);

        ListView todoLv = (ListView) findViewById(R.id.todo_list);
        todoLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailTaskActivity.class);
                intent.putExtra(CLICKED_POSITION, position);
                startActivity(intent);
            }
        });

        adapter = new MyCursorAdapter(this, null, 0);
        todoLv.setAdapter(adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(this, ENTRIES_URI, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
