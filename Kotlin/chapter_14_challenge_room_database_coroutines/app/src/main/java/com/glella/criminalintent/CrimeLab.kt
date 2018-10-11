package com.glella.criminalintent


import com.glella.criminalintent.database.AppDatabase
import com.glella.criminalintent.database.CrimesListDAO
import kotlinx.coroutines.experimental.*
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

    fun getCrimes(): ArrayList<Crime> = runBlocking(Dispatchers.Default) {
        //val result = async { retrieveCrimes() }.await()
        val result = async { crimesDAO.getAllCrimes() }.await()
        return@runBlocking result as ArrayList<Crime>
    }

    fun getCrime(id: UUID): Crime? = runBlocking(Dispatchers.Default) {
        val uuidString = id.toString()
        //var crime: Crime = crimesDAO.getOneCrime(uuidString)
        val result = async { crimesDAO.getOneCrime(uuidString) }.await()
        return@runBlocking result
    }

    fun addCrime(crime: Crime) = runBlocking(Dispatchers.Default) {
        val job = GlobalScope.launch { crimesDAO.insertCrime(crime) }
        //crimesDAO.insertCrime(crime)
        job.join()
        mCrimes.clear()
        mCrimes = getCrimes()
    }

    fun updateCrime(crime: Crime) = runBlocking(Dispatchers.Default) {
        val job = GlobalScope.launch { crimesDAO.updateCrime(crime) }
        job.join()
    }

    fun deleteCrime(crime: Crime): Boolean = runBlocking(Dispatchers.Default) {
        val job = GlobalScope.launch { crimesDAO.deleteCrime(crime) }
        mCrimes.clear()
        mCrimes = getCrimes()

        return@runBlocking true
    }


    /* Not needed - suspend functions
    suspend fun retrieveCrimes(): List<Crime> {
        return crimesDAO.getAllCrimes()
    }


    getCrimes() original - Main Thread
    return crimes
    return ArrayList()

    Coroutines with async and await (good for more than 1 simultaneous action)
    val result = async { retrieveCrimes() }.await()
    return@runBlocking result as ArrayList<Crime>

    3rd option - does not refresh list on ListFragment
    val result = withContext(Dispatchers.Default) { retrieveCrimes() }
    return@runBlocking result as ArrayList<Crime>
    */


}