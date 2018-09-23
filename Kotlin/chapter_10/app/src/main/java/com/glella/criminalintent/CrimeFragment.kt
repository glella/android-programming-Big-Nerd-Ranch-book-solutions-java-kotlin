package com.glella.criminalintent

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

    companion object {
        private val ARG_CRIME_ID = "crime_id"

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

        val mDateButton = v.findViewById<View>(R.id.crime_date) as Button
        mDateButton.setText(mCrime.mDate.toString())
        mDateButton.isEnabled = false


        mSolvedCheckBox = v.findViewById(R.id.crime_solved) as CheckBox
        mSolvedCheckBox.isChecked = mCrime.mSolved
        mSolvedCheckBox.setOnCheckedChangeListener { _, b -> mCrime.mSolved = b }

        return v
    }
}