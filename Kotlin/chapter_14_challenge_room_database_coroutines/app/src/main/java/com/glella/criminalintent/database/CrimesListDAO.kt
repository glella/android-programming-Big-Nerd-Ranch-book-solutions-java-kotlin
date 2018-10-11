package com.glella.criminalintent.database

import android.arch.persistence.room.*
import com.glella.criminalintent.Crime

@Dao
interface CrimesListDAO {

    @Query("SELECT * FROM crimes_table")
    fun getAllCrimes(): List<Crime>

    @Query("SELECT * FROM crimes_table WHERE uuid = :uuidString LIMIT 1")
    fun getOneCrime(uuidString: String): Crime

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrime(crime: Crime)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCrime(crime: Crime)

    @Delete
    fun deleteCrime(crime: Crime)
}



