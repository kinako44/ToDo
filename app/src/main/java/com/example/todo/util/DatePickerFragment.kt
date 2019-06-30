package com.example.todo.util

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener{

    private var listener: OnDateSelectListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(context!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = transformDateFormat(year, month, dayOfMonth)
        listener?.onDateSelect(date)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun setOnDataSelectListener(onDateSelectListener: OnDateSelectListener) {
        listener = onDateSelectListener
    }

    private fun transformDateFormat(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val format = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)
        return format.format(calendar.time)
    }

    interface OnDateSelectListener {
        fun onDateSelect(date: String)
    }

}