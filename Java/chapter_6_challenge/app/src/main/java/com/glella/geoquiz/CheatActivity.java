package com.glella.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
    // Challenge
    private TextView mShowAPITextView;

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

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswertextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswerIsTrue) {
                    mAnswertextView.setText(R.string.true_button);
                } else {
                    mAnswertextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });

        // Challenge
        mShowAPITextView = (TextView) findViewById(R.id.show_api_text_view);
        String versionAPI = "API Level " + String.valueOf(Build.VERSION.SDK_INT); // SDK deprecated
        mShowAPITextView.setText(versionAPI);


    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_IS_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
