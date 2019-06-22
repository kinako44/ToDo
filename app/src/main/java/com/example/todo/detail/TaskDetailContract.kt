package com.example.todo.detail

import com.example.todo.BasePresenter
import com.example.todo.BaseView
import com.example.todo.data.Task

interface TaskDetailContract{

    interface Presenter : BasePresenter {

        fun deleteTask()

        fun getTask(): Task?

        fun updateTask(description: String, state: Boolean)

        fun onDeleteMenuClick()

    }

    interface View : BaseView<Presenter> {

        fun showTask()

        fun showCompleteState()

        fun showTaskListUi()

        fun showDeleteDialog()

    }
}