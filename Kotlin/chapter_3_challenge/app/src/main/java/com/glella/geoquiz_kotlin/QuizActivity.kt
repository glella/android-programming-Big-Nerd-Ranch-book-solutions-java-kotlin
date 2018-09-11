package com.glella.geoquiz_kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    // Challenge: added 3rd attribute to class Question keeping track of which question was answered already
    private var mQuestionBank = arrayListOf<Question>(
            Question(R.string.question_australia, true, false),
            Question(R.string.question_oceans, true, false),
            Question(R.string.question_mideast, false, false),
            Question(R.string.question_africa, false, false),
            Question(R.string.question_americas, true, false),
            Question(R.string.question_asia, true, false)
    )

    private var mCurrentIndex = 0
    private val TAG = "QuizActivity"
    private val KEY_INDEX = "index"

    // Challenge - Counters to keep track of number of questions answered and how many correct
    private var answeredQuestions = 0
    private var correctAnswers = 0
    // Array to save the state of which questions were answered the size of mQuestion Bank
    private var whichAnswered = BooleanArray(mQuestionBank.size)

    // KEYS to save state if user rotates device and the Activity is recreated
    private val KEY_ANSWERED_QUESTIONS = "number_answered"
    private val KEY_CORRECT_ANSWERS = "number_correct"
    private val KEY_WHICH_QUESTIONS_ANSWERED = "which_answered"


    private fun toast(messageResID: Int) {
        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show()
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIstrue = mQuestionBank[mCurrentIndex].mAnswerTrue

        // Challenge - Mark question as answered and disable buttons and count answered questions
        mQuestionBank[mCurrentIndex].alreadyAnswered = true
        buttonsEnabled(false)
        answeredQuestions++

        var messageResId = 0

        if (userPressedTrue == answerIstrue) {
            messageResId = R.string.correct_toast
            correctAnswers++
        } else {
            messageResId = R.string.incorrect_toast
        }

        toast(messageResId)
        calculateScore()
    }

    private fun updateQuestion() {
        val question = mQuestionBank[mCurrentIndex].mTextResId
        question_textView.setText(question)

        // Challenge - disable or enable answer buttons depending if the Question was already answered
        if (mQuestionBank[mCurrentIndex].alreadyAnswered == false) {
            buttonsEnabled(true)
        } else {
            buttonsEnabled(false)
        }
    }

    // Challenge utility method for enabling or disabling buttons
    private fun buttonsEnabled(enabled: Boolean) {
        true_button.setEnabled(enabled)
        false_button.setEnabled(enabled)
    }

    // Challenge method to calculate score
    private fun calculateScore() {
        val totalQuestions = mQuestionBank.size
        val score = correctAnswers * 100 / totalQuestions

        // Challenge - Only shows score if we answered all the questions, and then rests all
        if (answeredQuestions == totalQuestions) {
            val message = "You scored $score% correct answers. Resetting all"
            val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
            // reset
            correctAnswers = 0
            answeredQuestions = 0
            for (question in mQuestionBank) {
                question.alreadyAnswered = false
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle) called")
        setContentView(R.layout.activity_quiz)

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0)

            // Loading state for the Challenge variables - Keys defined above
            answeredQuestions = savedInstanceState.getInt(KEY_ANSWERED_QUESTIONS)
            correctAnswers = savedInstanceState.getInt(KEY_CORRECT_ANSWERS)
            whichAnswered = savedInstanceState.getBooleanArray(KEY_WHICH_QUESTIONS_ANSWERED)
            for (i in mQuestionBank.indices) {
                mQuestionBank[i].alreadyAnswered = whichAnswered[i]
            }


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

        // Saving state for the Challenge variables - Keys defined above
        outState?.putInt(KEY_ANSWERED_QUESTIONS, answeredQuestions)
        outState?.putInt(KEY_CORRECT_ANSWERS, correctAnswers)
        for (i in mQuestionBank.indices) {
            whichAnswered[i] = mQuestionBank[i].alreadyAnswered
        }
        outState?.putBooleanArray(KEY_WHICH_QUESTIONS_ANSWERED, whichAnswered)
    }
}
