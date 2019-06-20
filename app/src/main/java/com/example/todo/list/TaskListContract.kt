package com.example.todo.list

import com.example.todo.BasePresenter
import com.example.todo.BaseView
import com.example.todo.data.Task

interface TaskListContract {
    interface Presenter: BasePresenter {

        fun addTask()

        fun showTaskDetail()

        fun switchTaskFontColorToGray(tag: Task)

        fun switchTaskFontColorToBlack(tag: Task)

        fun updateTask(task: Task)

        fun getTask(): List<Task>

    }

    interface View: BaseView<Presenter> {

        fun showAddTask()

        fun changeFontColorToGray(tag: Task)

        fun changeFontColorToBlack(tag: Task)

    }
}