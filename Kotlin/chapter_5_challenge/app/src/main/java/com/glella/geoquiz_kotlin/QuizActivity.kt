package com.glella.geoquiz_kotlin

import android.app.Activity
import android.content.Intent
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
    private var mIsCheater = false

    private val EXTRA_ANSWER_IS_TRUE = "com.glella.geoquiz.answer_is_true"
    private val EXTRA_ANSWER_IS_SHOWN = "com.glella.geoquiz.answer_shown"
    private val REQUEST_CODE_CHEAT = 0

    // Challenge - using an array to avoid solving it the same way as before altering the Question class
    // and perhaps not as smooth but easier.
    private var mCheatedArray = BooleanArray(mQuestionBank.size) // to save cheating state for each question
    private val KEY_CHEATER_ARRAY = "cheater_array" // to save cheating array

    private fun toast(messageResID: Int) {
        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show()
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIstrue = mQuestionBank[mCurrentIndex].mAnswerTrue

        var messageResId = 0

        if (mIsCheater) {
            messageResId = R.string.judgment_toast
        } else {
            messageResId = if (userPressedTrue == answerIstrue) R.string.correct_toast else R.string.incorrect_toast
        }
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

        // Challenge
        // No need to initialize array of booleand to false as each element gets set to false when defined

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0)

            // Challenge - Loading cheating state per question
            mCheatedArray = savedInstanceState.getBooleanArray(KEY_CHEATER_ARRAY)
            mIsCheater = mCheatedArray[mCurrentIndex]
        }

        true_button?.setOnClickListener { v -> checkAnswer(true) }
        false_button?.setOnClickListener { v -> checkAnswer(false) }

        next_button?.setOnClickListener{ v ->
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size

            // Challenge - Loading cheating status of next question
            mIsCheater = mCheatedArray[mCurrentIndex]

            updateQuestion()
        }

        cheat_button?.setOnClickListener {v ->
            val answerIstrue = mQuestionBank[mCurrentIndex].mAnswerTrue
            val intent = Intent(this, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIstrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
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

        // Challenge - saving cheating status of each question
        outState?.putBooleanArray(KEY_CHEATER_ARRAY, mCheatedArray)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == REQUEST_CODE_CHEAT) {
            val extras = data?.extras ?: return
            mIsCheater = extras.getBoolean(EXTRA_ANSWER_IS_SHOWN)

            // Challenge - Updating cheating status as we come back from CheatActivity
            mCheatedArray[mCurrentIndex] = mIsCheater
        }

    }
}
