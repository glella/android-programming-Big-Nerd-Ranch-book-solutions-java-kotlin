package com.glella.criminalintent

import java.util.*

class Crime () {

    val mId: UUID
    var mTitle: String? = null
    var mDate: Date? = null
    var mSolved: Boolean = false
    // Challenge
    var mRequiresPolice: Boolean = false

    init {
        mId = UUID.randomUUID()
        mDate = Date()
    }
}