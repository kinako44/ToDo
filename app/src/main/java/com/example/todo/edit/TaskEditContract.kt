package com.example.todo.edit

import com.example.todo.BasePresenter
import com.example.todo.BaseView

interface TaskEditContract {

    interface Presenter : BasePresenter {
        fun saveTask(description: String)

        fun datePick()

        fun destroy()

        fun setDeadline()

        fun removeDeadline()
    }

    interface View : BaseView<Presenter> {
        fun showDatePicker()

        fun showDeadlineCancel()

        fun showDeadline()

        fun hideDeadlineCancel()

        fun hideDeadline()

    }
}