package com.example.todo

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_edit_to_do.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainToEdit: Int = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, receiveIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, receiveIntent)

        //if (resultCode == Activity.RESULT_OK && requestCode == RESULT_SUBACTIVITY)
        if (resultCode == Activity.RESULT_OK &&
            requestCode == mainToEdit &&
            receiveIntent != null) {
            val resTodo = receiveIntent.extras.getString("key")
            main_textview_1.text = resTodo
        }
    }

    // onClick
    fun transEditToDo(view: View?) {
        var intent = Intent(this, EditToDO::class.java)
        intent.putExtra(EXTRA_MESSAGE, main_textview_1.text.toString())
        startActivityForResult(intent, mainToEdit)
    }

}
