package com.example.mininotes.ui.archive

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mininotes.Db.Databasehelper
import com.example.mininotes.Interface.MainInterface
import com.example.mininotes.MainActivity
import com.example.mininotes.MyObject
import com.example.mininotes.R
import com.example.mininotes.adapter.MyAdapter
import kotlinx.android.synthetic.main.appbar_main.*

class ArchiveFragment : Fragment(), MainInterface {

    lateinit var mHelper: Databasehelper
    lateinit var adapter: MyAdapter
    lateinit var recyclerView: RecyclerView


    var hashMapArrayList: ArrayList<HashMap<String, String>> = ArrayList()

    var actionMode : ActionMode? = null

    override fun mainInterface(size: Int) {
         if (actionMode == null) actionMode =   view?.startActionMode(ActionModeCallBack())
         if (size > 0) actionMode?.title = "$size item selected"
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
            adapter = MyAdapter((context as MainActivity?)!!,hashMapArrayList, this)
            recyclerView.adapter = adapter


        }

    }

    inner class ActionModeCallBack : ActionMode.Callback {

        var shouldResetRecyclerView = true


        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when(item?.itemId){

                R.id.action_unarchive ->{
                     adapter.unarchiveselectedIds()
                         actionMode?.finish()

                 }

            }

            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = mode?.menuInflater
            inflater?.inflate(R.menu.unarchive, menu)
            return true        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
              return true        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            if (shouldResetRecyclerView) {
                adapter.selectedIds.clear()
                adapter.notifyDataSetChanged()
            }
            isMultiSelectOn = false
            actionMode = null
            shouldResetRecyclerView = false      }
    }

    companion object{
        var ID: String = "id"
        val COL_TASK_TITLE: String = "title"
        val COL_TASK_TEXT : String = "text"
        val COL_TASK_DATE : String = "date"

        var isMultiSelectOn = false



    }
}