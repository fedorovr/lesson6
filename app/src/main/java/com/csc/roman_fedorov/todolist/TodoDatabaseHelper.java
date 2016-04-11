package com.csc.roman_fedorov.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "todoDatabase";

    private static TodoDatabaseHelper sInstance;

    public static synchronized TodoDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TodoDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_ENTRIES_TABLE =
            "CREATE TABLE " + ToDoTable.TABLE_NAME
                    + "("
                    + ToDoTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ToDoTable.COLUMN_TITLE + " TEXT, "
                    + ToDoTable.COLUMN_DESCRIPTION + " TEXT, "
                    + ToDoTable.COLUMN_IS_DONE + " INTEGER, "
                    + ToDoTable.COLUMN_TIME + " TEXT, "
                    + ToDoTable.COLUMN_COLOR + " TEXT"
                    + ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ToDoTable.TABLE_NAME;

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
    }
}
