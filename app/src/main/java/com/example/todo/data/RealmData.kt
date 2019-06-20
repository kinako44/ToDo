package com.example.todo.data

import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort

class RealmData {

    fun getAllTasks(): RealmResults<Task> {
        val realm = Realm.getDefaultInstance()
        val tasks = realm.where(Task::class.java).sort(Task::id.name).findAll()
        realm.close()
        return tasks
    }

    fun saveTask(task: Task) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            task.id = createNewId(realm)
            realm.copyToRealm(task)
        }
        realm.close()
    }


    fun updateTask(task: Task) {
        val realm = Realm.getDefaultInstance()
        val item = realm.where(Task::class.java).equalTo(Task::id.name, task.id).findFirst()
        realm.executeTransaction {
            if (task.body != "") {
                item?.body = task.body
            }
            item?.isCompleted = task.isCompleted
        }
        realm.close()
    }


    private fun createNewId(realm: Realm): Int {
        val id =  realm.where(Task::class.java).sort(Task::id.name, Sort.DESCENDING).findFirst()?.id ?: -1
        return id + 1   // start is 0
    }
}