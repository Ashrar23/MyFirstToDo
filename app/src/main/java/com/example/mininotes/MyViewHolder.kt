package com.example.mininotes

import android.view.View
 import android.widget.TextView
 import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val titleView: TextView = itemView.findViewById<View>(R.id.title) as TextView
    val textView: TextView = itemView.findViewById<View>(R.id.text) as TextView

    fun bind(myObject: MyObject) {
        titleView.text = myObject.title
        textView.text = myObject.text
     }

}