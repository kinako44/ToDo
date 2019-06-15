package com.example.todo.list


import android.util.Log
import com.example.todo.data.Task

class TaskListPresenter(private val taskListView: TaskListContract.View) : TaskListContract.Presenter {

    init {
        taskListView.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTask() {
        taskListView.showAddTask()
    }

    override fun showTaskDetail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun switchTaskFontColor(isCompleted: Boolean, task: Task) {
        when (isCompleted) {
            true -> taskListView.changeFontColorToGray(task)
            false -> taskListView.changeFontColorToBlack(task)
        }
    }



}