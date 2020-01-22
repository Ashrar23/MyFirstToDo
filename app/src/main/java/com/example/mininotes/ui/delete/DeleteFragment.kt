package com.example.mininotes.ui.delete

 import android.os.Bundle
 import android.view.LayoutInflater
 import android.view.View
 import android.view.ViewGroup
 import androidx.fragment.app.Fragment
 import com.example.mininotes.Db.CheckDbHelper
 import com.example.mininotes.Db.TaskContract
 import com.example.mininotes.R


class DeleteFragment : Fragment() {
    private lateinit var mHelper: CheckDbHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {



         return inflater.inflate(R.layout.fragment_delete, container, false)
    }


    fun deleteTask(taskId: String) {

        val db = mHelper.readableDatabase
        db.delete(
            TaskContract.TaskEntry.TABLE,
            "id=" + taskId, null)
        db.close()
    }

}