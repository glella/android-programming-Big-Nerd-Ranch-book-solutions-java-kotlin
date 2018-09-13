package com.glella.geoquiz_kotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_cheat.*

class CheatActivity : AppCompatActivity() {

    private val EXTRA_ANSWER_IS_TRUE = "com.glella.geoquiz.answer_is_true"
    private val EXTRA_ANSWER_IS_SHOWN = "com.glella.geoquiz.answer_shown"
    private var mAnswerIsTrue = false

    // Challenge - key to save state if user rotates device destroying activity
    private val KEY_HAS_CHEATED = "has_cheated"
    private var hasCheated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        // Challenge - Load variable cheating and get it ready for return to QuizActivity
        if (savedInstanceState != null) {
            hasCheated = savedInstanceState.getBoolean(KEY_HAS_CHEATED, false)
            setAnswerShownResult(hasCheated)
        }

        mAnswerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        show_answer_button?.setOnClickListener {v ->
            // Challenge - Updated variable
            hasCheated = true

            val textToDisplay = if (mAnswerIsTrue) R.string.true_button else R.string.false_button
            answer_text_view?.setText(textToDisplay)

            setAnswerShownResult(hasCheated)
        }

    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent()
        data.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown)
        setResult(RESULT_OK, data)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        // Challenge save state of cheating
        outState?.putBoolean(KEY_HAS_CHEATED, hasCheated)
    }
}
