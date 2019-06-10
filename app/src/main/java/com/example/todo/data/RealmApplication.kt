package com.example.todo.data

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        // !warning! Realmのモデルを更新した際、データベースが初期化される
        // val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        // Realm.setDefaultConfiguration(config)
    }
}