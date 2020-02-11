package com.example.mininotes.ui.delete

 import android.os.Bundle
 import android.view.LayoutInflater
 import android.view.View
 import android.view.ViewGroup
 import androidx.fragment.app.Fragment
 import com.example.mininotes.Db.Databasehelper
 import com.example.mininotes.R


class DeleteFragment : Fragment(){
    private lateinit var mHelper: Databasehelper



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view : View =inflater.inflate(R.layout.fragment_delete,container,false)


            return view
     }





    }

