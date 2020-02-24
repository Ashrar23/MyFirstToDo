package com.example.mininotes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mininotes.Db.Databasehelper
import java.text.SimpleDateFormat
import java.util.*


class AddNotesActivity  : AppCompatActivity() {

    lateinit var save : Button
    lateinit var edttitle : EditText
    lateinit var edttask : EditText

    lateinit var dbHelper: Databasehelper



        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)


            save = findViewById(R.id.update_btn)
            edttitle = findViewById(R.id.title_edit_text)
            edttask = findViewById(R.id.task_edit_text)

            dbHelper = Databasehelper(this)


            save.setOnClickListener{

                insertfunction()
            }

    }

            fun insertfunction(){

                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())


                var tasktitle = edttitle.text.toString()
                val tasktext = edttask.text.toString()

                if (tasktitle.isEmpty() && tasktext.isEmpty())
                {
                    Toast.makeText(this, "Note is empty", Toast.LENGTH_SHORT).show()
                }
                else if (tasktitle.isEmpty())
                    tasktitle = tasktext


                val task = MyObject()
                task.title = tasktitle
                task.text = tasktext
                task.record = currentDate



                val result : Boolean = dbHelper.onStoreData(task)

                when {

                    result -> {
                        Toast.makeText(this, "Data inserted Successfully..", Toast.LENGTH_LONG)
                            .show()
                        finish()
                    }else->Toast.makeText(this,"Failed to insert data",Toast.LENGTH_LONG).show()


                }







            }
}