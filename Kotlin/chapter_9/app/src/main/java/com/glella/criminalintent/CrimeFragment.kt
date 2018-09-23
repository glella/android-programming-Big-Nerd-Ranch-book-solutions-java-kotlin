package com.glella.criminalintent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_crime.*

class CrimeFragment: Fragment() {

    lateinit var mCrime: Crime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCrime = Crime()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_crime, container, false)

        crime_title?.addTextChangedListener(object: TextWatcher {
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


        crime_solved?.setOnCheckedChangeListener { _, b -> mCrime.mSolved = b }

        return v
    }
}