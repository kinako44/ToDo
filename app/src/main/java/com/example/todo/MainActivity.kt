package com.example.todo

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.TextPaint
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit_to_do.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    private val createNewTodoKey: Int = 1
    private val items = mutableListOf<String>()
    private lateinit var realm: Realm
    private lateinit var adapter: ItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()

        // RecyclerViewの初期化
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = ItemRecyclerViewAdapter(ArrayList(), this)
        recycler.adapter = adapter

        // floatingActionButtonのクリックリスナー実装
        fab.setOnClickListener {
            val intent = Intent(this, EditToDO::class.java)
            startActivityForResult(intent, createNewTodoKey)
        }

    }


    override fun onResume() {
        super.onResume()
        val todoLists = realm.where(ToDoRealm::class.java).findAll()

        items.clear()       // 修正必要箇所
        todoLists.forEach {
            //adapter.addItem(it.plan)
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        val list = realm.where(ToDoRealm::class.java).findAll()
        realm.executeTransaction {
            list.deleteAllFromRealm()
        }

        realm.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK &&
            requestCode == createNewTodoKey &&
            data != null) {
            val todoData = data.extras.getString("key1")
            items.add(todoData)

            adapter.addItem(todoData)


            // realmへの保存
            realm.executeTransaction { realm ->
                val obj = realm.createObject(ToDoRealm::class.java)
                obj.plan = todoData
            }
        }
    }


}
