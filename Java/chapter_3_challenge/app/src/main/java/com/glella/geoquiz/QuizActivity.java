package com.glella.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;



    // Challenge: added 3rd attribute to class Question keeping track of which question was answered already
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true, false),
            new Question(R.string.question_oceans, true, false),
            new Question(R.string.question_mideast, false, false),
            new Question(R.string.question_africa, false, false),
            new Question(R.string.question_americas, true, false),
            new Question(R.string.question_asia, true, false),
    };

    // Challenge - Counters to keep track of number of questions answered and how many correct
    private int answeredQuestions = 0;
    private int correctAnswers = 0;
    // Array to save the state of which questions were answered the size of mQuestion Bank
    private boolean[] whichAnswered = new boolean[mQuestionBank.length];

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    // KEYS to save state if user rotates device and the Activity is recreated
    private static final String KEY_ANSWERED_QUESTIONS = "number_answered";
    private static final String KEY_CORRECT_ANSWERS = "number_correct";
    private static final String KEY_WHICH_QUESTIONS_ANSWERED = "which_answered";

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState !=null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            // Loading state for the Challenge variables - Keys defined above
            answeredQuestions = savedInstanceState.getInt(KEY_ANSWERED_QUESTIONS);
            correctAnswers = savedInstanceState.getInt(KEY_CORRECT_ANSWERS);
            whichAnswered = savedInstanceState.getBooleanArray(KEY_WHICH_QUESTIONS_ANSWERED);
            for (int i = 0; i < mQuestionBank.length; i++) {
                mQuestionBank[i].setAlreadyAnswered(whichAnswered[i]);
            }
        }


        mQuestionTextView = (TextView) findViewById(R.id.question_textView);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });


        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        updateQuestion();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);

        // Saving state for the Challenge variables - Keys defined above
        outState.putInt(KEY_ANSWERED_QUESTIONS, answeredQuestions);
        outState.putInt(KEY_CORRECT_ANSWERS, correctAnswers);
        for (int i = 0; i < mQuestionBank.length; i++) {
            whichAnswered[i] = mQuestionBank[i].isAlreadyAnswered();
        }
        outState.putBooleanArray(KEY_WHICH_QUESTIONS_ANSWERED, whichAnswered);

    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        // Challenge - disable or enable answer buttons depending if the Question was already answered
        if (mQuestionBank[mCurrentIndex].isAlreadyAnswered() == false) {
            buttonsEnabled(true);
        } else {
            buttonsEnabled(false);
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        // Challenge - Mark question as answered and disable buttons and count answered questions
        mQuestionBank[mCurrentIndex].setAlreadyAnswered(true);
        buttonsEnabled(false);
        answeredQuestions++;

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            correctAnswers++; // count correct answers
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        calculateScore();
    }

    // Challenge utility method for enabling or disabling buttons
    private void buttonsEnabled(boolean enabled) {
        mTrueButton.setEnabled(enabled);
        mFalseButton.setEnabled(enabled);
    }

    // Challenge method to calculate score
    private void calculateScore() {
        int totalQuestions = mQuestionBank.length;
        int score = correctAnswers * 100 / totalQuestions;

        // Challenge - Only shows score if we answered all the questions, and then rests all
        if (answeredQuestions == totalQuestions) {
            String message = "You scored " + score + "% correct answers. Resetting all";
            Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            // reset
            correctAnswers = 0;
            answeredQuestions = 0;
            for (Question question : mQuestionBank) {
                question.setAlreadyAnswered(false);
            }

        }
    }

}
