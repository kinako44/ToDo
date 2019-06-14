package com.example.todo.TaskList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.todo.R


import com.example.todo.data.Task
import io.realm.RealmResults


class TaskListAdapter(
    private val model: RealmResults<Task>,
    private val listener: TaskListFragment.ItemClickListener
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    init {
        /*
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Task
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            //if ()
            //listener?.onListFragmentInteraction(item)
        }
        */
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_tasklist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = model[position]
        holder.taskBody.text = item?.task ?: ""
        holder.checkCompletion.isChecked = item?.isCompleted ?: false

        with(holder.checkCompletion) {
            setOnClickListener {
                listener.onCheckBoxClick(holder.mView)
            }
        }

    }

    override fun getItemCount(): Int = model.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val taskBody: TextView = mView.findViewById(R.id.task_body)
        val checkCompletion: CheckBox = mView.findViewById(R.id.check_completion)

    }
}
