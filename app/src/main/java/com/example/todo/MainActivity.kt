package com.example.todo

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import io.realm.Realm
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
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN
            , ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                adapter.insertItem(fromPosition, toPosition)
                recycler.adapter?.notifyItemMoved(fromPosition, toPosition)

                return true
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {


                val background = ColorDrawable()
                background.color = Color.parseColor("#f44336")
                val itemView = viewHolder.itemView
                val isLeftDirection = dX < 0
                val deleteIcon = getDrawable(R.drawable.ic_delete_white_24dp)


                // 背景の描写
                if (isLeftDirection) {
                    background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                } else {
                    background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
                }
                background.draw(c)


                // 削除アイコンの表示
                if (deleteIcon != null) {
                    val itemHeight = itemView.bottom - itemView.top
                    val iconWidth = deleteIcon.intrinsicWidth
                    val iconHeight = deleteIcon.intrinsicHeight

                    // 左上が座標の原点
                    val iconTop = itemView.top + (itemHeight - iconHeight) / 2
                    val iconBottom = iconTop + iconHeight
                    if (isLeftDirection) {
                        val iconRight = itemView.right - (itemHeight - iconHeight) / 2
                        val iconLeft = iconRight - iconWidth
                        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        deleteIcon.draw(c)

                    } else {
                        val iconLeft = itemView.left + (itemHeight - iconHeight) / 2
                        val iconRight = iconLeft + iconWidth
                        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                        deleteIcon.draw(c)
                    }


                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                viewHolder.let {
                    // 元に戻す処理のため一時保存
                    val tmpItem = adapter.getItem(position)
                    val tmpIsChecked = adapter.getIsChecked(position)

                    adapter.removeItem(position)
                    recycler.adapter?.notifyItemRemoved(position)

                    Snackbar.make(context_view, R.string.item_removed_message, Snackbar.LENGTH_LONG)
                        .setAction("元に戻す", View.OnClickListener {
                            adapter.insertNewItem(tmpItem, tmpIsChecked, position)
                        }).show()
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
        refreshRealmDB()    // insert, deleteしたときにDBを更新する　←　修正要
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

            val todoData = data.extras?.getString("key1").toString()
            if (todoData == "") return      // 入力がブランクのときは何もしない

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
    // insertとdeleteした上で順番を保持できるように改良必要
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
