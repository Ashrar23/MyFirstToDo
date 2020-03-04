package com.example.mininotes

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mininotes.Db.Databasehelper
import com.example.mininotes.Interface.MainInterface
import java.text.SimpleDateFormat
import java.util.*

class UpdateNotesActivity() : AppCompatActivity () {

    lateinit var update : Button
    lateinit var edttitle : EditText
    lateinit var edttask : EditText




    lateinit var  databaseHelper: Databasehelper

    var id: String = ""
    var title: String = ""
    var text: String = ""






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_note)

        update = findViewById(R.id.update_btn)
        edttitle = findViewById(R.id.title_edit_text)
        edttask=findViewById(R.id.task_edit_text)


        databaseHelper= Databasehelper(this)

        id = intent?.getStringExtra(ID).toString()
        edttitle.setText(intent.getStringExtra(COL_TASK_TITLE))
        edttask.setText(intent.getStringExtra(COL_TASK_TEXT))


        update.setOnClickListener {
                updatedata()
        }


    }

    private fun updatedata (){

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val title = edttitle.text.toString()
        val text = edttask.text.toString()


        val user = MyObject()

        user.id = Integer.parseInt(id)
        user.title = title
        user.text = text
        user.record = currentDate

        val result : Boolean = databaseHelper.update(user)

        when{
            result ->{
                Toast.makeText(this,"Data Updated Successfully..", Toast.LENGTH_LONG).show()
                finish()
            }else -> Toast.makeText(this,"Failed to update data",Toast.LENGTH_LONG).show()
        }




    }

    companion object{

        var ID: String = "id"
        val COL_TASK_TITLE: String = "title"
        val COL_TASK_TEXT: String = "text"

    }


}