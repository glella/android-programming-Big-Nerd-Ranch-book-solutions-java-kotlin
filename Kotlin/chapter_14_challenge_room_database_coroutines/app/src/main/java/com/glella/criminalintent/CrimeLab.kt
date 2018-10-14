package com.glella.criminalintent


import com.glella.criminalintent.database.AppDatabase
import com.glella.criminalintent.database.CrimesListDAO
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.util.*
import kotlin.collections.ArrayList

// Doing database operations in UI thread is BAD but doing it like this for simplicity in this test


object CrimeLab {
    var mCrimes = ArrayList<Crime>()
    var appDB: AppDatabase
    var crimesDAO: CrimesListDAO


    init {
        appDB = ApplicationContextProvider.database!!
        crimesDAO = appDB.crimesListDAO()
    }


    // *** Suspending Private DAO Functions ***

    suspend private fun retrieveCrimesSuspending(): List<Crime> {
        return crimesDAO.getAllCrimes()
    }

    suspend private fun retrieveCrimeSuspending(uuidString: String): Crime? {
        return crimesDAO.getOneCrime(uuidString)
    }

    suspend private fun addCrimeSuspending(crime: Crime) {
        crimesDAO.insertCrime(crime)
    }

    suspend private fun updateCrimeSuspending(crime: Crime) {
        crimesDAO.updateCrime(crime)
    }

    suspend private fun deleteCrimeSuspending(crime: Crime) {
        crimesDAO.deleteCrime(crime)
    }


    // *** Public DAO Functions ***

    fun getCrimes(): ArrayList<Crime> = runBlocking {
        val result = async(Dispatchers.Default) { retrieveCrimesSuspending() }.await()
        return@runBlocking result as ArrayList<Crime>
    }

    fun getCrime(id: UUID): Crime? = runBlocking {
        val uuidString = id.toString()
        val result = async(Dispatchers.Default) { retrieveCrimeSuspending(uuidString) }.await()
        return@runBlocking result
    }

    fun addCrime(crime: Crime) = runBlocking {
        val job = launch(Dispatchers.Default) { addCrimeSuspending(crime) }
        job.join()
        mCrimes.clear()
        mCrimes = getCrimes()
    }

    fun updateCrime(crime: Crime) = runBlocking {
        val job = launch(Dispatchers.Default) { updateCrimeSuspending(crime) }
        job.join()
    }

    fun deleteCrime(crime: Crime): Boolean = runBlocking {
        val job = launch(Dispatchers.Default) { deleteCrimeSuspending(crime) }
        job.join()
        mCrimes.clear()
        mCrimes = getCrimes()
        return@runBlocking true
    }

    
}