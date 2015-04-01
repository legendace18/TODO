package com.ace.legend.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rohan on 3/30/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper{
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ToDo";

    // table name
    private static final String TABLE_TODO = "Todo";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DETAIL = "detail";
    private static final String KEY_STATUS = "status";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE
                + " VARCHAR," + KEY_DETAIL + " VARCHAR, " + KEY_STATUS
                + " INTEGER, " + KEY_DATE + " VARCHAR, " + KEY_TIME
                + " VARCHAR " + ")";
        Log.d("Legend.ace18", "table=" + CREATE_TODO_TABLE);
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        // Create tables again
        onCreate(db);

    }

    public void addTodo(ToDo toDo) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("Legend.ace18", "value== ....." + toDo.getDetail());
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, toDo.getTitle());
        values.put(KEY_DETAIL, toDo.getDetail());
        values.put(KEY_STATUS, 0);
        // Inserting Row
        db.insert(TABLE_TODO, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<ToDo> getTodo() {
        ArrayList<ToDo> todoList = new ArrayList<ToDo>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TODO + " WHERE "
                + KEY_STATUS + " = 0";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ToDo todo = new ToDo();
                todo.setTitle(cursor.getString(1));
                todo.setDetail(cursor.getString(2));
                todo.setDate(cursor.getString(4));
                todo.setTime(cursor.getString(5));

                // Adding todo to list
                todoList.add(todo);
            } while (cursor.moveToNext());
        }
        db.close(); // Closing database connection

        return todoList;
    }

    public int shiftTodo(ToDo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STATUS, 1);
        String[] whereArgs = { todo.getTitle(), todo.getDetail() };
        int count = db.update(TABLE_TODO, values, KEY_TITLE + " =? AND "
                + KEY_DETAIL + " =? ", whereArgs);
        db.close();
        return count;
    }

    public int deleteTodo(ToDo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = { todo.getTitle(), todo.getDetail() };
        int count = db.delete(TABLE_TODO, KEY_TITLE + " =? AND " + KEY_DETAIL
                + " =? ", whereArgs);
        db.close();
        return count;
    }

    public ArrayList<ToDo> getDone() {
        ArrayList<ToDo> todoList = new ArrayList<ToDo>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TODO + " WHERE "
                + KEY_STATUS + " = 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ToDo todo = new ToDo();
                todo.setTitle(cursor.getString(1));
                todo.setDetail(cursor.getString(2));

                // Adding todo to list
                todoList.add(todo);
            } while (cursor.moveToNext());
        }
        db.close(); // Closing database connection

        return todoList;
    }

    public int shiftDone(ToDo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STATUS, 0);
        String[] whereArgs = { todo.getTitle(), todo.getDetail() };
        int count = db.update(TABLE_TODO, values, KEY_TITLE + " =? AND "
                + KEY_DETAIL + " =? ", whereArgs);
        db.close();
        return count;
    }

    public int updateTodo(String title, String detail, String etitle,
                          String edetail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, etitle);
        values.put(KEY_DETAIL, edetail);
        String[] whereArgs = { title, detail };
        int count = db.update(TABLE_TODO, values, KEY_TITLE + " =? AND "
                + KEY_DETAIL + " =? ", whereArgs);
        db.close();
        return count;
    }


    public void addRemainder(ToDo todo) {
        Log.d("legendace18","Date and Time"+ todo.getDate());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, todo.getDate());
        values.put(KEY_TIME, todo.getTime());
        String[] whereArgs = { todo.getTitle(), todo.getDetail() };
        db.update(TABLE_TODO, values, KEY_TITLE + " =? AND " + KEY_DETAIL
                + " =? ", whereArgs);
        db.close();
    }

}
