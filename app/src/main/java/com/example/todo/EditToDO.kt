package com.example.todo

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_edit_to_do.*

class EditToDO : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_to_do)
    }

    fun transMainActivity(view: View?) {
        val intent = Intent()
        if (todo_editText.text != null) {
            val message: String = todo_editText.text.toString()
            intent.putExtra("key", message)

            todo_editText.setText("")
            //Log.d("fun", message)
        }


        setResult(RESULT_OK, intent)
        finish()
    }

}
