package com.example.todo.list


import com.example.todo.data.Repository
import com.example.todo.data.Task

class TaskListPresenter(private val taskListView: TaskListContract.View,
                        private val repository: Repository
    ) : TaskListContract.Presenter {

    init {
        taskListView.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun updateTask(task: Task) {
        repository.saveTask(task)
    }

    override fun getTask(): List<Task> {
        return repository.getAllTasks()
    }



}