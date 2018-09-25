package com.glella.criminalintent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import com.glella.criminalintent.CrimeLab.mCrimes
import java.util.*

class CrimePagerActivity: AppCompatActivity() {

    companion object {
        private val EXTRA_CRIME_ID = "com.gella.crime_id"

        fun newIntent(packageContext: Context, crimeID: UUID): Intent {
            val intent = Intent(packageContext, CrimePagerActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeID)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)

        val crimeID = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID

        val mViewPager = findViewById<View>(R.id.crime_view_pager) as ViewPager

        // Challenge
        val mFirstButton = findViewById<View>(R.id.first_button) as ImageButton
        val mLastButton = findViewById<View>(R.id.last_button) as ImageButton

        mFirstButton.setOnClickListener { v ->
            mViewPager.currentItem = 0 // First Item
        }

        mLastButton.setOnClickListener { v ->
            mViewPager.currentItem = mCrimes.size - 1 // Last Item
        }



        mViewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                val crime = mCrimes.get(position)
                return CrimeFragment.newInstance(crime.mId)
            }

            override fun getCount(): Int {
                return mCrimes.size
            }
        }

        for (i in mCrimes.indices) {
            if (mCrimes[i].mId.equals(crimeID)) {
                mViewPager.currentItem = i
                break
            }
        }


    }
}