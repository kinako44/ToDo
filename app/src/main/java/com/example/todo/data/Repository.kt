package com.example.todo.data


class Repository (private val realmData: RealmData) {

    fun saveTask(task: Task) {
        if (task.id < 0) {
            realmData.saveTask(task)
            return
        }
        realmData.updateTask(task)
    }

    fun getAllTasks(): List<Task>{
        return realmData.getAllTasks()
    }


}