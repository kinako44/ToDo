package com.example.todo.list

import android.app.Activity
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
import com.example.todo.edit.TaskEditActivity
import com.example.todo.R
import com.example.todo.data.Task
import com.example.todo.detail.TaskDetailActivity
import com.example.todo.detail.TaskDetailFragment
import kotlinx.android.synthetic.main.fragment_tasklist_list.*


class TaskListFragment : Fragment(), TaskListContract.View {

    private var columnCount = 1
    private var listener: RecyclerViewStateListener? = null
    override lateinit var presenter: TaskListContract.Presenter


    private val recyclerViewStateListener: RecyclerViewStateListener = object: RecyclerViewStateListener {

        override fun onCheckBoxClick(tag: Task) {
            val isTaskCompleted = !tag.isCompleted
            when (isTaskCompleted) {
                true -> presenter.switchTaskFontColorToGray(tag)
                false -> presenter.switchTaskFontColorToBlack(tag)
            }

            val task = Task()
            with(task) {
                isCompleted = isTaskCompleted
                id = tag.id
            }
            presenter.updateTask(task)
        }

        override fun onTaskClick(task: Task) {
            presenter.showTaskDetail(task.id)
        }

        override fun onViewAttachedToWindow(isCompleted: Boolean, tag: Task) {
            when (isCompleted) {
                true -> presenter.switchTaskFontColorToGray(tag)
                false -> presenter.switchTaskFontColorToBlack(tag)
            }
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

                val itemList = presenter.getTask()
                adapter = TaskListAdapter(itemList, recyclerViewStateListener)
            }
        }

        activity?.findViewById<FloatingActionButton>(R.id.fab_add_task)?.setOnClickListener {
            presenter.addTask()
        }

        return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val recyclerView = view?.findViewById<RecyclerView>(R.id.list) ?: return
        recyclerView.adapter?.notifyDataSetChanged()
        /*
        val position = data?.getIntExtra(TaskDetailFragment.TASK_DETAIL_ID, -1) ?: return   // position = taskId

        if (requestCode == TaskDetailActivity.REQUEST_TASK_DELETE &&
                    resultCode == Activity.RESULT_OK) {
            recyclerView.adapter?.notifyItemRemoved(position)
        }

        if (resultCode == Activity.RESULT_CANCELED) {
            recyclerView.adapter?.notifyItemChanged(position)
        }
        */
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun showAddTaskUi() {
        val intent = Intent(context, TaskEditActivity::class.java)
        startActivity(intent)
    }

    override fun showTaskDetailUi(taskId: Int) {
        val intent = Intent(context, TaskDetailActivity::class.java)
        intent.putExtra(TaskDetailActivity.ARG_TASK_DETAIL_KEY, taskId)
        startActivityForResult(intent, TaskDetailActivity.REQUEST_TASK_DELETE)
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
