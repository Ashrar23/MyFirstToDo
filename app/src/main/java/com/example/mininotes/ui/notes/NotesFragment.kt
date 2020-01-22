
package com.example.mininotes.ui.notes

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
 import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mininotes.Db.CheckDbHelper
import com.example.mininotes.Db.TaskContract
import com.example.mininotes.adapter.MyAdapter
import com.example.mininotes.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


 class NotesFragment: Fragment(), View.OnClickListener {
    private lateinit var mHelper: CheckDbHelper
    val adapter = activity?.let { MyAdapter(it, mHelper) }
    val db = mHelper.writableDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_notes, container, false)

        var recyclerView = view!!.findViewById(R.id.list_todo)  as RecyclerView
        recyclerView = activity?.findViewById(R.id.list_todo) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter



        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener(this)
        return view




    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.fab -> {
                adapter?.addTask()

                val cursor = db.query(
            TaskContract.TaskEntry.TABLE,
            arrayOf(TaskContract.TaskEntry.ID, TaskContract.TaskEntry.COL_TASK_TITLE), null, null, null, null, null)
        while (cursor.moveToNext()) {
            val idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE)
        }
        cursor.close()
        db.close()

    }
 }


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode)
        {
            42 -> {
                val action = data?.getStringExtra("Action")

                when (action) {
                    "ADD" -> {
                        val taskTitle = data.getStringExtra("TaskTitle")
                        val taskText = data.getStringExtra("TaskText")

                        adapter?.addTask(taskTitle, taskText)
                    }

                }
            }
        }
    }






}




