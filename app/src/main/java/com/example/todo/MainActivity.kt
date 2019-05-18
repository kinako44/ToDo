package com.example.todo

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_edit_to_do.*
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private val createNewTodoKey: Int = 1
    private val editEnteredTodoKey: Int = 2
    private val items = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        todo_list.adapter = adapter
        
        // 予定を入力textView用クリックリスナー
        plan_input.isClickable = true
        plan_input.setOnClickListener {
            val intent = Intent(this, EditToDO::class.java)
            startActivityForResult(intent, createNewTodoKey)
        }

        // 既存のTodoリストを選択したときのクリックリスナー
        todo_list.setOnItemClickListener { parent, view, position, id ->
            val textView: TextView = view.findViewById(android.R.id.text1)
            val intent = Intent(this, EditToDO::class.java)
            intent.putExtra("key2", textView.text.toString())
            startActivityForResult(intent, editEnteredTodoKey)

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK &&
            requestCode == createNewTodoKey &&
             data != null) {
            val todoData =  data.extras.getString("key1")
            items.add(todoData)
        }
    }


}
