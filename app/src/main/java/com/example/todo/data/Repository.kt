package com.example.todo.data

import com.example.todo.list.TaskListAdapter
import io.realm.RealmResults


class Repository (private val realmData: RealmData) {

    fun getTask(taskId: Int): Task?{
        return realmData.getTask(taskId)
    }

    fun saveTask(task: Task) {
        if (task.id < 0) {
            realmData.saveTask(task)
            return
        }
        realmData.updateTask(task)
    }

    fun getAllTasks(): RealmResults<Task>{
        return realmData.getAllTasks()
    }

    fun deleteTask(taskId: Int) {
        realmData.deleteTask(taskId)
    }


}