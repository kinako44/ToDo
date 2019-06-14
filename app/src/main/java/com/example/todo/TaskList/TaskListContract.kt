package com.example.todo.TaskList

import android.widget.TextView
import com.example.todo.BasePresenter
import com.example.todo.BaseView

interface TaskListContract {
    interface Presenter: BasePresenter {

        fun addTask()

        fun showTaskDetail()

        // TODO viewに関する情報はなるべく隠蔽するべき
        fun switchTaskFontColor(isCompleted: Boolean, textView: TextView)

    }

    interface View: BaseView<Presenter> {

        fun showAddTask()

        fun changeFontColorToGray(textView: TextView)

        fun changeFontColorToBlack(textView: TextView)



    }
}