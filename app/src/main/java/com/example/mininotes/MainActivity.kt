package com.example.mininotes

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mininotes.Db.CheckDbHelper
import com.example.mininotes.Db.Instance
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mHelper: CheckDbHelper
    private lateinit var mTaskListView: ListView
    private var mAdapter: ArrayAdapter<String>? = null
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mTaskListView = findViewById(R.id.list_todo)

        mHelper = CheckDbHelper(this)


        fab.setOnClickListener {

            val taskEditText = EditText(this)
            val dialog = AlertDialog.Builder(this)
                .setTitle("Add a new task")
                .setMessage("What do you want to do next?")
                .setView(taskEditText)
                .setPositiveButton("Add", DialogInterface.OnClickListener { dialog, which ->
                    val task = taskEditText.text.toString()
                    val db = mHelper.getWritableDatabase()
                    val values = ContentValues()
                    values.put(Instance.TaskEntry.COL_TASK_TITLE, task)
                    db.insertWithOnConflict(Instance.TaskEntry.TABLE,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE)
                    db.close()

                    updateUI()
                })
                .setNegativeButton("Cancel", null)
                .create()
            dialog.show()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_archive,R.id.nav_delete
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
         menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun updateUI() {
        val taskList = ArrayList<String>()
        val db = mHelper.readableDatabase
        val cursor = db.query(Instance.TaskEntry.TABLE,
            arrayOf(Instance.TaskEntry._ID, Instance.TaskEntry.COL_TASK_TITLE), null, null, null, null, null)
        while (cursor .moveToNext()) {
            val idx = cursor.getColumnIndex(Instance.TaskEntry.COL_TASK_TITLE)
            taskList.add(cursor.getString(idx))
        }
        if (mAdapter == null) {
            mAdapter = ArrayAdapter(this,
                R.layout.list_items_todo,
                R.id.task,
                taskList)
            mTaskListView.adapter = mAdapter
        } else {
            mAdapter?.clear()
            mAdapter?.addAll(taskList)
            mAdapter?.notifyDataSetChanged()
        }

        cursor.close()
        db.close()
    }

}




