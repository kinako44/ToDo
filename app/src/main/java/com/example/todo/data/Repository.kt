package com.example.todo.data


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

    fun getAllTasks(): List<Task>{
        return realmData.getAllTasks()
    }

    fun deleteTask(taskId: Int) {
        realmData.deleteTask(taskId)
    }


}