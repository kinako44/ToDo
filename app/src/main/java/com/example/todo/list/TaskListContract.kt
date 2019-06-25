package com.example.todo.list

import com.example.todo.BasePresenter
import com.example.todo.BaseView
import com.example.todo.data.Task
import io.realm.RealmResults

interface TaskListContract {
    interface Presenter: BasePresenter {

        fun addTask()

        fun showTaskDetail(taskId: Int)

        fun switchTaskFontColorToGray(tag: Task)

        fun switchTaskFontColorToBlack(tag: Task)

        fun updateTask(taskId: Int, isCompleted: Boolean)

        fun getTasks(): RealmResults<Task>

    }

    interface View: BaseView<Presenter> {

        fun showAddTaskUi()

        fun showTaskDetailUi(taskId: Int)

        fun changeFontColorToGray(tag: Task)

        fun changeFontColorToBlack(tag: Task)

    }
}