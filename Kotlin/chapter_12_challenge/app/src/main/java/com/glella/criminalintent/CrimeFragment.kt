package com.glella.criminalintent

import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import java.util.*

class CrimeFragment: Fragment() {

    lateinit var mCrime: Crime
    lateinit var mCrimeTitle: EditText
    lateinit var mSolvedCheckBox: CheckBox
    lateinit var mDateButton: Button

    // Challenge
    lateinit var mTimeButton: Button

    companion object {
        private val ARG_CRIME_ID = "crime_id"
        private val DIALOG_DATE = "dialogDate"
        private val REQUEST_DATE = 0

        // Challenge
        private val DIALOG_TIME = "dialogTime"
        private val REQUEST_TIME = 1

        fun newInstance(crimeID: UUID): CrimeFragment {
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeID)

            val fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mCrime = Crime()
        //val crimeID = activity!!.intent.getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID) as UUID
        val crimeID = arguments!!.getSerializable(ARG_CRIME_ID) as UUID
        mCrime = CrimeLab.getCrime(crimeID)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_crime, container, false)

        mCrimeTitle = v.findViewById<EditText>(R.id.crime_title) as EditText
        mCrimeTitle.setText(mCrime.mTitle)
        mCrimeTitle.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                // Do nothing
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mCrime.mTitle = p0?.toString()
            }
        })

        //val texttodisplay = mCrime.mDate.toString()
        //crime_date?.setText(mCrime.mDate.toString())
        //crime_date?.isEnabled = false

        mDateButton = v.findViewById<View>(R.id.crime_date) as Button
        updateDate()
        mDateButton.setOnClickListener { _ ->
            val dialog = DatePickerFragment.newInstance(mCrime.mDate!!)
            dialog.setTargetFragment(this@CrimeFragment, REQUEST_DATE)
            dialog.show(fragmentManager, DIALOG_DATE)
        }

        mTimeButton = v.findViewById(R.id.crime_time)
        updateTime()
        mTimeButton.setOnClickListener { _ ->
            val dialog2 = TimePickerFragment.newInstance(mCrime.mDate!!)
            dialog2.setTargetFragment(this@CrimeFragment, REQUEST_TIME)
            dialog2.show(fragmentManager, DIALOG_TIME)
        }

        mSolvedCheckBox = v.findViewById(R.id.crime_solved) as CheckBox
        mSolvedCheckBox.isChecked = mCrime.mSolved
        mSolvedCheckBox.setOnCheckedChangeListener { _, b -> mCrime.mSolved = b }

        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_DATE) {
            val date = data!!.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date
            mCrime.mDate = date
            updateDate()
        }

        // Challenge
        if (requestCode == REQUEST_TIME) {
            val date = data!!.getSerializableExtra(TimePickerFragment.EXTRA_TIME) as Date
            mCrime.mDate = date
            updateTime()
        }
    }

    private fun updateDate() {
        // Challenge - Implement date in 1 button and Time in the other
        //mDateButton.setText(mCrime.mDate.toString()) // Old line
        val pattern = "EEE, MMM d, ''yy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val dateString = simpleDateFormat.format(mCrime.mDate)
        mDateButton.text = dateString
    }

    private fun updateTime() {
        // Challenge
        val pattern = "h:mm a"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val timeString = simpleDateFormat.format(mCrime.mDate)
        mTimeButton.text = timeString
    }


}