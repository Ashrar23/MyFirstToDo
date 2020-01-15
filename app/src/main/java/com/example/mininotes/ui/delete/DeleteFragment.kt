package com.example.mininotes.ui.delete

 import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
 import androidx.fragment.app.Fragment
 import androidx.lifecycle.ViewModelProviders
import com.example.mininotes.R

class DeleteFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete, container, false)
    }

}