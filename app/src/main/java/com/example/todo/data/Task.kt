package com.example.todo.data

import io.realm.RealmObject
import io.realm.annotations.Required

open class Task : RealmObject() {

    //@PrimaryKey

    @Required
    var body: String = ""
    var isCompleted: Boolean = false
    var id: Int = -1         // start is 0
}