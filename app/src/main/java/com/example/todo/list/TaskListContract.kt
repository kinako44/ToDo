package com.example.todo.list

import com.example.todo.BasePresenter
import com.example.todo.BaseView
import com.example.todo.data.Task

interface TaskListContract {
    interface Presenter: BasePresenter {

        fun addTask()

        fun showTaskDetail()

        fun switchTaskFontColor(isCompleted: Boolean, task: Task)

    }

    interface View: BaseView<Presenter> {

        fun showAddTask()

        fun changeFontColorToGray(tag: Task)

        fun changeFontColorToBlack(tag: Task)



    }
}