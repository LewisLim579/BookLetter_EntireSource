package com.lewisapp.bookletter.login;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lewisapp.bookletter.FontManager;
import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.PropertyManager;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.UserResult;
import com.lewisapp.bookletter.dialog.SignUpDialogFragment;
import com.lewisapp.bookletter.main.MainActivity;

public class SignupActivity extends ActionBarActivity {

	EditText editTextNickName;
	EditText editTextEmail;
	EditText editTextPasswrod;
	TextView tv;
	TextView tv2;
	RadioGroup genderGroup;
	String gender;
	RadioButton radioMale;
	RadioButton radioFemale;

	CheckBox checkBox_notice;
	final String GMAIL = "com.google";
	private AccountManager mailManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		genderGroup = (RadioGroup) findViewById(R.id.radioGroup_genderradio);
		gender = "1";
		Button btn = (Button) findViewById(R.id.button_signup);
		editTextEmail = (EditText) findViewById(R.id.editText_email);
		editTextNickName = (EditText) findViewById(R.id.editText_nickname);
		editTextPasswrod = (EditText) findViewById(R.id.editText_password);
		radioMale = (RadioButton) findViewById(R.id.radio_male);
		radioFemale = (RadioButton) findViewById(R.id.radio_female);
		radioMale.setTextColor(Color.WHITE);
		radioFemale.setTextColor(R.color.lightblack);
		checkBox_notice = (CheckBox) findViewById(R.id.checkBox_notice);
		mailManager = AccountManager.get(this);
		for (Account account : mailManager.getAccountsByType(GMAIL)) {
			editTextEmail.setText(account.name);
		}
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

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (checkBox_notice.isChecked()) {
					final String email = editTextEmail.getText().toString();
					final String password = editTextPasswrod.getText()
							.toString();
					final String userNick = editTextNickName.getText()
							.toString();

	
				
					if (userNick.length() > 6 || userNick.length()<1) {
						 Toast.makeText(SignupActivity.this, "닉네임은 1~6글자입니다.", 0).show();
					}else if (password.length() < 5 || password.length() > 12){
						Toast.makeText(SignupActivity.this, "비밀번호는 5~12자입니다.",
								0).show();
					}
					else{
					NetworkManager.getInstnace().signUp(SignupActivity.this,
							userNick, email, password, gender, gender,
							PropertyManager.getInstnace().getRegistrationId(),
							new OnResultListener<UserResult>() {

								@Override
								public void onSuccess(UserResult dataFromServer) {

									if (dataFromServer.success.equals("1")) {
										// TODO Auto-generated method stub

										Intent intent = new Intent(
												SignupActivity.this,
												MainActivity.class);
										PropertyManager.getInstnace()
												.setUserEmail(email);
										PropertyManager.getInstnace()
												.setPassword(password);

										MyUserManager.getInstance()
												.setMyUserData(
														dataFromServer.user);
										MyUserManager.getInstance().doneLogin(); // 임시
										intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
												| Intent.FLAG_ACTIVITY_NEW_TASK);
										startActivity(intent);
										finish();
										overridePendingTransition(0, 0);
									}
									else if(dataFromServer.success.equals("0")){
										
										Toast.makeText(SignupActivity.this, dataFromServer.message, 0).show();
									}

								}

								@Override
								public void onFail(int code) {
									// TODO Auto-generated method stub
									// 로그인실패해서 그 경우에 따라서 밑에
								}
							});
					
					}
					
					
					
					
					
				} else {
					Toast.makeText(SignupActivity.this,
							"이용약관 및 개인정보 취급방침에 동의해주세요", 0).show();
				}

			}
		});

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);

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
		if (id == android.R.id.home) {
			finish();
			overridePendingTransition(0, 0);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
