package com.example.mininotes

import android.view.View
 import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mininotes.adapter.MyAdapter
import com.example.mininotes.ui.notes.NotesAddTask

class MyViewHolder(itemView: View, val tap: NotesAddTask) : RecyclerView.ViewHolder(itemView),
         View.OnLongClickListener,View.OnClickListener
{

    val titleView: TextView = itemView.findViewById<View>(R.id.title) as TextView
    val textView: TextView = itemView.findViewById<View>(R.id.text) as TextView
    val cardView : CardView = itemView.findViewById<View>(R.id.card) as CardView

    fun bind(myObject: MyObject) {
        titleView.text = myObject.title
        textView.text = myObject.text
        cardView.setOnClickListener(this)
        cardView.setOnLongClickListener(this)
     }

    override fun onLongClick(v: View?): Boolean {
        tap.onLongTap(adapterPosition)
        return true
    }

    override fun onClick(v: View?) {
    tap.onTap(adapterPosition)
    }

}