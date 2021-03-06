package com.glella.criminalintent

import java.util.*

class Crime () {

    var mId: UUID
    var mTitle: String? = null
    var mDate: Date? = null
    var mSolved: Boolean = false
    var mSuspect: String? = null

    init {
        mId = UUID.randomUUID()
        mDate = Date()
    }
}