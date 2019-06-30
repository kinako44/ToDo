package com.example.todo.edit

import com.example.todo.data.Repository
import com.example.todo.data.Task


class TaskEditPresenter(
    private val taskEditView: TaskEditContract.View,
    private val repository: Repository)
    : TaskEditContract.Presenter {

    init {
        taskEditView.presenter = this
    }

    override fun start() {
    }

    override fun saveTask(description: String) {
        if (description.isNotEmpty()) {
            Task().also {
                it.body = description
                it.isCompleted = false
                repository.saveTask(it)
            }

        }
    }

    override fun datePick() {
        taskEditView.showDatePicker()
    }

    override fun destroy() {
        repository.onDestroy()
    }

    override fun setDeadline() {
        taskEditView.showDeadline()
        taskEditView.showDeadlineCancel()
    }

    override fun removeDeadline() {
        taskEditView.hideDeadline()
        taskEditView.hideDeadlineCancel()
    }

}

