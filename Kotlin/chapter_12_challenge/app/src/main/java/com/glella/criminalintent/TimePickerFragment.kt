package com.glella.criminalintent

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.TimePicker
import java.util.*

// Challenge - This class and dialog_time layout plus modification of fragment_crime layout to have 2 buttons
// one for the date and another for the time

class TimePickerFragment : DialogFragment() {

    companion object {

        private val ARG_TIME = "time"

        val EXTRA_TIME = "com.glella.criminalintent.time"

        fun newInstance(date: Date): TimePickerFragment {
            val args = Bundle()
            args.putSerializable(ARG_TIME, date)

            val fragment = TimePickerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var mTimePicker: TimePicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val date = arguments!!.getSerializable(ARG_TIME) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date

        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)

        val v = LayoutInflater.from(activity).inflate(R.layout.dialog_time, null)

        mTimePicker = v.findViewById<View>(R.id.dialog_time_picker) as TimePicker
        mTimePicker.hour = hours
        mTimePicker.minute = minutes

        return AlertDialog.Builder(activity!!)
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok) { dialogInterface, which ->
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    val hours = mTimePicker.hour
                    val minutes = mTimePicker.minute
                    val date = GregorianCalendar(year, month, day, hours, minutes).time

                    sendResult(Activity.RESULT_OK, date)
                }
                .create()

    }

    private fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null) {
            return
        }

        val intent = Intent()
        intent.putExtra(EXTRA_TIME, date)

        targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
    }

}