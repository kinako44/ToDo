package com.example.todo.edit


class TaskEditPresenter(private val taskEditView: TaskEditContract.View) : TaskEditContract.Presenter {

    init {
        taskEditView.presenter = this
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}

