package com.example.mininotes.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mininotes.R
import com.example.mininotes.ui.delete.DeleteViewModel

class NotesFragment : Fragment() {
    private lateinit var notesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notesViewModel =
            ViewModelProviders.of(this).get(notesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notes, container, false)
        val listView : ListView= root.findViewById(R.id.list_todo)


        return root


    }
}