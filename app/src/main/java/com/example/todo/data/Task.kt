package com.example.todo.data

import io.realm.RealmObject
import io.realm.annotations.Required

open class Task : RealmObject() {

    //@PrimaryKey

    @Required
    var task: String = ""
    var isCompleted: Boolean = false
    var id: Int = 0         // start is 0
}