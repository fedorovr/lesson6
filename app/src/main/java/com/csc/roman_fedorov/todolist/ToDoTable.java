package com.csc.roman_fedorov.todolist;

import android.provider.BaseColumns;

interface ToDoTable extends BaseColumns {
    String TABLE_NAME = "ToDoTasks";

    String COLUMN_TITLE = "title";
    String COLUMN_DESCRIPTION = "description";
    String COLUMN_TIME = "time";
    String COLUMN_IS_DONE = "is_done";
    String COLUMN_COLOR = "color";
}
