package com.bignerdranch.android.geoquiz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends ActionBarActivity {

	private final static String TAG = "QuizActivity";
	private final static String sCURRENTINDEX = "sCURRENTINDEX";
	private final static String sISCHEAT = "sISCHEAT";
	public static final int REQ_ANSWER = 0;
	private Button mTrueButton;
	private Button mFalsebutton;
	private TextView mQuestionTextView;
	private ImageButton mNextButton;
	private ImageButton mPreButton;
	private Button mCheatButton;
	private boolean mIsCheat;

	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_asia, true),
			new TrueFalse(R.string.quesion_americas, true),
			new TrueFalse(R.string.question_midest, false),
			new TrueFalse(R.string.question_oceans, true) };
	private boolean[] mQuestionCheat = new boolean[] { false, false, false,
			false, false };

	private int mCurrentIndex = 0;// 问题索引

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_quiz);
		/*if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
			ActionBar actionBar = getActionBar();
			actionBar.setTitle("Bodies of water");
		}*/
		if (null != savedInstanceState) {// 获取屏幕旋转时保存的数据
			mCurrentIndex = savedInstanceState.getInt(
					QuizActivity.sCURRENTINDEX, 0);
			mIsCheat = savedInstanceState.getBoolean(QuizActivity.sISCHEAT,
					false);
		}

		Log.d(TAG, "onCreate() called,currentIndex=" + mCurrentIndex);
		mTrueButton = (Button) findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});
		mFalsebutton = (Button) findViewById(R.id.false_button);
		mFalsebutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});

		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
		mQuestionTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				updateQuestion();
			}
		});

		mNextButton = (ImageButton) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mIsCheat = false;
				mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
				updateQuestion();
			}
		});

		mPreButton = (ImageButton) findViewById(R.id.pre_button);
		mPreButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mIsCheat = false;
				mCurrentIndex = Math
						.abs((mQuestionBank.length + mCurrentIndex - 1)
								% mQuestionBank.length);
				updateQuestion();
				Log.d(TAG, "mCurrentIndex=" + mCurrentIndex);
			}
		});

		mCheatButton = (Button) findViewById(R.id.cheatButton);
		mCheatButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(QuizActivity.this,
						CheatActivity.class);
				boolean isAnswerTrue = mQuestionBank[mCurrentIndex]
						.isTrueQuestion();
				intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE,
						isAnswerTrue);
				// startActivity(intent);
				startActivityForResult(intent, QuizActivity.REQ_ANSWER);
			}
		});

		updateQuestion();

		/*
		 * if (savedInstanceState == null) {
		 * getSupportFragmentManager().beginTransaction() .add(R.id.container,
		 * new PlaceholderFragment()) .commit(); }
		 */
	}

	private void updateQuestion() {
		Log.d(TAG, "updateQuestion()", new Exception("调试异常"));
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}

	private void checkAnswer(boolean userProcessed) {
		Log.d(TAG, "checkAnswer()");
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
		int messageResId = 0;
		if (mQuestionCheat[mCurrentIndex]) {
			messageResId = R.string.judgment_toast;
		} else {
			if (answerIsTrue == userProcessed) {
				messageResId = R.string.correct_toast;
			} else {
				messageResId = R.string.incorrect_toast;
			}
		}

		Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_quiz, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart() called");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume() called");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause() called");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop() called");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() called");
	}

	/**
	 * 屏幕旋转时，需要保存的数据
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanceState() called ,mCurrentIndex="
				+ mCurrentIndex);
		Log.d(TAG, "onSaveInstanceState() called", new NullPointerException(
				"测试异常"));
		outState.putInt(QuizActivity.sCURRENTINDEX, mCurrentIndex);
		outState.putBoolean(QuizActivity.sISCHEAT, mIsCheat);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (null == data) {
			return;
		}
		Log.d(TAG, "requestCode=" + requestCode + ",resultCode=" + resultCode);
		switch (requestCode) {
		case QuizActivity.REQ_ANSWER:
			mIsCheat = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOW,
					false);
			mQuestionCheat[mCurrentIndex] = mIsCheat;
			break;

		}
	}

}
