package com.example.mininotes

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mininotes.Interface.ViewHolderClickListner

class MyViewHolder (itemView: View , val tap : ViewHolderClickListner) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener, View.OnClickListener {

    val titleView = itemView.findViewById<TextView>(R.id.task)
    val textView = itemView.findViewById<TextView>(R.id.task_text)
    val time = itemView.findViewById<TextView>(R.id.txt_time)
    val card = itemView.findViewById<CardView>(R.id.card)
    val check = itemView.findViewById<ConstraintLayout>(R.id.select)

    val delete = itemView.findViewById<Button>(R.id.action_delete)



    init {

        card.setOnClickListener(this)
        card.setOnLongClickListener(this)
    }


    override fun onLongClick(v: View?): Boolean {
        tap.onLongTap(adapterPosition)

        return true
     }

    override fun onClick(v: View?) {
        tap.onTap(adapterPosition)
    }


}