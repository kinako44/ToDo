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
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.TextPaint
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import io.realm.Realm
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_edit_to_do.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private val createNewTodoKey: Int = 1
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
        adapter = ItemRecyclerViewAdapter(ArrayList(), ArrayList(), this)
        recycler.adapter = adapter

        // ドラッグ&ドロップとスワイプの処理
        // activity内に実装するのはいまいちか？
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN
            , ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                adapter.exchangeItem(fromPosition, toPosition)
                recycler.adapter?.notifyItemMoved(fromPosition, toPosition)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewHolder.let {
                    adapter.removeItem(viewHolder.adapterPosition)
                    recycler.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                }
            }

        })
        itemTouchHelper.attachToRecyclerView(recycler)

        // floatingActionButtonのクリックリスナー実装
        fab.setOnClickListener {
            val intent = Intent(this, EditToDO::class.java)
            startActivityForResult(intent, createNewTodoKey)
        }

    }

    override fun onPause() {
        super.onPause()
        refreshRealmDB()    // Pauseの度にDBを作り直すのは負荷が大きい？
    }


    override fun onResume() {
        super.onResume()
        val todoLists = realm.where(ToDoRealm::class.java).findAll()
        adapter.clearAllItems()
        todoLists.forEach {
            adapter.addItem(it.plan, it.isChecked)
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        realm.close()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK &&
            requestCode == createNewTodoKey &&
            data != null) {

            val todoData = data.extras.getString("key1").toString()
            if (todoData == "") return

            adapter.addItem(todoData, false)

            // realmへの保存
            realm.executeTransaction { realm ->
                val newRealmObj = realm.createObject(ToDoRealm::class.java)
                newRealmObj.plan = todoData
                newRealmObj.isChecked = false
            }
        }
    }

    // Realmデータベースを新しく作り直す
    private fun refreshRealmDB() {
        val allList = realm.where(ToDoRealm::class.java).findAll()
        realm.executeTransaction {
            allList.deleteAllFromRealm()
            adapter.mItems.forEachIndexed { index, it ->
                val newRealmObj = realm.createObject(ToDoRealm::class.java)
                newRealmObj.plan = it
                newRealmObj.isChecked = adapter.mIsChecked[index]
            }
        }
    }


}
