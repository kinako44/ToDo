package com.example.todo.edit


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.example.todo.R
import com.example.todo.util.DatePickerFragment

class TaskEditFragment : Fragment(), TaskEditContract.View {

    override lateinit var presenter: TaskEditContract.Presenter
    private lateinit var saveButton: CardView
    private lateinit var taskInput: EditText
    private lateinit var deadlineButton: CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_task_edit, container, false)

        with(root) {
            taskInput = findViewById(R.id.task_input)
            saveButton = findViewById(R.id.task_input_complete)
            deadlineButton = findViewById(R.id.setting_deadline)
        }

        saveButton.setOnClickListener {
            presenter.saveTask(taskInput.text.toString())
            activity?.finish()
        }

        deadlineButton.setOnClickListener {
            showDatePicker()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroy()
    }

    override fun showDatePicker() {
        DatePickerFragment().show(fragmentManager, "DataPicker")
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            TaskEditFragment()
    }
}
