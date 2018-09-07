package com.glella.geoquiz_kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        true_button?.setOnClickListener { v -> toast(getString(R.string.correct_toast)) }
        false_button?.setOnClickListener { v -> toast(getString(R.string.incorrect_toast)) }


    }
}
