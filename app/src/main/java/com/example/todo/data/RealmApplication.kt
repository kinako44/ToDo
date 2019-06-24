package com.example.todo.data

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        realm = Realm.getDefaultInstance()

        // warning! Realmのモデルを更新した際、データベースが初期化される
        // val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build()
        // Realm.setDefaultConfiguration(config)

    }

    override fun onTerminate() {
        super.onTerminate()
        realm.close()
    }

    companion object {
        lateinit var realm: Realm
    }

}