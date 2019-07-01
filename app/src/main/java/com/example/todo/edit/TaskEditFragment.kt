package com.example.todo.edit


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import com.example.todo.R
import com.example.todo.util.DatePickerFragment

class TaskEditFragment : Fragment(), TaskEditContract.View {

    override lateinit var presenter: TaskEditContract.Presenter
    private lateinit var saveButton: CardView
    private lateinit var deadlineButton: CardView
    private lateinit var taskInput: EditText
    private lateinit var deadline: TextView
    private lateinit var cancelButton: ImageView

    private var deadlineDate: String? = null

    private val dateSelectListener = object : DatePickerFragment.OnDateSelectListener{
        override fun onDateSelect(date: String) {
            deadlineDate = date
            presenter.setDeadline()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_task_edit, container, false)

        with(root) {
            taskInput = findViewById(R.id.task_input)
            saveButton = findViewById(R.id.task_input_complete)
            deadlineButton = findViewById(R.id.setting_deadline)
            deadline = findViewById(R.id.text_deadline)
            cancelButton = findViewById(R.id.cancel_btn)
        }

        savedInstanceState?.let {
            deadlineDate = savedInstanceState.getString(SAVE_DEADLINE_KEY)
            presenter.setDeadline()
        }

        saveButton.setOnClickListener {
            presenter.saveTask(taskInput.text.toString(), deadlineDate)
            activity?.finish()
        }

        deadlineButton.setOnClickListener {
            showDatePicker()
        }

        cancelButton.setOnClickListener {
            presenter.removeDeadline()
            deadlineDate = null
        }

        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        deadlineDate?.let {
            outState.putString(SAVE_DEADLINE_KEY, deadlineDate)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroy()
        deadlineDate = null
    }

    override fun showDatePicker() {
        DatePickerFragment().also {
            it.show(fragmentManager, "DataPicker")
            it.setOnDateSelectListener(dateSelectListener)
        }
    }

    override fun showDeadlineCancel() {
        deadlineDate?.let {
            cancelButton.visibility = View.VISIBLE
        }
    }

    override fun showDeadline() {
        deadlineDate?.let {
            deadline.text = deadlineDate
        }
    }

    override fun hideDeadlineCancel() {
        cancelButton.visibility = View.GONE
    }

    override fun hideDeadline() {
        deadline.text = getString(R.string.deadline_setting)
    }


    companion object {

        const val SAVE_DEADLINE_KEY = "SAVE_DEADLINE_KEY"

        @JvmStatic
        fun newInstance() =
            TaskEditFragment()
    }
}
