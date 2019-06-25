package com.example.todo.list

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
import android.widget.TextView
import com.example.todo.edit.TaskEditActivity
import com.example.todo.R
import com.example.todo.data.Task
import com.example.todo.detail.TaskDetailActivity


class TaskListFragment : Fragment(), TaskListContract.View {

    private var columnCount = 1
    override lateinit var presenter: TaskListContract.Presenter

    private val recyclerViewStateListener: RecyclerViewStateListener = object: RecyclerViewStateListener {

        override fun onCheckBoxClick(tag: Task) {
            val isTaskCompleted = !tag.isCompleted
            switchTaskFontColor(isTaskCompleted, tag)
            presenter.updateTask(tag.id, isTaskCompleted)
        }

        override fun onTaskClick(task: Task) {
            presenter.showTaskDetail(task.id)
        }

        override fun onViewAttachedToWindow(isCompleted: Boolean, tag: Task) {
            switchTaskFontColor(isCompleted, tag)
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

                val itemList = presenter.getTasks()
                adapter = TaskListAdapter(itemList, recyclerViewStateListener, autoUpdate = true)
            }
        }

        activity?.findViewById<FloatingActionButton>(R.id.fab_add_task)?.setOnClickListener {
            presenter.addTask()
        }

        return view
    }

    private fun switchTaskFontColor(isCompleted: Boolean, tag: Task) {
        when (isCompleted) {
            true -> presenter.switchTaskFontColorToGray(tag)
            false -> presenter.switchTaskFontColorToBlack(tag)
        }
    }

    override fun showAddTaskUi() {
        val intent = Intent(context, TaskEditActivity::class.java)
        startActivity(intent)
    }

    override fun showTaskDetailUi(taskId: Int) {
        val intent = Intent(context, TaskDetailActivity::class.java)
        intent.putExtra(TaskDetailActivity.ARG_TASK_DETAIL_KEY, taskId)
        startActivity(intent)
    }

    override fun changeFontColorToGray(tag: Task) {
        val textView = view?.findViewWithTag<TextView>(tag) ?: return
        textView.setTextColor(ContextCompat.getColor(context!!, R.color.colorFontPrimaryLight))

    }

    override fun changeFontColorToBlack(tag: Task) {
        val textView = view?.findViewWithTag<TextView>(tag) ?: return
        textView.setTextColor(ContextCompat.getColor(context!!, R.color.colorFontPrimary))
    }


    interface RecyclerViewStateListener {

        fun onCheckBoxClick(tag: Task)

        fun onViewAttachedToWindow(isCompleted: Boolean, tag: Task)

        fun onTaskClick(task: Task)

    }

    companion object {

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
