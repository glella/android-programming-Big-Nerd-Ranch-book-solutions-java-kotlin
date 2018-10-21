package com.glella.criminalintent

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import java.util.*

class DatePickerFragment : DialogFragment() {

    companion object {

        private val ARG_DATE = "date"
        val EXTRA_DATE = "com.glella.criminalintent.date"

        fun newInstance(date: Date): DatePickerFragment {
            val args = Bundle()
            args.putSerializable(ARG_DATE, date)

            val fragment = DatePickerFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val date = arguments!!.getSerializable(ARG_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val v = LayoutInflater.from(activity).inflate(R.layout.dialog_date, null)

        var mDatePicker = v.findViewById<View>(R.id.dialog_date_picker) as DatePicker
        mDatePicker.init(year, month, day, null)

        return AlertDialog.Builder(activity!!)
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok) { dialogInterface, which ->
                    val year = mDatePicker.year
                    val month = mDatePicker.month
                    val day = mDatePicker.dayOfMonth
                    val date = GregorianCalendar(year, month, day).time
                    sendResult(Activity.RESULT_OK, date)
                }
                .create()
    }

    private fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null) {
            return
        }

        val intent = Intent()
        intent.putExtra(EXTRA_DATE, date)

        targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
    }
}