package com.example.todo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_edit_to_do.*

class EditToDO : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_to_do)
        /*
        val receiveIntent = intent

        if (receiveIntent.extras?.getString("key2") != null) {
            val enteredTodo = receiveIntent.extras.getString("key2")
            todo_editText.setText(enteredTodo)
        }
        */


        save_btn.setOnClickListener {
            val sendIntent = Intent()

            if (todo_editText.text != null) {
                sendIntent.putExtra("key1", todo_editText.text.toString())
                todo_editText.setText("")
            }

            setResult(Activity.RESULT_OK, sendIntent)
            finish()
        }
    }


}
