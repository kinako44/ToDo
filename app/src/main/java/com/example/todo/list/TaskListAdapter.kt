package com.example.todo.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.todo.R


import com.example.todo.data.Task
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults


class TaskListAdapter(
    private val model: RealmResults<Task>,
    private val listener: StateListener,
    autoUpdate: Boolean
) : RealmRecyclerViewAdapter<Task, TaskListAdapter.ViewHolder>(model, autoUpdate) {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_tasklist, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = model[position]
        with(holder) {
            taskBody.text = item!!.body
            taskBody.tag = item
            check.isChecked = item.isCompleted

            check.setOnClickListener {
                listener.onCheckBoxClick(item)
            }

            taskBody.setOnClickListener {
                listener.onTaskClick(item)
            }
        }
    }

    override fun getItemCount(): Int = model.size

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        listener.onViewAttachedToWindow(holder.check.isChecked, holder.taskBody.tag as Task)
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val taskBody: TextView = mView.findViewById(R.id.task_body)
        val check: CheckBox = mView.findViewById(R.id.check_completion)

    }

    interface StateListener {

        fun onCheckBoxClick(tag: Task)

        fun onViewAttachedToWindow(isCompleted: Boolean, tag: Task)

        fun onTaskClick(task: Task)

    }

}