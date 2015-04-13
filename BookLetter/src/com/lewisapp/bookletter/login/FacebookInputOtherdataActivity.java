package com.lewisapp.bookletter.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.FontManager;
import com.lewisapp.bookletter.PropertyManager;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.UserResult;
import com.lewisapp.bookletter.dialog.SignUpDialogFragment;
import com.lewisapp.bookletter.main.MainActivity;

public class FacebookInputOtherdataActivity extends ActionBarActivity {
	String userId;
	String userImg;
	String userEmail;
	EditText editTextNickName;
	RadioGroup genderGroup;
	String gender;
	TextView tv;
	TextView tv2;
	CheckBox checkBox_notice;
	RadioButton radioMale;
	RadioButton radioFemale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook_input_otherdata);
		userId = getIntent().getStringExtra("userId");
		userImg = getIntent().getStringExtra("userImg");
		userEmail = getIntent().getStringExtra("userEmail");
		radioMale = (RadioButton) findViewById(R.id.radio_male);
		radioFemale = (RadioButton) findViewById(R.id.radio_female);
		radioMale.setTextColor(Color.WHITE);
		radioFemale.setTextColor(R.color.lightblack);
		userImg += "?type=large";
		checkBox_notice = (CheckBox) findViewById(R.id.checkBox_notice);
		editTextNickName = (EditText) findViewById(R.id.editText_nickname);
		genderGroup = (RadioGroup) findViewById(R.id.radioGroup_genderradio);

		genderGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.radio_male) {
					gender = "1";
					radioMale.setTextColor(Color.WHITE);
					radioFemale.setTextColor(R.color.lightblack);
				} else if (checkedId == R.id.radio_female) {
					gender = "2";
					radioMale.setTextColor(R.color.lightblack);
					radioFemale.setTextColor(Color.WHITE);
				}
			}
		});
		gender = "1";

		Button btn = (Button) findViewById(R.id.button_signup);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (checkBox_notice.isChecked()) {

					final String userNick = editTextNickName.getText()
							.toString();

					if (userNick.length() > 6 || userNick.length() < 1) {
						Toast.makeText(FacebookInputOtherdataActivity.this,
								"닉네임은 1~6글자입니다.", 0).show();
					} else {
						NetworkManager.getInstnace().signUpFacebook(
								FacebookInputOtherdataActivity.this,
								userNick,
								userEmail,
								gender,
								userImg,
								PropertyManager.getInstnace()
										.getRegistrationId(),
								new OnResultListener<UserResult>() { // 페이스북
																		// 회원가입

									@Override
									public void onSuccess(
											UserResult dataFromServer) {
										// TODO Auto-generated method stub
										if (dataFromServer.success.equals("1")) {
											PropertyManager.getInstnace()
													.setFacebookId(userId);
											MyUserManager
													.getInstance()
													.setMyUserData(
															dataFromServer.user);
											Intent intent = new Intent(
													FacebookInputOtherdataActivity.this,
													MainActivity.class);
											intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
													| Intent.FLAG_ACTIVITY_NEW_TASK);
											MyUserManager.getInstance()
													.doneLogin();
											startActivity(intent);

											finish();
											overridePendingTransition(0, 0);
										} else if (dataFromServer.success
												.equals("0")) {

											Toast.makeText(
													FacebookInputOtherdataActivity.this,
													dataFromServer.message, 0)
													.show();
										}
									}

									@Override
									public void onFail(int code) {
										// TODO Auto-generated method stub

									}
								});
					}
				} else {
					Toast.makeText(FacebookInputOtherdataActivity.this,
							"이용약관 및 개인정보 취급방침에 동의해주세요", 0).show();
				}

			}
		});

		tv = (TextView) findViewById(R.id.textView_notice1);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String tmp = Html.fromHtml(
						getResources().getString(R.string.notice1)).toString();

				SignUpDialogFragment dialog = new SignUpDialogFragment("이용 약관",
						tmp);
				dialog.show(getSupportFragmentManager(), "약관");
			}
		});

		tv2 = (TextView) findViewById(R.id.textView_notice2);
		tv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String tmp2 = Html.fromHtml(
						getResources().getString(R.string.notice2)).toString();
				SignUpDialogFragment dialog = new SignUpDialogFragment(
						"개인정보 취급방침", tmp2);
				dialog.show(getSupportFragmentManager(), "약관");
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.facebook_input_nickname, menu);
		return true;
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(0, 0);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		return super.onOptionsItemSelected(item);
	}
}
