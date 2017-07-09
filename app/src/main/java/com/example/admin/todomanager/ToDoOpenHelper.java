package com.example.admin.todomanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 7/3/2017.
 */

class ToDoOpenHelper extends SQLiteOpenHelper {
    public final static String ToDo_Table_Name="Todo";
    public final static String ToDo_Table_title="Title";
    public final static String ToDo_Table_Id="id";
    public final static String ToDo_Table_Category="category";
    public final static String ToDo_Table_Date="Date";
    public ToDoOpenHelper(Context context) {
        super(context, "Todo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "create table " + ToDo_Table_Name +"( " + ToDo_Table_Id +" integer primary key autoincrement, "+
                ToDo_Table_title +" text, "+ToDo_Table_Date + " text,"+ToDo_Table_Category +" text);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
