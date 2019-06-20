package com.example.todo.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.todo.R


import com.example.todo.data.Task


class TaskListAdapter(
    private val model: List<Task>,  // RealmResults<Task>
    private val listener: TaskListFragment.RecyclerViewStateListener
) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_tasklist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = model[position]
        with(holder) {
            taskBody.text = item.body
            taskBody.tag = item
            checkCompletion.isChecked = item.isCompleted

            checkCompletion.setOnClickListener {
                listener.onCheckBoxClick(item)
            }
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        listener.onViewAttachedToWindow(holder.checkCompletion.isChecked, holder.taskBody.tag as Task)
    }

    override fun getItemCount(): Int = model.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val taskBody: TextView = mView.findViewById(R.id.task_body)
        val checkCompletion: CheckBox = mView.findViewById(R.id.check_completion)

    }
}
