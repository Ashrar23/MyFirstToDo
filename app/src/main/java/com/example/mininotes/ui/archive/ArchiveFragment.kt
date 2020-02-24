package com.example.mininotes.ui.archive

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mininotes.Db.Databasehelper
import com.example.mininotes.Interface.MainInterface
import com.example.mininotes.MainActivity
import com.example.mininotes.MyObject
import com.example.mininotes.R
import com.example.mininotes.adapter.MyAdapter
import com.example.mininotes.ui.notes.NotesFragment
import java.util.Observer

class ArchiveFragment : Fragment(), MainInterface {

    lateinit var mHelper: Databasehelper
    lateinit var adapter: MyAdapter
    lateinit var recyclerView: RecyclerView


    var hashMapArrayList: ArrayList<HashMap<String, String>> = ArrayList()

    var actionMode : ActionMode? = null



    override fun mainInterface(size: Int) {
        if (actionMode == null) actionMode = view?.startActionMode(ActionModeCallBack())
        if (size > 0) actionMode?.setTitle("$size item selected")
        else actionMode?.finish()
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        isMultiSelectOn = false

        val view: View = inflater.inflate(R.layout.fragment_archive, container, false)
        setHasOptionsMenu(true)
        recyclerView = view.findViewById(R.id.list_archive)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        mHelper = Databasehelper(context!!)

        readData()

        return view
    }


    private fun readData() {
        val list = mHelper.users

        hashMapArrayList.clear()
        if (list != null && list.size > 0) {

            for (user: MyObject in list) {
                val hashMap = HashMap<String, String>()
                hashMap.put(ID, user.id.toString())
                hashMap.put(COL_TASK_TITLE, user.title)
                hashMap.put(COL_TASK_TEXT, user.text)
                hashMap.put(COL_TASK_DATE, user.record)

                hashMapArrayList.add(hashMap)
            }
            adapter = MyAdapter((context as MainActivity?)!!,hashMapArrayList,this)
            recyclerView.adapter = adapter


        }

    }


    inner class ActionModeCallBack : ActionMode.Callback {

        var shouldResetRecyclerView = true


        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when(item?.itemId){

                R.id.action_unarchive ->{



                 }

            }

            return true
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = mode?.getMenuInflater()
            inflater?.inflate(R.menu.unarchive, menu)
            return true        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menu?.findItem(R.id.action_unarchive)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
             return true        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            if (shouldResetRecyclerView) {
                adapter.selectedIds.clear()
                adapter.notifyDataSetChanged()
            }
            isMultiSelectOn = false
            actionMode = null
            shouldResetRecyclerView = true        }
    }

    companion object{
        var ID: String = "id"
        val COL_TASK_TITLE: String = "title"
        val COL_TASK_TEXT: String = "text"
        val COL_TASK_DATE: String = "date"

        var isMultiSelectOn = false



    }
}