package com.glella.criminalintent


import android.os.AsyncTask
import com.glella.criminalintent.database.AppDatabase
import com.glella.criminalintent.database.CrimesListDAO
import java.util.*

// Doing database operations in UI thread is BAD but doing it like this for simplicity in this test


object CrimeLab {
    var mCrimes = ArrayList<Crime>()
    var appDB: AppDatabase
    var crimesDAO: CrimesListDAO


    init {
        appDB = ApplicationContextProvider.database!!
        crimesDAO = appDB.crimesListDAO()

        // leaving just this one async as a test
        AsyncTask.execute {
        mCrimes = getCrimes() as ArrayList<Crime>
        }
    }


    fun getCrimes(): List<Crime> {
        var crimes = crimesDAO.getAllCrimes() as ArrayList<Crime>
        return crimes
    }


    fun getCrime(id: UUID): Crime? {
        val uuidString = id.toString()
        var crime: Crime = crimesDAO.getOneCrime(uuidString)
        return crime
    }

    fun addCrime(crime: Crime) {
        crimesDAO.insertCrime(crime)
        mCrimes.clear()
        mCrimes = getCrimes() as ArrayList<Crime>
    }

    fun updateCrime(crime: Crime) {
        crimesDAO.updateCrime(crime)
    }

    // Challenge
    fun deleteCrime(crime: Crime): Boolean {
        crimesDAO.deleteCrime(crime)
        mCrimes.clear()
        mCrimes = getCrimes() as ArrayList<Crime>

        return true
    }

}