package com.example.mininotes.Db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CheckDbHelper(context: Context): SQLiteOpenHelper(context,TaskContract.DB_NAME,null,TaskContract.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " (" +
                TaskContract.TaskEntry.ID + " INTEGER, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_TEXT + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_DATE + " TEXT NOT NULL);"

        val archiveTable = "ARCHIVE TABLE " + TaskContract.TaskEntry.TABLEARCHIVE + " (" +
                TaskContract.TaskEntry.ID + " INTEGER, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_TEXT + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_DATE + " TEXT NOT NULL);"



        db?.execSQL(createTable)
        db?.execSQL(archiveTable)
    }



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE)
        db?.execSQL("DROP TABLE IF EXISTS" +  TaskContract.TaskEntry.TABLEARCHIVE)
        onCreate(db)

    }






}