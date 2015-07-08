/**
 * 
 */
package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 偷看答案
 * 
 * @author keily
 * 
 */
public class CheatActivity extends Activity {
	
	private static final String TAG = "CheatActivity";
	public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.QuizActivity.answer_is_true";
	public static final String EXTRA_ANSWER_SHOW = "com.bignerdranch.android.geoquiz.CheatActivity.answer_show";
	private static final String sISANSWERSHOW = "sISANSWERSHOW";
	private boolean mAnswerIsTrue;
	private TextView mAnswerTextView;
	private Button mShowAnswerButton;
	private boolean mIsAnswerShow;
	private TextView mDeviceVersion;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		mAnswerIsTrue = getIntent().getBooleanExtra(
				CheatActivity.EXTRA_ANSWER_IS_TRUE, false);
		if (null != savedInstanceState) {
			mIsAnswerShow = savedInstanceState.getBoolean(
					CheatActivity.sISANSWERSHOW, false);
		}
		Log.d(TAG, "in onCreate() ->mIsAnswerShow="+mIsAnswerShow);
		mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
		mShowAnswerButton = (Button) findViewById(R.id.showAnswerButton);
		mShowAnswerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.true_button);
				} else {
					mAnswerTextView.setText(R.string.false_button);
				}
				setAnswerShowResult(true);
			}
		});
		
		mDeviceVersion = (TextView)findViewById(R.id.deviceVersion);
		mDeviceVersion.setText("The Api level:"+Build.VERSION.SDK_INT);

		setAnswerShowResult(mIsAnswerShow);

	}

	private void setAnswerShowResult(boolean isAnswerShow) {
		mIsAnswerShow = isAnswerShow;
		Log.d(TAG, "in setAnswerShowResult() ->mIsAnswerShow="+mIsAnswerShow);
		Intent intent = new Intent();
		intent.putExtra(CheatActivity.EXTRA_ANSWER_SHOW, isAnswerShow);
		setResult(RESULT_OK, intent);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(CheatActivity.sISANSWERSHOW, mIsAnswerShow);
	}
}
