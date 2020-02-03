package com.example.mininotes.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mininotes.*
import com.example.mininotes.ui.notes.NotesAddTask

class MyAdapter(
    private val context: Context, private val notesAddTask: NotesAddTask
) : RecyclerView.Adapter<MyViewHolder>() {

    private var list = mutableListOf<MyObject>()


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        notesAddTask.initList()
        super.onAttachedToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, itemType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_items_todo, viewGroup, false)
        return MyViewHolder(view, notesAddTask)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        val myObject = list[position]
        myViewHolder.bind(myObject)
        val activity: Activity = context as Activity

        myViewHolder.textView.setOnClickListener {

            activity.launchActivity<AddNotesActivity>(42) {
                putExtra("PositionInList", position.toString())
                putExtra("TaskTitle", myObject.title)
                putExtra("TaskText", myObject.text)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}