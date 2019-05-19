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
import kotlinx.android.synthetic.main.item.view.*
import java.util.ArrayList

class ItemRecyclerViewAdapter(
    private val mItems: ArrayList<String>,
    private val mContext: Context
) : RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int = mItems.size

    fun addItem(item: String) {
        mItems.add(item)
        notifyDataSetChanged()
    }

    private fun removeItem(position: Int) {
        mItems.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.todoBody.text = mItems[position]

        // CheckBox用クリックリスナーの実装
        holder.check.setOnClickListener {
            val paint: TextPaint = holder.todoBody.paint

            if (holder.check.isChecked) {
                holder.todoBody.setTextColor(Color.LTGRAY)
                paint.flags = holder.todoBody.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                paint.isAntiAlias = true

            } else {
                holder.todoBody.setTextColor(Color.DKGRAY)
                paint.flags = 0
            }
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val todoBody: TextView = view.body_todo
        val check: CheckBox = view.checkBox

    }
}