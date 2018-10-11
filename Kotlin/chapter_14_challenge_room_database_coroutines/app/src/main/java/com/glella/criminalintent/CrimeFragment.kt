package com.glella.criminalintent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import java.util.*

class CrimeFragment: Fragment() {

    lateinit var mCrime: Crime
    lateinit var mCrimeTitle: EditText
    lateinit var mSolvedCheckBox: CheckBox
    lateinit var mDateButton: Button

    companion object {
        private val ARG_CRIME_ID = "crime_id"
        private val DIALOG_DATE = "dialogDate"
        private val REQUEST_DATE = 0

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

        // Challenge
        setHasOptionsMenu(true)
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
        mDateButton.setText(mCrime.mDate.toString())
        mDateButton.setOnClickListener { _ ->
            val dialog = DatePickerFragment.newInstance(mCrime.mDate!!)
            dialog.setTargetFragment(this@CrimeFragment, REQUEST_DATE)
            dialog.show(fragmentManager, DIALOG_DATE)
        }


        mSolvedCheckBox = v.findViewById(R.id.crime_solved) as CheckBox
        mSolvedCheckBox.isChecked = mCrime.mSolved
        mSolvedCheckBox.setOnCheckedChangeListener { _, b -> mCrime.mSolved = b }

        return v
    }

    override fun onPause() {
        super.onPause()

        CrimeLab.updateCrime(mCrime)
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
    }

    private fun updateDate() {
        mDateButton.setText(mCrime.mDate.toString())
    }

    // Challenge
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.fragment_crime, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.delete_crime -> {
                activity!!.finish()
                return CrimeLab.deleteCrime(mCrime)
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }
}