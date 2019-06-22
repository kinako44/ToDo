package com.example.todo.data

import com.example.todo.list.TaskListAdapter
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort

class RealmData {

    fun getTask(taskId: Int) : Task?{
        val realm = Realm.getDefaultInstance()
        val task = realm.where(Task::class.java).equalTo(Task::id.name, taskId).findFirst()
        realm.close()
        return task
    }

    fun getAllTasks(): RealmResults<Task> {
        val realm = Realm.getDefaultInstance()
        val tasks = realm.where(Task::class.java).sort(Task::id.name, Sort.ASCENDING).findAll()
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
            if (task.body.isNotEmpty()) {
                item?.body = task.body
            }
            item?.isCompleted = task.isCompleted
        }
        realm.close()
    }

    fun deleteTask(taskId: Int) {
        val realm = Realm.getDefaultInstance()
        val item = realm.where(Task::class.java).equalTo(Task::id.name, taskId).findFirst()
        realm.executeTransaction {
            item?.deleteFromRealm()
        }
        updateAllId(realm)
        realm.close()
    }

    private fun updateAllId(realm: Realm) {
        val list = realm.where(Task::class.java).sort(Task::id.name, Sort.ASCENDING).findAll()
        realm.executeTransaction {
            // 方法についてはとりあえず保留
            list.forEachIndexed { index, task ->
                task.id = index
            }
        }
    }


    private fun createNewId(realm: Realm): Int {
        val id =  realm.where(Task::class.java).sort(Task::id.name, Sort.DESCENDING).findFirst()?.id ?: -1
        return id + 1   // start is 0
    }

}