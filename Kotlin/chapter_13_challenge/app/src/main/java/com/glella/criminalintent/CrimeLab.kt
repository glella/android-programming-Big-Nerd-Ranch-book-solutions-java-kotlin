package com.glella.criminalintent

import java.util.*
import kotlin.collections.ArrayList

object CrimeLab {
    var mCrimes = ArrayList<Crime>()

    init {
        //
    }

    fun getCrime(id: UUID): Crime? {
        for (item in mCrimes)
            if (item.mId.equals(id)) return item
        return null
    }

    fun addCrime(c : Crime) {
        mCrimes.add(c)
    }

    // Challenge
    fun deleteCrime(id: UUID): Boolean {
        for (crime in mCrimes) {
            if (crime.mId.equals(id)) {
                mCrimes.remove(crime)
                return true
            }
        }
        return false
    }

}