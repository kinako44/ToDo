package com.example.todo.list

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.todo.edit.TaskEditActivity
import com.example.todo.R
import com.example.todo.data.RealmData
import com.example.todo.data.Repository
import com.example.todo.data.Task
import io.realm.Realm
import io.realm.Sort

class TaskListActivity : AppCompatActivity(){

    private lateinit var taskListFragment: TaskListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            realm.where(Task::class.java).findAll().deleteAllFromRealm()
        }
        realm.close()
        */

        taskListFragment = supportFragmentManager.findFragmentById(R.id.container) as TaskListFragment? ?:
                            TaskListFragment().also {
                                supportFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.container, it)
                                    .commit()
                            }

        TaskListPresenter(taskListFragment, Repository(RealmData()))


        /*


        // ドラッグ&ドロップとスワイプの処理
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN
            , ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                adapter.insertItem(fromPosition, toPosition)
                recycler.adapter?.notifyItemMoved(fromPosition, toPosition)

                moveItemInRealm(fromPosition + 1, toPosition + 1)

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
                val itemView = viewHolder.itemView
                val isLeftDirection = dX < 0
                val deleteIcon = getDrawable(R.drawable.ic_delete_white_24dp)

                background.color = Color.parseColor("#f44336")

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

                // 元に戻す処理のため一時保存
                val tmpItem = adapter.getItem(position)
                val tmpIsChecked = adapter.getIsChecked(position)

                adapter.removeItem(position)
                recycler.adapter?.notifyItemRemoved(position)

                // Snackbarの実装
                var cancel = false
                Snackbar
                    .make(context_view, R.string.item_removed_message, Snackbar.LENGTH_LONG)
                    .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        // Realmの更新はSnackbarが消えたあと行う
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            if (!cancel) {
                                removeItemFromRealm(position + 1)
                            }
                        }
                    })
                    .setAction(R.string.item_undo_message, View.OnClickListener {
                        adapter.insertNewItem(tmpItem, tmpIsChecked, position)
                        cancel = true
                    })
                    .show()
            }

        })
        itemTouchHelper.attachToRecyclerView(recycler)
        */

    }

}
