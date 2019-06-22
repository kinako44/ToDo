package com.example.todo.edit

import com.example.todo.data.Repository
import com.example.todo.data.Task


class TaskEditPresenter(private val taskEditView: TaskEditContract.View,
                        private val repository: Repository)
    : TaskEditContract.Presenter {

    init {
        taskEditView.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveTask(task: Task) {
        if (task.body.isNotEmpty()) {
            repository.saveTask(task)
        }
    }


}

