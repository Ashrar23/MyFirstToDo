package com.example.mininotes.Db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mininotes.MyObject
import com.example.mininotes.adapter.MyAdapter
import java.util.*





class Databasehelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    lateinit var adapter: MyAdapter

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE + " (" +
                ID + " INTEGER PRIMARY KEY, " +
                COL_TASK_TITLE + " TEXT , " +
                COL_TASK_TEXT + " TEXT , " +
                COL_TASK_DATE + " TEXT);"

        val archiveTable = "CREATE TABLE " + TABLEARCHIVE + " (" +
                ID + " INTEGER, " +
                COL_TASK_TITLE + " TEXT , " +
                COL_TASK_TEXT + " TEXT , " +
                COL_TASK_DATE + " TEXT );"



        db?.execSQL(createTable)
        db?.execSQL(archiveTable)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE)
        db?.execSQL("DROP TABLE IF EXISTS" + TABLEARCHIVE)
        onCreate(db)

    }

    fun onStoreData(user: MyObject): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_TASK_TITLE, user.title)
        contentValues.put(COL_TASK_TEXT, user.text)
        contentValues.put(COL_TASK_DATE, user.record)
        val insert_data = db.insert(TABLE, null, contentValues)
        db.close()

        return !insert_data.equals(-1)
    }

    val user: List<MyObject>
        get() {
            val userList = ArrayList<MyObject>()
            val db = readableDatabase
            val selectQuery = "SELECT  * FROM $TABLE"
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val user = MyObject()
                    user.id = cursor.getInt(cursor.getColumnIndex(ID))
                    user.title = cursor.getString(cursor.getColumnIndex(COL_TASK_TITLE))
                    user.text = cursor.getString(cursor.getColumnIndex(COL_TASK_TEXT))
                    user.record = cursor.getString(cursor.getColumnIndex(COL_TASK_DATE))
                    userList.add(user)
                } while (cursor.moveToNext())
                cursor.close()
            }

            return userList
        }


    fun update(user: MyObject): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
      values.put(COL_TASK_TITLE, user.title)
        values.put(COL_TASK_TEXT, user.text)
        values.put(COL_TASK_DATE, user.record)
        val _success = db.update(TABLE, values, ID + "=?", arrayOf(user.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun delete(id: Int): Boolean {
        val db = this.readableDatabase
        val values = ContentValues()
        values.put(ID, id)
        val success = db.delete(TABLE, ID + "=?", arrayOf(id.toString())).toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }

    fun archive(id: Int): Boolean {
        val db: SQLiteDatabase = this.readableDatabase
         val contentValues = ContentValues()
         contentValues.put(ID, id)
        contentValues.put(COL_TASK_TITLE,id)
        contentValues.put(COL_TASK_TEXT,id)
        contentValues.put(COL_TASK_DATE,id)

        val success = db.insert(TABLEARCHIVE, null, contentValues)

         db.close()

        return Integer.parseInt("$success") != -1
    }

    val users: List<MyObject>
        get() {


            val userList = ArrayList<MyObject>()
            val db = readableDatabase
            val selectQuery = "SELECT  * FROM $TABLEARCHIVE"
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val user = MyObject()
                    user.id = cursor.getInt(cursor.getColumnIndex(ID))
                    user.title = cursor.getString(cursor.getColumnIndex(COL_TASK_TITLE))
                    user.text = cursor.getString(cursor.getColumnIndex(COL_TASK_TEXT))
                    user.record = cursor.getString(cursor.getColumnIndex(COL_TASK_DATE))

                    userList.add(user)

                } while (cursor.moveToNext())
                cursor.close()
            }


            return userList
        }




    fun add_item(list: List<MyObject>) {
        val db =this.readableDatabase

        db.beginTransaction()
        try {
            val values = ContentValues()
            for (item in list) {
                values.put(ID, item.id)
                values.put(COL_TASK_TITLE, item.title)
                values.put(COL_TASK_TEXT,item.text)
                values.put(COL_TASK_DATE,item.record)

                db.insert(TABLEARCHIVE, null, values)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }


    companion object {
        val DB_NAME = "Database"
        val DB_VERSION = 2
        val TABLE: String = "tasks"
        val TABLEARCHIVE: String = "archive"


        var ID: String = "id"
        val COL_TASK_TITLE: String = "title"
        val COL_TASK_TEXT: String = "text"
        val COL_TASK_DATE: String = "date"


    }










}