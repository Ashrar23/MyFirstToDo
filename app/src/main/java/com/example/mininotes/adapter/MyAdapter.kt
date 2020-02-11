package com.example.mininotes.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mininotes.*
import com.example.mininotes.Db.Databasehelper
import com.example.mininotes.Db.Databasehelper.Companion.COL_TASK_DATE
import com.example.mininotes.Db.Databasehelper.Companion.COL_TASK_TEXT
import com.example.mininotes.Db.Databasehelper.Companion.COL_TASK_TITLE
import com.example.mininotes.Interface.MainInterface
import com.example.mininotes.Interface.ViewHolderClickListner
import com.example.mininotes.ui.notes.NotesFragment

class MyAdapter(val context: Context, val mainInterface: MainInterface,val arrayList: ArrayList<HashMap<String,String>>) : RecyclerView.Adapter<MyViewHolder>(), ViewHolderClickListner {

          lateinit var mHelper:Databasehelper




    override fun onCreateViewHolder(viewGroup: ViewGroup, itemType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_items_todo, viewGroup, false)
        return MyViewHolder(view,this)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val id = arrayList.get(position).get(ID)
        if (selectedIds.contains(id)) {
            holder.check.background= ColorDrawable(ContextCompat.getColor( context,R.color.colorPrimary))
        } else {
            holder.check.background = ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent))
        }

        holder.titleView.text = arrayList.get(position).get(COL_TASK_TITLE)
        holder.textView.text = arrayList.get(position).get(COL_TASK_TEXT)
        holder.time.text = arrayList.get(position).get(COL_TASK_DATE)
        mHelper = Databasehelper(context)


        holder.card.setOnClickListener{
            val intent = Intent(context,UpdateNotesActivity::class.java)
            intent.putExtra(ID,arrayList.get(position).get(ID))
            intent.putExtra(COL_TASK_TITLE,arrayList.get(position).get(COL_TASK_TITLE))
            intent.putExtra(COL_TASK_TEXT,arrayList.get(position).get(COL_TASK_TEXT))
            intent.putExtra(COL_TASK_DATE,arrayList.get(position).get(COL_TASK_DATE))
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
        if(NotesFragment.isMultiSelectOn){
            addIDIntoSelectedIds(index)
        }else{
            Toast.makeText(context, "Clicked Item @ Position ${index + 1}", Toast.LENGTH_SHORT).show()

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
companion object
{
    var ID: String = "id"
    val COL_TASK_TITLE: String = "title"
    val COL_TASK_TEXT: String = "text"
    val COL_TASK_DATE: String = "date"
}
}