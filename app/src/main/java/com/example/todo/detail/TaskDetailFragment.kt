package com.example.todo.detail

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.*
import android.widget.CheckBox
import android.widget.EditText
import com.example.todo.R
import com.example.todo.data.Task
import com.example.todo.util.ConfirmDialogFragment

/**
 * A placeholder fragment containing a simple view.
 */
class TaskDetailFragment : Fragment(), TaskDetailContract.View {

    private var taskId = -1
    override lateinit var presenter: TaskDetailContract.Presenter
    private lateinit var description: EditText
    private lateinit var check: CheckBox

    private val dialogClickListener = DialogInterface.OnClickListener { _, _ ->
        presenter.deleteTask()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        arguments?.let {
            taskId = it.getInt(TASK_DETAIL_ID)
        }

        val root = inflater.inflate(R.layout.fragment_task_detail, container, false)
        setHasOptionsMenu(true)

        with(root) {
            description = findViewById(R.id.task_body)
            check = findViewById(R.id.check_completion)
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        val description = description.text.toString()
        val state = check.isChecked
        presenter.updateTask(description, state)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_task_detail, menu)

        menu?.let {
            it.findItem(R.id.delete).isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId) {
            R.id.delete -> presenter.onDeleteMenuClick()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showTask() {
        val task = presenter.getTask()
        description.setText(task!!.body)
    }

    override fun showCompleteState() {
        val task = presenter.getTask()
        check.isChecked = task!!.isCompleted
    }

    override fun showTaskListUi() {
        activity?.finish()
    }

    override fun showDeleteDialog() {
        ConfirmDialogFragment().also {
            it.listener = dialogClickListener
            it.show(fragmentManager, "dialog")
        }
    }

    companion object {

        const val TASK_DETAIL_ID = "TASK_DETAIL_ID"
        fun newInstance(taskId: Int) =
            TaskDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(TASK_DETAIL_ID, taskId)
                }
            }
    }
}
