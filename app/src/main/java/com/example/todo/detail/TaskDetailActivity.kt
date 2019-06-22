package com.example.todo.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.todo.R
import com.example.todo.data.RealmData
import com.example.todo.data.Repository
import com.example.todo.list.TaskListFragment

import kotlinx.android.synthetic.main.activity_task_detail.*

class TaskDetailActivity : AppCompatActivity() {
    private val defaultTaskId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        val taskId = intent.getIntExtra(ARG_TASK_DETAIL_KEY, defaultTaskId)
        if (taskId == defaultTaskId) {
            finish()
        }

        val taskDetailFragment =
            supportFragmentManager.findFragmentById(R.id.detail_container) as TaskDetailFragment?
                ?: TaskDetailFragment.newInstance(taskId).also {
                    supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.detail_container, it)
                    .commit()
                }

        TaskDetailPresenter(taskId, taskDetailFragment, Repository(RealmData()))

    }

    companion object {
        const val ARG_TASK_DETAIL_KEY = "ARG_TASK_DETAIL_KEY"
        const val REQUEST_TASK_DELETE = 1
    }

}
