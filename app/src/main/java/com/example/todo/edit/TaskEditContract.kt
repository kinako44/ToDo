package com.example.todo.edit

import com.example.todo.BasePresenter
import com.example.todo.BaseView
import com.example.todo.data.Task

interface TaskEditContract {

    interface Presenter : BasePresenter {
        fun saveTask(description: String)

        fun datePick()

        fun destroy()
    }

    interface View : BaseView<Presenter> {
        fun showDatePicker()

    }
}