package com.example.todo.data

import io.realm.RealmResults


class Repository (private val realmData: RealmData) {

    fun getTask(taskId: Int): Task?{
        return realmData.getTask(taskId)
    }

    fun saveTask(task: Task) {
        if (task.id < 0) {
            realmData.createTask(task)
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

    fun onDestroy() {
        realmData.closeRealm()
    }

}