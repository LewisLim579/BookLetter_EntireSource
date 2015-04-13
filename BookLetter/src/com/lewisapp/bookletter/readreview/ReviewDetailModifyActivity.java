package com.lewisapp.bookletter.readreview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lewisapp.bookletter.BasicResult;
import com.lewisapp.bookletter.FontManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ReviewDetailModifyActivity extends ActionBarActivity {

	ImageView imageView_userimg;
	TextView textView_userName;

	EditText editText_keyword1;
	EditText editText_keyword2;
	EditText editText_keyword3;
	EditText editText_title;
	EditText editText_content;
	Button button_upload;
	ImageButton imageButton_keywordplus;
	ImageLoader mLoader;
	String revNum;
	RadioGroup radioGroup_level;
	RadioButton radiohigh;
	RadioButton radiomid;
	RadioButton radiolow;
	String bookISBN;
	String bookTitle;
	String bookImg;
	LinearLayout layoutkeyword2;
	LinearLayout layoutkeyword3;
	String bookAuth;
	String level;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review_detail_modify);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		revNum = getIntent().getStringExtra("revNum");
		imageButton_keywordplus = (ImageButton) findViewById(R.id.imageButton_keywordplus);
		imageView_userimg = (ImageView) findViewById(R.id.imageView_userImg);
		textView_userName = (TextView) findViewById(R.id.textView_userName);
		editText_keyword1 = (EditText) findViewById(R.id.editText_keyword1);
		editText_keyword2 = (EditText) findViewById(R.id.editText_keyword2);
		editText_keyword3 = (EditText) findViewById(R.id.editText_keyword3);
		editText_title = (EditText) findViewById(R.id.editText_tosubject);
		editText_content = (EditText) findViewById(R.id.editText_reviewcontent);
		layoutkeyword2 = (LinearLayout) findViewById(R.id.layout_keyword2);
		layoutkeyword3 = (LinearLayout) findViewById(R.id.layout_keyword3);
		button_upload = (Button) findViewById(R.id.button_reviewupload);
		mLoader = ImageLoader.getInstance();
		radioGroup_level = (RadioGroup) findViewById(R.id.radioGroup_level);
		radiohigh = (RadioButton) findViewById(R.id.radio_levelhigh);
		radiomid = (RadioButton) findViewById(R.id.radio_levelmid);
		radiolow = (RadioButton) findViewById(R.id.radio_levellow);

		radioGroup_level
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub

						if (checkedId == R.id.radio_levelhigh) {
							level = "3";
							radioGroup_level.setBackground(getResources()
									.getDrawable(R.drawable.level_high));

						} else if (checkedId == R.id.radio_levelmid) {
							level = "2";
							radioGroup_level.setBackground(getResources()
									.getDrawable(R.drawable.level_middle));

						} else if (checkedId == R.id.radio_levellow) {
							level = "1";
							radioGroup_level.setBackground(getResources()
									.getDrawable(R.drawable.level_low));

						}

					}
				});

		init();
		button_upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String revTitle = editText_title.getText().toString();
				final String revContent = editText_content.getText().toString();
				final String key1 = editText_keyword1.getText().toString();
				final String key2 = editText_keyword2.getText().toString();
				final String key3 = editText_keyword3.getText().toString();

				NetworkManager.getInstnace().modifyReviewSave(
						ReviewDetailModifyActivity.this, revNum, revTitle,
						revContent, level, key1, key2, key3, bookISBN,
						bookTitle, bookImg, bookAuth,
						new OnResultListener<BasicResult>() {

							@Override
							public void onSuccess(BasicResult dataFromServer) {
								// TODO Auto-generated method stub
								if (dataFromServer.success.equals("1")) {
									Intent data = new Intent();
									setResult(Activity.RESULT_OK, data);
									finish();
									overridePendingTransition(0, 0);
								}
							}

							@Override
							public void onFail(int code) {
								// TODO Auto-generated method stub

							}
						});
			}
		});
	}

	private void init() {
		NetworkManager.getInstnace().modifyReviewFrom(
				ReviewDetailModifyActivity.this, revNum,
				new OnResultListener<ReviewModifyResult>() {

					@Override
					public void onSuccess(ReviewModifyResult dataFromServer) {
						// TODO Auto-generated method stub
						final String keyword1 = dataFromServer.review.key1;
						final String keyword2 = dataFromServer.review.key2;
						final String keyword3 = dataFromServer.review.key3;
						final String title = dataFromServer.review.revTitle;
						final String content = dataFromServer.review.revContent;
						bookISBN = dataFromServer.review.bookISBN;
						bookTitle = dataFromServer.review.bookTitle;
						bookImg = dataFromServer.review.bookImg;
						bookAuth = dataFromServer.review.bookAuth;
						level = dataFromServer.review.revLevel;
						mLoader.displayImage(dataFromServer.review.userImg,
								imageView_userimg);
						textView_userName
								.setText(dataFromServer.review.userNick);

						if (level.equals("3")) {
							level = "3";
							radioGroup_level.setBackground(getResources()
									.getDrawable(R.drawable.level_high));
							radiohigh.setChecked(true);
						} else if (level.equals("2")) {
							level = "2";
							radioGroup_level.setBackground(getResources()
									.getDrawable(R.drawable.level_middle));
							radiomid.setChecked(true);
						} else if (level.equals("1")) {
							level = "1";
							radioGroup_level.setBackground(getResources()
									.getDrawable(R.drawable.level_low));
							radiolow.setChecked(true);

						}

						if (keyword2 == null) {
							layoutkeyword2.setVisibility(View.GONE);
							layoutkeyword3.setVisibility(View.GONE);
							editText_keyword1
									.setText(dataFromServer.review.key1);
						} else if (keyword3 == null) {
							layoutkeyword2.setVisibility(View.VISIBLE);
							layoutkeyword3.setVisibility(View.GONE);
							editText_keyword1
									.setText(dataFromServer.review.key1);
							editText_keyword2
									.setText(dataFromServer.review.key2);
						} else {
							layoutkeyword2.setVisibility(View.VISIBLE);
							layoutkeyword3.setVisibility(View.VISIBLE);
							editText_keyword1
									.setText(dataFromServer.review.key1);
							editText_keyword2
									.setText(dataFromServer.review.key2);
							editText_keyword3
									.setText(dataFromServer.review.key3);
						}

						editText_title.setText(title);
						editText_content.setText(content);
					}

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub

					}
				});

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(0, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.review_detail_modify, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
			overridePendingTransition(0, 0);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(String name, @NonNull Context context,
			@NonNull AttributeSet attrs) {

		View view = super.onCreateView(name, context, attrs);
		if (view == null && name.equals("TextView")) {
			TextView tv = new TextView(context, attrs);
			tv.setTypeface(FontManager.getInstance().getTypeface(context,
					FontManager.getInstance().FONT_NAME_NOTOSANS));
			view = tv;
		}

		if (view == null && name.equals("Button")) {
			Button btn = new Button(context, attrs);
			btn.setTypeface(FontManager.getInstance().getTypeface(context,
					FontManager.getInstance().FONT_NAME_NOTOSANS));
			view = btn;
		}

		if (view == null && name.equals("EditText")) {
			EditText et = new EditText(context, attrs);
			et.setTypeface(FontManager.getInstance().getTypeface(context,
					FontManager.getInstance().FONT_NAME_NOTOSANS));
			view = et;
		}
		return view;

	}
}
