package com.glella.criminalintent

import android.support.v4.app.Fragment

class CrimeListActivity: SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return CrimeListFragment()
    }
}