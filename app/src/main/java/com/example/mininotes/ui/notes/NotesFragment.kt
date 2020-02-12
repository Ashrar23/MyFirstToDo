
package com.example.mininotes.ui.notes

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mininotes.AddNotesActivity
import com.example.mininotes.Db.Databasehelper
import com.example.mininotes.Interface.MainInterface
import com.example.mininotes.MainActivity
import com.example.mininotes.MyObject
 import com.example.mininotes.adapter.MyAdapter
import com.example.mininotes.R


class NotesFragment: Fragment(),MainInterface {

    lateinit var mHelper: Databasehelper
    lateinit var adapter: MyAdapter
    lateinit var recyclerView: RecyclerView


    var hashMapArrayList: ArrayList<HashMap<String, String>> = ArrayList()

    var actionMode : ActionMode? = null


    override fun mainInterface(size: Int) {
        if (actionMode == null) actionMode = view?.startActionMode(ActionModeCallback())
        if (size > 0) actionMode?.setTitle("$size item selected")
        else actionMode?.finish()     }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        isMultiSelectOn = false

        val view: View = inflater.inflate(R.layout.fragment_notes, container, false)
        setHasOptionsMenu(true)
         recyclerView = view.findViewById(R.id.list_todo)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        mHelper = Databasehelper(context!!)

        return view
    }

    private fun readData() {
        val list = mHelper.user

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
            adapter = MyAdapter((context as MainActivity?)!!, this, hashMapArrayList)
            recyclerView.adapter = adapter


        }




    }




    override fun onResume() {
        readData()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.insert_data, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.insert->{
                val intent=Intent(activity,AddNotesActivity::class.java)
                activity?.startActivity(intent)
                actionMode?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }




    inner class ActionModeCallback : ActionMode.Callback {
        var shouldResetRecyclerView = true

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.itemId) {

                R.id.action_delete -> {
                    val builder = AlertDialog.Builder(activity)
                    builder.setTitle("Delete item")
                    builder.setMessage("deleted file may not be restore")
                     builder.setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                         adapter.deleteSelectedIds(0)
                         Toast.makeText(activity, "deleted successfully", Toast.LENGTH_LONG)
                            .show()                    }

                    builder.setNegativeButton("No") { dialog: DialogInterface?, which: Int ->

                            actionMode?.finish()

                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()

                }

                R.id.acton_archive -> {



                }

            }
            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = mode?.getMenuInflater()
            inflater?.inflate(R.menu.delete, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menu?.findItem(R.id.nav_delete)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            if (shouldResetRecyclerView) {
                adapter.selectedIds.clear()
                adapter.notifyDataSetChanged()
            }
            isMultiSelectOn = false
            actionMode = null
            shouldResetRecyclerView = true
        }



    }

    companion object{
        var ID: String = "id"
        val COL_TASK_TITLE: String = "title"
        val COL_TASK_TEXT: String = "text"
        val COL_TASK_DATE: String = "date"

        var isMultiSelectOn = false


    }



}



