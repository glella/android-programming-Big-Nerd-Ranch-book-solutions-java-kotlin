package com.glella.criminalintent

import java.util.*

class Crime () {

    var mId: UUID
    var mTitle: String? = null
    var mDate: Date? = null
    var mSolved: Boolean = false

    init {
        mId = UUID.randomUUID()
        mDate = Date()
    }
}