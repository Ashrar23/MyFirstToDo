package com.example.mininotes.adapter

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mininotes.*
import com.example.mininotes.Db.CheckDbHelper
import com.example.mininotes.Db.TaskContract
import com.example.mininotes.`interface`.MainInterface
import com.example.mininotes.`interface`.ViewHolderClickListener
import com.example.mininotes.ui.notes.NotesFragment
import java.text.SimpleDateFormat
import java.util.*

class MyAdapter(private val context: Context, private val mHelper: CheckDbHelper, private val mainInterface: MainInterface) :  RecyclerView.Adapter<MyViewHolder>(),
    ViewHolderClickListener {

    private var list = mutableListOf<MyObject>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        initList()
        super.onAttachedToRecyclerView(recyclerView)
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, itemType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_items_todo, viewGroup, false)
        return MyViewHolder(view,this)
    }

     override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        val myObject = list[position]
        myViewHolder.bind(myObject)
        val activity: Activity = context as Activity

        myViewHolder.textView.setOnClickListener{

            activity.launchActivity<AddNotesActivity>(42) {
                putExtra("PositionInList", position.toString())
                putExtra("TaskTitle", myObject.title)
                putExtra("TaskText", myObject.text)
            }
        }





    }

    private fun initList() {
        list.clear()
        val db = mHelper.readableDatabase
        val cursor = db.query(TaskContract.TaskEntry.TABLE,
            arrayOf(TaskContract.TaskEntry.ID,
                TaskContract.TaskEntry.COL_TASK_TITLE,
                TaskContract.TaskEntry.COL_TASK_TEXT,
                TaskContract.TaskEntry.COL_TASK_DATE),null, null, null, null, TaskContract.TaskEntry.ID)
        while (cursor.moveToNext()) {
            val id = cursor.getColumnIndex(TaskContract.TaskEntry.ID)
            val idTitle = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE)
            val idText = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TEXT)
            val idDate = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DATE)

            list.add(
                MyObject(
                    cursor.getString(id),
                    cursor.getString(idTitle),
                    cursor.getString(idText),
                    cursor.getString(idDate)
                )
            )
        }
        notifyDataSetChanged()

        cursor.close()
        db.close()
    }

    override fun getItemCount(): Int {
        return list.size
    }



    fun addTask(taskTitle: String?, taskText: String?) {
        val values = ContentValues()

        val sdf = SimpleDateFormat("dd/MM/yyyy/", Locale.US)
        val date = sdf.format(Date())

        values.put(TaskContract.TaskEntry.ID, list.size)
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, taskTitle)
        values.put(TaskContract.TaskEntry.COL_TASK_TEXT, taskText)
        values.put(TaskContract.TaskEntry.COL_TASK_DATE, date)

        val db = mHelper.readableDatabase
        val id = db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE)
        db.close()

        list.add(
            MyObject(
                list.size.toString(),
                taskTitle,
                taskText,
                date
            )
        )

        notifyItemInserted(list.size)
    }

    fun addTask() {
        val test: Activity = context as Activity

        test.launchActivity<AddNotesActivity>(42) {

        }
    }

    fun deleteTask() {

        val db = mHelper.readableDatabase
        db.delete(TaskContract.TaskEntry.TABLE,
            "id=" + deleteSelectedIds(), null)
        db.close()
    }

    fun modifyTask(taskPosition: String, taskTitle: String, taskText: String) {
        val target = list[taskPosition.toInt()]

        target.title = taskTitle
        target.text = taskText

        val values = ContentValues()

        val sdf = SimpleDateFormat("dd/MM/yyyy/", Locale.US)
        val date = sdf.format(Date())

        values.put(TaskContract.TaskEntry.ID, taskPosition)
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, taskTitle)
        values.put(TaskContract.TaskEntry.COL_TASK_TEXT, taskText)
        values.put(TaskContract.TaskEntry.COL_TASK_DATE, date)

        val db = mHelper.readableDatabase
        db.update(TaskContract.TaskEntry.TABLE,
            values, TaskContract.TaskEntry.ID + "=" + target.ID, null)

        db.close()

        notifyItemChanged(taskPosition.toInt())
    }

    fun updateNotesPositionInDb() {
        val db = mHelper.readableDatabase

        var i = 0
        while (i < list.size) {
            val values = ContentValues()

            values.put(TaskContract.TaskEntry.ID, i)
            db.update(TaskContract.TaskEntry.TABLE,
                values, TaskContract.TaskEntry.ID + "=? AND " + TaskContract.TaskEntry.COL_TASK_TITLE + "=?", arrayOf(list[i].ID, list[i].title))
            i++

        }
        db.close()
    }

    override fun onLongTap(index: Int) {
        if (!NotesFragment.isMultiSelectOn) {
            NotesFragment.isMultiSelectOn = true
        }
        addIDIntoSelectedIds(index)    }

    override fun onTap(index: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addIDIntoSelectedIds(index: Int) {
        val id = modelList[index].ID
        if (selectedIds.contains(id))
            selectedIds.remove(id)
        else
            selectedIds.add(id)

        notifyItemChanged(index)
        if (selectedIds.size < 1) NotesFragment.isMultiSelectOn = false
        mainInterface.mainInterface(selectedIds.size)

    }

    fun deleteSelectedIds() {
        if (selectedIds.size < 1)  return
        val selectedIdIteration =  selectedIds.listIterator()
        while (selectedIdIteration.hasNext()) {
            val selectedItemID = selectedIdIteration.next()
            var indexOfModelList = 0
            val modelListIteration: MutableListIterator<MyObject> = modelList.listIterator();
            while (modelListIteration.hasNext()) {
                val model = modelListIteration.next()
                if (selectedItemID.equals(model.ID)) {
                    modelListIteration.remove()
                    selectedIdIteration.remove()
                    notifyItemRemoved(indexOfModelList)
                }
                indexOfModelList++

            }
            NotesFragment.isMultiSelectOn = false
        }
    }
    var modelList: MutableList<MyObject> = ArrayList<MyObject>()
    val selectedIds: MutableList<String> = ArrayList<String>()
}