package com.example.todo

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

class DatabaseController : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(config)
    }
}

open class ToDoRealm : RealmObject() {

    //@PrimaryKey

    @Required
    var task: String = ""
    var isChecked: Boolean = false
    var id: Int = 0

}