package com.glella.criminalintent

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import java.util.*

class CrimeActivity : SingleFragmentActivity() {

    companion object {
        val EXTRA_CRIME_ID = "com.gella.crime_id"

        fun newIntent(packageContext: Context, crimeID: UUID): Intent {
            val intent = Intent(packageContext, CrimeActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeID)
            return intent
        }
    }


    override fun createFragment(): Fragment {
        //return CrimeFragment()
        val crimeID: UUID = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID
        return CrimeFragment.newInstance(crimeID)
    }
}
