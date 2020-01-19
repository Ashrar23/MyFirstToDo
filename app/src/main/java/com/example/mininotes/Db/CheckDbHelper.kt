package com.example.mininotes.Db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CheckDbHelper(context: Context): SQLiteOpenHelper(context,Instance.DB_NAME,null,Instance.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + Instance.TaskEntry.TABLE + " ( "+
                Instance.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Instance.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL," +
                Instance.TaskEntry.COL_TASK_NOTES + " TEXT NOT NULL)";

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + Instance.TaskEntry.TABLE)
        onCreate(db)

    }






}