package com.glella.criminalintent

import java.util.*
import kotlin.collections.LinkedHashMap

object CrimeLab {

    // Challenge 2 - Using a Linked Hashmap we will be able to fetch a single crime with its UUID
    // Using a Linked hashMap so we can preserve the order of the Crimes
    //var mCrimes = ArrayList<Crime>()
    var mCrimes = LinkedHashMap<UUID, Crime>()


    init {
        for (i in 0..99) {
            val crime = Crime()
            crime.mTitle = "Crime #$i"
            crime.mSolved = (i % 2 == 0) // Every other one
            // Challenge 2
            //mCrimes.add(crime)
            mCrimes.put(crime.mId, crime)
        }
    }

    // Challenge
    fun getCrimeList(): List<Crime> {
        return ArrayList(mCrimes.values)
    }

    fun getCrime(id: UUID): Crime? {
        // Challenge
        //for (item in mCrimes)
            //if (item.mId.equals(id)) return item
        //return null

        return mCrimes.get(id)
    }

}