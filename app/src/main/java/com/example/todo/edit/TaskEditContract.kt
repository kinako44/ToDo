package com.example.todo.edit

import com.example.todo.BasePresenter
import com.example.todo.BaseView
import com.example.todo.data.Task

interface TaskEditContract {

    interface Presenter : BasePresenter {
        fun saveTask(task: Task)

    }

    interface View : BaseView<Presenter> {

    }
}