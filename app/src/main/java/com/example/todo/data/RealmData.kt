package com.example.todo.data


import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort

class RealmData {

    fun getTask(taskId: Int) : Task?{
        Realm.getDefaultInstance().use { realm ->
            return realm.where(Task::class.java).equalTo(Task::id.name, taskId).findFirst()
        }
    }

    fun getAllTasks(): RealmResults<Task> {
        //val realm = Realm.getDefaultInstance()
        return RealmApplication.realm.where(Task::class.java).sort(Task::id.name, Sort.ASCENDING).findAll()
        /*
        Realm.getDefaultInstance().use { realm ->
            return realm.where(Task::class.java).sort(Task::id.name, Sort.ASCENDING).findAll()
        }
        */
    }

    fun saveTask(task: Task) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                task.id = createNewId(realm)
                realm.copyToRealm(task)
            }
        }
    }


    fun updateTask(task: Task) {
        Realm.getDefaultInstance().use { realm ->
            val item = realm.where(Task::class.java).equalTo(Task::id.name, task.id).findFirst()
            realm.executeTransaction {
                if (task.body.isNotEmpty()) {
                    item?.body = task.body
                }
                item?.isCompleted = task.isCompleted
            }
        }
    }

    fun deleteTask(taskId: Int) {
        Realm.getDefaultInstance().use { realm ->
            val item = realm.where(Task::class.java).equalTo(Task::id.name, taskId).findFirst()
            realm.executeTransaction {
                item?.deleteFromRealm()
                //updateAllId(realm)
            }
        }

    }

    private fun updateAllId(realm: Realm) {
        val list = realm.where(Task::class.java).sort(Task::id.name, Sort.ASCENDING).findAll()
        list.forEachIndexed { index, task ->
            task.id = index
        }

    }

    private fun createNewId(realm: Realm): Int {
        val id =  realm.where(Task::class.java).sort(Task::id.name, Sort.DESCENDING).findFirst()?.id ?: -1
        return id + 1   // start is 0
    }

}