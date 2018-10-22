package com.glella.criminalintent

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.Fragment
import android.support.v4.app.ShareCompat
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
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
    lateinit var mReportButton: Button
    lateinit var mSuspectButton: Button
    // Challenge
    lateinit var mCallSuspectButton: Button


    companion object {
        private val ARG_CRIME_ID = "crime_id"
        private val DIALOG_DATE = "dialogDate"
        private val REQUEST_DATE = 0
        private val REQUEST_CONTACT = 1
        // Challenge
        private val CONTACTS_PERMISSIONS = arrayOf(Manifest.permission.READ_CONTACTS)
        private val REQUEST_CONTACTS_PERMISSIONS = 3

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
        mDateButton.setText(mCrime.mDate.toString())
        mDateButton.setOnClickListener { _ ->
            val dialog = DatePickerFragment.newInstance(mCrime.mDate!!)
            dialog.setTargetFragment(this@CrimeFragment, REQUEST_DATE)
            dialog.show(fragmentManager, DIALOG_DATE)
        }


        mSolvedCheckBox = v.findViewById(R.id.crime_solved) as CheckBox
        mSolvedCheckBox.isChecked = mCrime.mSolved
        mSolvedCheckBox.setOnCheckedChangeListener { _, b -> mCrime.mSolved = b }

        val packageManager = activity!!.packageManager
        mReportButton = v.findViewById<View>(R.id.crime_report) as Button
        mReportButton.setOnClickListener {
            // Challenge
            /*
            var i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_TEXT, getCrimeReport())
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject))
            i = Intent.createChooser(i, getString(R.string.send_report))
            startActivity(i)
            */
            val mimeType = "text/plain"
            val title = getString(R.string.send_report)
            val i = ShareCompat.IntentBuilder.from(activity!!)
                    .setChooserTitle(title)
                    .setType(mimeType)
                    .setText(getCrimeReport())
                    .createChooserIntent()

            if (i.resolveActivity(packageManager) != null) {
                startActivity(i)
            }
        }

        // Challenge
        val callContact = Intent(Intent.ACTION_DIAL)
        mCallSuspectButton = v.findViewById<View>(R.id.call_suspect) as Button
        mCallSuspectButton.setOnClickListener {
            val numberString = "tel:" + mCrime.mSuspectPhone
            val number = Uri.parse(numberString)
            callContact.data = number
            startActivity(callContact)
        }

        val pickContact = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        mSuspectButton = v.findViewById<View>(R.id.crime_suspect) as Button
        mSuspectButton.setOnClickListener {
            mCallSuspectButton.isEnabled = true
            startActivityForResult(pickContact, REQUEST_CONTACT)
        }

        if (mCrime.mSuspect != null) {
            mSuspectButton.setText(mCrime.mSuspect)
            mCallSuspectButton.isEnabled = true
        } else {
            mCallSuspectButton.isEnabled = false
        }

        if (packageManager.resolveActivity(pickContact,
                        PackageManager.MATCH_DEFAULT_ONLY) == null) {
            mSuspectButton.isEnabled = false
        }


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
        } else if (requestCode == REQUEST_CONTACT && data != null) {
            val contactUri = data.data
            // Specify which fields you want your query to return values for
            val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
            // Perform your query - the contactUri is like a "where" clause here
            val c = activity!!.contentResolver
                    .query(contactUri!!, queryFields, null, null, null)
            try {
                // Double-check that you actually got results
                if (c!!.count == 0) {
                    return
                }
                // Pull out the first column of the first row of data - that is your suspect's name.
                c.moveToFirst()
                val suspect = c.getString(0)
                mCrime.mSuspect = suspect
                mSuspectButton.text = suspect
            } finally {
                c!!.close()
            }
        }
    }

    // Challenge
    private fun getSuspectName(data: Intent): String? {
        val contactUri = data.data
        // Specify which fields you want your query to return values for
        val queryFields = arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME)
        // Perform your query - the contactUri is like a "where" clause here.
        val c = activity!!.contentResolver
                .query(contactUri!!, queryFields, null, null, null)
        try {
            // Double-check that you actually got results.
            if (c!!.count == 0) {
                return null
            }
            // Pull out the first column of the first row of data - that is your suspect's name.
            c.moveToFirst()

            mCrime.mSuspectID = c.getString(0)
            return c.getString(1)

        } finally {
            c!!.close()
        }
    }

    // Challenge
    private fun getSuspectPhoneNumber(contactId: String): String? {
        var suspectPhoneNumber: String? = null
        // The content URI of the CommonDataKinds.Phone
        val phoneContactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // The columns to return for each row
        val queryFields = arrayOf(ContactsContract.Data.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER, // which is the default phone number.
                ContactsContract.CommonDataKinds.Phone.TYPE)
        // Selection criteria
        val mSelectionClause = ContactsContract.Data.CONTACT_ID + " = ?"
        val mSelectionArgs = arrayOf("")
        mSelectionArgs[0] = contactId
        // Do a query against the table and return a Cursor object
        val c = activity!!.contentResolver
                .query(phoneContactUri, queryFields, mSelectionClause, mSelectionArgs, null)
        try {
            // Double-check that you actually got results.
            if (c!!.count == 0) {
                return null
            }
            while (c.moveToNext()) {
                val phoneType = c.getInt(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))
                if (phoneType == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) {
                    suspectPhoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA))
                    break
                }
            }
        } finally {
            c!!.close()
        }
        return suspectPhoneNumber
    }

    // Challenge
    private fun hasContactPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(activity!!, CONTACTS_PERMISSIONS[0])
        return result == PackageManager.PERMISSION_GRANTED
    }

    // Challenge
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CONTACTS_PERMISSIONS -> if (hasContactPermission()) {
                updateSuspectPhone()
            }
        }
    }

    // Challenge
    private fun updateSuspectPhone() {
        val suspectPhoneNumber = getSuspectPhoneNumber(mCrime.mSuspectID!!)
        mCrime.mSuspectPhone = suspectPhoneNumber
    }

    private fun updateDate() {
        mDateButton.setText(mCrime.mDate.toString())
    }

    private fun getCrimeReport(): String {
        var solvedString: String? = null
        if (mCrime.mSolved) {
            solvedString = getString(R.string.crime_report_solved)
        } else {
            solvedString = getString(R.string.crime_report_unsolved)
        }

        val dateFormat = "EEE, MMM dd"
        val dateString = DateFormat.format(dateFormat, mCrime.mDate).toString()

        var suspect = mCrime.mSuspect
        if (suspect == null) {
            suspect = getString(R.string.crime_report_no_suspect)
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect)
        }

        return getString(R.string.crime_report,
                mCrime.mTitle, dateString, solvedString, suspect)
    }
}