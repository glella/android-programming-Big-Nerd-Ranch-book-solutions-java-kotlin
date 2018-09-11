package com.glella.geoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    // Challenge
    private boolean mAlreadyAnswered;

    public Question(int textResId, boolean answerTrue, boolean alreadyAnswered) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mAlreadyAnswered = alreadyAnswered;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    // Challenge

    public boolean isAlreadyAnswered() {
        return mAlreadyAnswered;
    }

    public void setAlreadyAnswered(boolean alreadyAnswered) {
        mAlreadyAnswered = alreadyAnswered;
    }
}
