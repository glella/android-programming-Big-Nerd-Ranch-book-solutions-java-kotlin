package com.glella.criminalintent

import java.util.*
import kotlin.collections.ArrayList

object CrimeLab {
    var mCrimes = ArrayList<Crime>()

    init {
        for (i in 0..99) {
            val crime = Crime()
            crime.mTitle = "Crime #$i"
            crime.mSolved = (i % 2 == 0) // Every other one
            // Challenge set requires police 1 in every 5
            crime.mRequiresPolice = (i % 5 == 0)
            mCrimes.add(crime)
        }
    }

    fun getCrime(id: UUID): Crime? {
        for (item in mCrimes)
            if (item.mId.equals(id)) return item
        return null
    }

}