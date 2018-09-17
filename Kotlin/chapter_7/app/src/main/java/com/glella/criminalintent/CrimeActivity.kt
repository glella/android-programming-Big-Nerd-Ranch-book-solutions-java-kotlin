package com.glella.criminalintent

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class CrimeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime)

        var fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            fragment = CrimeFragment()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit()
        }

            }
}
