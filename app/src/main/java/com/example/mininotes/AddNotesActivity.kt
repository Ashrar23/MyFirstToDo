package com.example.mininotes

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.mininotes.Db.CheckDbHelper
import com.example.mininotes.Db.TaskContract
import com.example.mininotes.ui.notes.NotesFragment
import java.text.SimpleDateFormat
import java.util.*


class AddNotesActivity  : AppCompatActivity() {

    private var list = mutableListOf<MyObject>()


    var position: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)

        position = intent.getStringExtra("PositionInList")
        val title = intent.getStringExtra("TaskTitle")
        val text = intent.getStringExtra("TaskText")

        val bundle=Bundle()
        bundle.putString("position",position)
        bundle.putString("title",title)
        bundle.putString("text",text)
        val frag=NotesFragment()
        frag.arguments=bundle


        val editTextTitle = findViewById<EditText>(R.id.title_edit_text)
        val editTextText = findViewById<EditText>(R.id.task_edit_text)

        editTextTitle.setText(title)
        editTextText.setText(text)
    }

    override fun onBackPressed() {
        val data = Bundle()
        data.putString("Action", "CANCEL")
        val myFragment = NotesFragment()
        myFragment.arguments = data
        super.onBackPressed()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_note, menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_validate -> {
                val editTextTitle = findViewById<EditText>(R.id.title_edit_text)
                val editTextText = findViewById<EditText>(R.id.task_edit_text)

                var taskTitle = editTextTitle.text.toString()
                val taskText = editTextText.text.toString()


                if (taskTitle.isEmpty() && taskText.isEmpty()) {
                    Toast.makeText(this, "Note is empty", Toast.LENGTH_SHORT).show()
                    return super.onOptionsItemSelected(item)
                } else if (taskTitle.isEmpty())
                    taskTitle = taskText

                val data = Bundle()

                if (position != null) {
                    data.putString("Action", "MODIFY")
                    data.putString("PositionInList", position)

                } else
                    data.putString("Action", "ADD")
                    data.putString("TaskTitle", taskTitle)
                    data.putString("TaskText", taskText)

                val myFragment = NotesFragment()
                myFragment.arguments = data
                finish()
            }

            android.R.id.home ->
                onBackPressed()
        }

        return super.onOptionsItemSelected(item)

    }






}