package com.example.mininotes.ui.notes

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.mininotes.Db.CheckDbHelper
import com.example.mininotes.Db.Instance
import com.example.mininotes.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.notes_dialog.view.*


class NotesFragment : Fragment() {
    private lateinit var mHelper: CheckDbHelper
    private lateinit var mTaskListView: ListView
    private var mAdapter: ArrayAdapter<String>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mTaskListView = view!!.findViewById(R.id.list_todo) as ListView

        mHelper = activity?.let { CheckDbHelper(it) }!!

       val  fab = view!!.findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(View.OnClickListener { val mDialogview = LayoutInflater.from(activity).inflate(R.layout.notes_dialog, null)
            val dialog = this.activity?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setTitle("Add a new task")
                    .setView(mDialogview)
            }

            val malertdialog = dialog?.show()

            mDialogview.btn_add.setOnClickListener {

                malertdialog?.dismiss()

                val title = mDialogview.edtxt_title.text.toString()
                val note = mDialogview.edtxt_note.text.toString()
                val db = mHelper.writableDatabase
                val values = ContentValues()
                values.put(Instance.TaskEntry.COL_TASK_TITLE, title)
                values.put(Instance.TaskEntry.COL_TASK_NOTES, note)
                db.insertWithOnConflict(
                    Instance.TaskEntry.TABLE,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
                db.close()

                updateUI()
            } });


        return inflater.inflate(R.layout.fragment_notes, container, false)
        return view




    }





    fun updateUI() {

        val taskList = ArrayList<String>()
        val db = mHelper.readableDatabase
        val cursor = db.query(
            Instance.TaskEntry.TABLE,
            arrayOf(
                Instance.TaskEntry._ID,
                Instance.TaskEntry.COL_TASK_TITLE,
                Instance.TaskEntry.COL_TASK_NOTES
            ), null, null, null, null, null
        )
        while (cursor.moveToNext()) {
            val idx = cursor.getColumnIndex(Instance.TaskEntry.COL_TASK_TITLE)
            val idy = cursor.getColumnIndex(Instance.TaskEntry.COL_TASK_NOTES)
            taskList.add(cursor.getString(idx))
            taskList.add(cursor.getString(idy))
        }

        if (mAdapter == null) {
            mAdapter =
                activity?.let { ArrayAdapter(it, R.layout.list_items_todo, R.id.task, taskList) }
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