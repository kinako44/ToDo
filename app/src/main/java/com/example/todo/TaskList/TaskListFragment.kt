package com.example.todo.TaskList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.todo.EditToDO
import com.example.todo.R
import com.example.todo.data.Task

import io.realm.Realm


class TaskListFragment : Fragment(), TaskListContract.View {

    // TODO: Customize parameters
    private var columnCount = 1
    private var listener: ItemClickListener? = null
    override lateinit var presenter: TaskListContract.Presenter


    internal var itemClickListener : ItemClickListener = object : ItemClickListener {
        override fun onCheckBoxClick(view: View) {
            val taskBody: TextView = view.findViewById(R.id.task_body)
            val checkCompletion: CheckBox = view.findViewById(R.id.check_completion)
            presenter.switchTaskFontColor(checkCompletion.isChecked, taskBody)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasklist_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                // TODO: repositoryに移譲
                val realm = Realm.getDefaultInstance()
                val itemList = realm.where(Task::class.java).sort(Task::id.name).findAll()
                realm.close()

                adapter = TaskListAdapter(itemList, itemClickListener)
            }
        }

        activity?.findViewById<FloatingActionButton>(R.id.fab_add_task)?.setOnClickListener {
            presenter.addTask()
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        /*
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
        */
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    override fun showAddTask() {
        val intent = Intent(context, EditToDO::class.java)
        startActivityForResult(intent, 1)   // TODO change key

    }

    override fun changeFontColorToGray(textView: TextView) {
        textView.setTextColor(ContextCompat.getColor(context!!, R.color.colorFontPrimaryLight))
    }

    override fun changeFontColorToBlack(textView: TextView) {
        textView.setTextColor(ContextCompat.getColor(context!!, R.color.colorFontPrimary))
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */

    interface ItemClickListener {

        fun onCheckBoxClick(view: View)

    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TaskListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
