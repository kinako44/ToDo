package com.example.todo.edit

import com.example.todo.BasePresenter
import com.example.todo.BaseView

interface TaskEditContract {

    interface Presenter : BasePresenter {

    }

    interface View : BaseView<Presenter> {

    }
}