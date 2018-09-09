package com.glella.geoquiz_kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
        setContentView(R.layout.activity_quiz)

        true_button?.setOnClickListener { v -> checkAnswer(true) }
        false_button?.setOnClickListener { v -> checkAnswer(false) }

        next_button?.setOnClickListener{ v ->
            mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size
            updateQuestion()
        }

        updateQuestion()

    }
}
