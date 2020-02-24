package com.example.mininotes.adapter

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mininotes.Db.Databasehelper
import com.example.mininotes.Db.Databasehelper.Companion.TABLEARCHIVE
import com.example.mininotes.Interface.MainInterface
import com.example.mininotes.Interface.ViewHolderClickListner
import com.example.mininotes.MyObject
import com.example.mininotes.MyViewHolder
import com.example.mininotes.R
import com.example.mininotes.UpdateNotesActivity
import com.example.mininotes.ui.archive.ArchiveFragment
import com.example.mininotes.ui.notes.NotesFragment


class MyAdapter(
    val context: Context,
    val arrayList: ArrayList<HashMap<String, String>>,
    val mainInterface: MainInterface

) : RecyclerView.Adapter<MyViewHolder>(), ViewHolderClickListner {

    lateinit var mHelper: Databasehelper



    override fun onCreateViewHolder(viewGroup: ViewGroup, itemType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_items_todo, viewGroup, false)
        return MyViewHolder(view, this)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val id = arrayList.get(position).get(ID)
        if (selectedIds.contains(id)) {
            holder.check.setBackgroundResource(R.drawable.cardborderselect)
        } else {
            holder.check.setBackgroundResource(R.drawable.cardborder)
        }

        holder.titleView.text = arrayList.get(position).get(COL_TASK_TITLE)
        holder.textView.text = arrayList.get(position).get(COL_TASK_TEXT)
        holder.time.text = arrayList.get(position).get(COL_TASK_DATE)
        mHelper = Databasehelper(context)


        holder.titleView.setOnClickListener {

            val intent = Intent(context, UpdateNotesActivity::class.java)
            intent.putExtra(ID, arrayList.get(position).get(ID))
            intent.putExtra(COL_TASK_TITLE, arrayList.get(position).get(COL_TASK_TITLE))
            intent.putExtra(COL_TASK_TEXT, arrayList.get(position).get(COL_TASK_TEXT))
            intent.putExtra(COL_TASK_DATE, arrayList.get(position).get(COL_TASK_DATE))
            context.startActivity(intent)

        }


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onLongTap(index: Int) {
        if (!NotesFragment.isMultiSelectOn) {
            NotesFragment.isMultiSelectOn = true
        }
        addIDIntoSelectedIds(index)
    }

    override fun onTap(index: Int) {
        if (NotesFragment.isMultiSelectOn) {
            addIDIntoSelectedIds(index)
        } else {
            Toast.makeText(context, "Clicked Item @ Position ${index + 1}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun deleteSelectedId() {
        if (selectedIds.size < 1) return
        val selectedIdIteration = selectedIds.listIterator()
        while (selectedIdIteration.hasNext()) {
            val selectedItemID = selectedIdIteration.next()
            var indexOfModelList = 0
            val modelListIteration: MutableListIterator<HashMap<String, String>> =
                arrayList.listIterator()
            while (modelListIteration.hasNext()) {
                val model = modelListIteration.next()
                if (selectedItemID.equals(model.get(ID))) {
                    modelListIteration.remove()
                    selectedIdIteration.remove()
                    mHelper.delete(selectedItemID.toInt())
                    notifyItemRemoved(indexOfModelList)
                }
                indexOfModelList++
            }
            NotesFragment.isMultiSelectOn = false
        }
    }

    fun archiveselectedIds() {

        if (selectedIds.size < 1) return
        val selectedIdIteration = selectedIds.listIterator()
        while (selectedIdIteration.hasNext()) {
            val selectedItemId = selectedIdIteration.next()
            var indexModelList = 0
            val modelListIterator: MutableListIterator<HashMap<String, String>> =
                arrayList.listIterator()
            while (modelListIterator.hasNext()) {
                val model = modelListIterator.next()
                if (selectedItemId.equals(model.get(ID))) {
                    modelListIterator.remove()
                    selectedIdIteration.remove()
                    //mHelper.getselectedid(selectedItemId.toInt())
                        mHelper.archive(selectedItemId.toInt())
                        mHelper.delete(selectedItemId.toInt())
                        notifyItemRemoved(indexModelList)
                }
                indexModelList++
            }
            ArchiveFragment.isMultiSelectOn
        }
    }

    fun addIDIntoSelectedIds(position: Int) {
        val id = arrayList.get(position).get(ID).toString()
        if (selectedIds.contains(id))
            selectedIds.remove(id)
        else
            selectedIds.add(id)
        notifyItemChanged(position)
        if (selectedIds.size < 1) NotesFragment.isMultiSelectOn = false
        mainInterface.mainInterface(selectedIds.size)
    }

    val selectedIds: MutableList<String> = java.util.ArrayList<String>()

    companion object {
        var ID: String = "id"
        val COL_TASK_TITLE: String = "title"
        val COL_TASK_TEXT: String = "text"
        val COL_TASK_DATE: String = "date"

        val TABLE: String = "tasks"


    }


    val getselectedid : List<MyObject>
        get()
        {
            val userList = ArrayList<MyObject>()
            val id = selectedIds
            val db = mHelper.getReadableDatabase()
            val selectQuery = "SELECT * FROM $TABLE WHERE  $id"
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val user = MyObject()
                    user.id = cursor.getInt(cursor.getColumnIndex(ID))
                    user.title = cursor.getString(cursor.getColumnIndex(COL_TASK_TITLE))
                    user.text = cursor.getString(cursor.getColumnIndex(COL_TASK_TEXT))
                    user.record = cursor.getString(cursor.getColumnIndex(COL_TASK_DATE))
                    userList.add(user)
                    notifyDataSetChanged()
                } while (cursor.moveToNext())
            }
            return userList
        }


}