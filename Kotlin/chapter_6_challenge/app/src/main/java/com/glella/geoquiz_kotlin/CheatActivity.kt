package com.glella.geoquiz_kotlin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_cheat.*

class CheatActivity : AppCompatActivity() {

    private val EXTRA_ANSWER_IS_TRUE = "com.glella.geoquiz.answer_is_true"
    private val EXTRA_ANSWER_IS_SHOWN = "com.glella.geoquiz.answer_shown"
    private var mAnswerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        mAnswerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        show_answer_button?.setOnClickListener {v ->
            val textToDisplay = if (mAnswerIsTrue) R.string.true_button else R.string.false_button
            answer_text_view?.setText(textToDisplay)

            setAnswerShownResult(true)
        }

        // Challenge
        val versionAPI = "API Level " + Build.VERSION.SDK_INT.toString() // SDK deprecated
        show_api_text_view?.setText(versionAPI)

    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent()
        data.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown)
        setResult(RESULT_OK, data)
    }
}
