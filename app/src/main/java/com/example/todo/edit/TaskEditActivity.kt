package com.example.todo.edit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.R
import com.example.todo.data.RealmData
import com.example.todo.data.Repository
import kotlinx.android.synthetic.main.activity_edit_to_do.*

class TaskEditActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_to_do)

        val taskEditFragment = supportFragmentManager.findFragmentById(R.id.task_add_container) as TaskEditFragment? ?:
        TaskEditFragment().also {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.task_add_container, it)
                .commit()
        }

        TaskEditPresenter(taskEditFragment, Repository(RealmData()))
    }
}
