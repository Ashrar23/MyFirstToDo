package com.example.mininotes.ui.archive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mininotes.R
import java.util.Observer

class ArchiveFragment : Fragment() {
    private lateinit var archiveViewModel: ArchiveViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        archiveViewModel =
            ViewModelProviders.of(this).get(ArchiveViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_archive, container, false)
        val textView: TextView = root.findViewById(R.id.archive)
        archiveViewModel.text.observe(this, androidx.lifecycle.Observer{
            textView.text = it

        })

        return root
    }

}