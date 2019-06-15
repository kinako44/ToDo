package com.example.todo.edit

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.R
import kotlinx.android.synthetic.main.activity_edit_to_do.*

class TaskEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_to_do)

        save_btn.setOnClickListener {
            val intent = Intent()

            val editText = TextInoutLayout.editText
            if (editText != null) {
                intent.putExtra(NEW_DATA_FROM_EDIT_KEY, editText.text.toString())
                todo_editText.setText("")
            }

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        const val NEW_DATA_FROM_EDIT_KEY = "NEW_DATA_FROM_EDIT_KEY"
    }


}
