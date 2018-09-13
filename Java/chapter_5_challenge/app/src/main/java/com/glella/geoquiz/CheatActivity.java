package com.glella.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.glella.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_IS_SHOWN = "com.glella.geoquiz.answer_shown";
    private boolean mAnswerIsTrue;

    private TextView mAnswertextView;
    private Button mShowAnswerButton;

    // Challenge - key to save state if user rotates device destroying activity
    private static final String KEY_HAS_CHEATED = "has_cheated";
    private boolean hasCheated;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_IS_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        // Challenge - Load variable cheating and get it ready for return to QuizActivity
        if (savedInstanceState != null ) {
            hasCheated = savedInstanceState.getBoolean(KEY_HAS_CHEATED, false);
            setAnswerShownResult(hasCheated);
        }

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswertextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Challenge - Updated variable
                hasCheated = true;

                if (mAnswerIsTrue) {
                    mAnswertextView.setText(R.string.true_button);
                } else {
                    mAnswertextView.setText(R.string.false_button);
                }

                //setAnswerShownResult(true);
                setAnswerShownResult(hasCheated);

            }
        });

    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Challenge save state of cheating
        outState.putBoolean(KEY_HAS_CHEATED, hasCheated);
    }
}


