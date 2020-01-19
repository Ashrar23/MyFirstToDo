package com.example.mininotes.Db

import android.provider.BaseColumns

class Instance {
    companion object {
        val DB_NAME = "Database"
        val DB_VERSION = 2

    }

    class TaskEntry : BaseColumns {

        companion object {
            val TABLE = "tasks"
            val COL_TASK_TITLE = "title"
            val COL_TASK_NOTES = "notes"
            val _ID = BaseColumns._ID
        }
    }
}