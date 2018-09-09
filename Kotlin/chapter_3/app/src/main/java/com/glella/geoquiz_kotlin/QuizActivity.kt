package com.glella.geoquiz_kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    private var mQuestionBank = arrayListOf<Question>(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true)
    )

    private var mCurrentIndex = 0
    private val TAG = "QuizActivity"
    private val KEY_INDEX = "index"

    private fun toast(messageResID: Int) {
        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show()
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIstrue = mQuestionBank[mCurrentIndex].mAnswerTrue
        val messageResId = if (userPressedTrue == answerIstrue) R.string.correct_toast else R.string.incorrect_toast
        toast(messageResId)
    }

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].mTextResId
        question_textView.setText(question)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle) called")
        setContentView(R.layout.activity_quiz)

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0)
        }

        true_button?.setOnClickListener { v -> checkAnswer(true) }
        false_button?.setOnClickListener { v -> checkAnswer(false) }

        next_button?.setOnClickListener{ v ->
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }

        updateQuestion()

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSavedInstanceState")
        outState?.putInt(KEY_INDEX, mCurrentIndex)
    }
}
