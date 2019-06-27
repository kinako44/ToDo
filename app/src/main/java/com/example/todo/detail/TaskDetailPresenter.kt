package com.example.todo.detail

import com.example.todo.data.Repository
import com.example.todo.data.Task

class TaskDetailPresenter(
        private val taskId: Int,
        private val taskDetailView: TaskDetailContract.View,
        private val repository: Repository)
    : TaskDetailContract.Presenter{

    init {
        taskDetailView.presenter = this
    }

    override fun start() {
        taskDetailView.showTask()
        taskDetailView.showCompleteState()
    }

    override fun getTask(): Task? = repository.getTask(taskId)

    override fun deleteTask() {
        repository.deleteTask(taskId)
        taskDetailView.showTaskListUi()
    }

    override fun updateTask(description: String, state: Boolean) {
        repository.getTask(taskId) ?: return

        Task().also {
            it.body = description
            it.isCompleted = state
            it.id = taskId
            repository.saveTask(it)
        }
    }

    override fun onDeleteMenuClick() {
        taskDetailView.showDeleteDialog()
    }

    override fun destroy() {
        repository.onDestroy()
    }


}