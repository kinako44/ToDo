package com.example.todo

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.example.todo.data.Task
import io.realm.Realm
import kotlinx.android.synthetic.main.item.view.*
import java.util.ArrayList

class ItemRecyclerViewAdapter(
    val mItems: ArrayList<String>,
    val mIsChecked: ArrayList<Boolean>,
    private val mContext: Context
) : RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int = mItems.size

    fun getItem(position: Int) = mItems[position]

    fun getIsChecked(position: Int) = mIsChecked[position]

    fun addItem(item: String, check: Boolean) {
        mItems.add(item)
        mIsChecked.add(check)
        notifyDataSetChanged()
    }

    fun insertNewItem(item:String, check: Boolean, position: Int) {
        mItems.add(position, item)
        mIsChecked.add(position, check)
        notifyDataSetChanged()
    }

    fun insertItem(fromPosition: Int, toPosition: Int) {
        val tmpItem = mItems.removeAt(fromPosition)
        mItems.add(toPosition, tmpItem)

        val tmpIsChecked = mIsChecked.removeAt(fromPosition)
        mIsChecked.add(toPosition, tmpIsChecked)

        //notifyItemMoved(position1, position2)
    }

    fun clearAllItems() {
        mItems.clear()
        mIsChecked.clear()
    }

    fun removeItem(position: Int) {
        mItems.removeAt(position)
        mIsChecked.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.todoBody.text = mItems[position]
        holder.check.isChecked = mIsChecked[position]

        fun switchTextViewLook() {
            val paint: TextPaint = holder.todoBody.paint

            if (holder.check.isChecked) {
                holder.todoBody.setTextColor(Color.LTGRAY)
                paint.flags = holder.todoBody.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                paint.isAntiAlias = true

            } else {
                holder.todoBody.setTextColor(Color.DKGRAY)
                paint.flags = 0
                paint.isAntiAlias = false
            }
        }
        // RecycleViewを再生成したときにチェック済みのものの見た目を変更する
        switchTextViewLook()

        // CheckBox用クリックリスナーの実装
        holder.check.setOnClickListener {
            mIsChecked[position] = !mIsChecked[position]

            val realm = Realm.getDefaultInstance()
            realm.executeTransaction {
                val item = realm.where(Task::class.java).equalTo("id", (position + 1)).findFirst()
                item!!.isCompleted = !item.isCompleted
            }
            switchTextViewLook()
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val todoBody: TextView = view.body_todo
        val check: CheckBox = view.check_completion

    }
}