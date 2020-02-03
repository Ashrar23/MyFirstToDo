
package com.example.mininotes.ui.notes

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mininotes.Db.CheckDbHelper
import com.example.mininotes.Db.TaskContract
import com.example.mininotes.`interface`.MainInterface
import com.example.mininotes.adapter.MyAdapter
import com.example.mininotes.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NotesFragment: Fragment(), View.OnClickListener, MainInterface  {

     lateinit var mHelper: CheckDbHelper
     var actionMode: ActionMode? = null
    lateinit var  adapter : MyAdapter



    companion object {
         var isMultiSelectOn = false

     }


    override fun mainInterface(size: Int) {
        if (actionMode == null) actionMode = activity?.startActionMode(ActionModeCallback())
        if (size > 0) actionMode?.setTitle("$size item selected")
        else actionMode?.finish()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_notes, container, false)

        isMultiSelectOn = false
        mHelper = CheckDbHelper(context!!)
        val  recyclerView = activity?.findViewById(R.id.list_todo) as RecyclerView?
        recyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = MyAdapter(context!!,mHelper,this)
        recyclerView?.adapter = adapter
        adapter.modelList




        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener(this)
        return view
    }
    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.fab -> {
               adapter.addTask()

    }
 }


    }

    inner class ActionModeCallback : ActionMode.Callback{
        var shouldResetRecyclerView = true

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.getItemId()) {
                R.id.nav_delete -> {

                    val dialog = AlertDialog.Builder(activity)
                    dialog.setTitle("Delete")
                    dialog.setMessage( "Are you sure to delete")
                    dialog.setPositiveButton("OK"){ _, which ->
                        adapter.deleteTask()
                     }
                    dialog.setNegativeButton("Cancel"){ _, which ->

                    }
                    shouldResetRecyclerView = false
                    actionMode?.finish()
                    return true


                }

                R.id.nav_archive -> {

                    shouldResetRecyclerView = false
                    actionMode?.finish()
                    return true

                }
            }



            return false           }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = mode?.getMenuInflater()
            inflater?.inflate(R.menu.menu, menu)
            return true

        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menu?.findItem(R.id.nav_delete)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            menu?.findItem(R.id.nav_archive)?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            if (shouldResetRecyclerView) {
                adapter.notifyDataSetChanged()
            }
            isMultiSelectOn = false
            actionMode = null
            shouldResetRecyclerView = true            }


    }




        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK)
            return

        when (requestCode)
        {
            42 -> {
                val action = data?.getStringExtra("Action")

                when (action) {
                    "ADD" -> {
                        val taskTitle = this.arguments?.getString("taskTitle")
                        val taskText = this.arguments?.getString("taskText")

                        adapter.addTask(taskTitle,taskText)
                    }

                }
            }
        }
    }



}




