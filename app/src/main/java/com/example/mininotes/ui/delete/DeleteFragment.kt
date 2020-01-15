package com.example.mininotes.ui.delete

 import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
 import androidx.fragment.app.Fragment
 import androidx.lifecycle.ViewModelProviders
import com.example.mininotes.R
import com.example.mininotes.ui.archive.ArchiveViewModel

class DeleteFragment : Fragment() {
    private lateinit var deleteViewModel: DeleteViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        deleteViewModel =
            ViewModelProviders.of(this).get(DeleteViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_archive, container, false)

        return root
    }

}