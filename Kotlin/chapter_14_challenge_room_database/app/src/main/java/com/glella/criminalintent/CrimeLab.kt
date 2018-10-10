package com.glella.criminalintent


import com.glella.criminalintent.database.AppDatabase
import com.glella.criminalintent.database.CrimesListDAO
import java.util.*


object CrimeLab {
    var mCrimes = ArrayList<Crime>()
    var appDB: AppDatabase
    var crimesDAO: CrimesListDAO


    init {
        appDB = ApplicationContextProvider.database!!
        crimesDAO = appDB.crimesListDAO()

        mCrimes = getCrimes() as ArrayList<Crime>
    }

    fun getCrimes(): List<Crime> {

       
        //var crimes: ArrayList<Crime>

        var crimes = crimesDAO.getAllCrimes() as ArrayList<Crime>

        //AsyncTask.execute({
            //crimes = crimesDAO.getAllCrimes() as ArrayList<Crime>
            //runOnUiThread { listCategoryAdapter.notifyDataSetChanged() }
        //})

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