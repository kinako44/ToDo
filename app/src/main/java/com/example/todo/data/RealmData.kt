package com.example.todo.data


import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort

class RealmData{

    private val realm: Realm = Realm.getDefaultInstance()

    fun closeRealm() {
        realm.close()
    }

    fun getTask(taskId: Int) : Task? =
        realm.where(Task::class.java).equalTo(Task::id.name, taskId).findFirst()


    fun getAllTasks(): RealmResults<Task> =
        realm.where(Task::class.java).sort(Task::id.name, Sort.ASCENDING).findAll()


    fun createTask(task: Task) {
        realm.executeTransaction {
            task.id = createNewId()
            realm.copyToRealm(task)
        }
    }

    fun updateTask(task: Task) {
        val item = realm.where(Task::class.java).equalTo(Task::id.name, task.id).findFirst()
        realm.executeTransaction {
            if (task.body.isNotEmpty()) {
                item?.body = task.body
            }
            item?.isCompleted = task.isCompleted
        }
    }

    fun deleteTask(taskId: Int) {
        val item = realm.where(Task::class.java).equalTo(Task::id.name, taskId).findFirst()
        realm.executeTransaction {
            item?.deleteFromRealm()
        }

    }

    fun deleteAllTasks() {
        realm.executeTransaction {
            realm.where(Task::class.java).findAll().deleteAllFromRealm()
        }
    }

    private fun createNewId(): Int {
        val id =  realm.where(Task::class.java).sort(Task::id.name, Sort.DESCENDING).findFirst()?.id ?: -1
        return id + 1   // start is 0
    }

}