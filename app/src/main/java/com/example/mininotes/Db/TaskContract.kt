package com.example.mininotes.Db

import android.provider.BaseColumns

class TaskContract {
    companion object {
        val DB_NAME = "Database"
        val DB_VERSION = 2

    }

    class TaskEntry : BaseColumns {

        companion object {
            val TABLE: String = "tasks"
            val TABLEARCHIVE : String = "archive"

            var ID: String = "id"
            val COL_TASK_TITLE: String = "title"
            val COL_TASK_TEXT: String = "text"
            val COL_TASK_DATE: String = "date"

        }
    }
}