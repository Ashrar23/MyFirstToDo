package com.example.mininotes.ui.delete

 import android.content.Intent
 import android.os.Bundle
 import android.view.LayoutInflater
 import android.view.View
 import android.view.ViewGroup
 import android.widget.ArrayAdapter
 import androidx.fragment.app.Fragment
 import com.example.mininotes.AddNotesActivity
 import com.example.mininotes.Db.CheckDbHelper
 import com.example.mininotes.Db.TaskContract
 import com.example.mininotes.R
 import com.google.android.material.floatingactionbutton.FloatingActionButton


class DeleteFragment : Fragment(){
    private lateinit var mHelper: CheckDbHelper



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view : View =inflater.inflate(R.layout.fragment_delete,container,false)


            return view
     }





    }

