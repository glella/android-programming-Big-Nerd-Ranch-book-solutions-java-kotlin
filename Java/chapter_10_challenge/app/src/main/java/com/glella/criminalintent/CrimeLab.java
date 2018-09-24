package com.glella.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    // Challenge 2 - Using a Linked Hashmap we will be able to fetch a single crime with its UUID
    // Using a Linked HashMap so we can preserve the order of the Crimes
    //private List<Crime> mCrimes;
    private Map<UUID, Crime> mCrimes;


    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        // Challenge
        //mCrimes = new ArrayList<Crime>();
        mCrimes = new LinkedHashMap<>();

        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0); // Every other one
            // Challenge
            //mCrimes.add(crime);
            mCrimes.put(crime.getId(), crime);
        }
    }

    public List<Crime> getCrimes() {
        // Challenge
        //return mCrimes;
        return new ArrayList<>(mCrimes.values());
    }

    public Crime getCrime(UUID id) {
        // Challenge
        //for (Crime crime : mCrimes) {
            //if (crime.getId().equals(id)) {
                //return crime;
            //}
        //}
        //return null;

        return mCrimes.get(id);
    }

}
