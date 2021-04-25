package com.jasson.tourAppMobile.ui.helpers

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.util.*

class SelectDateFragment(checkInDate: EditText) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {
    val checkInDate = checkInDate
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val yy = calendar[Calendar.YEAR]
        val mm = calendar[Calendar.MONTH]
        val dd = calendar[Calendar.DAY_OF_MONTH]
        return DatePickerDialog(requireActivity(), this, yy, mm, dd)
    }

    override fun onDateSet(view: DatePicker, yy: Int, mm: Int, dd: Int) {
        populateSetDate(yy, mm + 1, dd)
    }

    fun populateSetDate(year: Int, month: Int, day: Int) {
        checkInDate.setText("$month/$day/$year")
    }
}