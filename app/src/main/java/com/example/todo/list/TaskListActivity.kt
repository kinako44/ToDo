package com.example.todo.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.R
import com.example.todo.data.RealmData
import com.example.todo.data.Repository


class TaskListActivity : AppCompatActivity(){

    private lateinit var taskListFragment: TaskListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskListFragment = supportFragmentManager.findFragmentById(R.id.container) as TaskListFragment? ?:
                            TaskListFragment().also {
                                supportFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.container, it)
                                    .commit()
                            }

        TaskListPresenter(taskListFragment, Repository(RealmData()))
    }
}
