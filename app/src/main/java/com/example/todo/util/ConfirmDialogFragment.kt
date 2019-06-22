package com.example.todo.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.example.todo.R
import com.example.todo.detail.TaskDetailFragment

class ConfirmDialogFragment : DialogFragment(){
    var listener: DialogInterface.OnClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity).also {
            it.setMessage(R.string.delete_dialog)
            it.setPositiveButton(R.string.delete, listener)
            it.setNegativeButton(R.string.cancel, null)
        }
        return builder.create()
    }

}