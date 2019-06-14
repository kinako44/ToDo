package com.example.todo.TaskList


import android.widget.TextView

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


    override fun switchTaskFontColor(isCompleted: Boolean, textView: TextView) {
        when (isCompleted) {
            true -> taskListView.changeFontColorToGray(textView)
            false -> taskListView.changeFontColorToBlack(textView)
        }
    }



}