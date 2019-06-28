package com.example.todo.list


import com.example.todo.data.Repository
import com.example.todo.data.Task
import io.realm.RealmResults

class TaskListPresenter(
    private val taskListView: TaskListContract.View,
    private val repository: Repository
    ) : TaskListContract.Presenter {


    init {
        taskListView.presenter = this
    }

    override fun start() {
    }

    override fun destroy() {
        repository.onDestroy()
    }

    override fun addTask() {
        taskListView.showAddTaskUi()
    }

    override fun showTaskDetail(taskId: Int) {
        taskListView.showTaskDetailUi(taskId)
    }

    override fun switchTaskFontColorToGray(tag: Task) {
        taskListView.changeFontColorToGray(tag)
    }

    override fun switchTaskFontColorToBlack(tag: Task) {
        taskListView.changeFontColorToBlack(tag)
    }

    override fun updateTask(taskId: Int, isCompleted: Boolean) {
        Task().also {
            it.id = taskId
            it.isCompleted = isCompleted
            repository.saveTask(it)
        }
    }

    override fun getTasks(): RealmResults<Task> {
        return repository.getAllTasks()
    }



}