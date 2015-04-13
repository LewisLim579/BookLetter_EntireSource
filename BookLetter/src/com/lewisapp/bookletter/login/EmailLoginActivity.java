package com.lewisapp.bookletter.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.FontManager;
import com.lewisapp.bookletter.PropertyManager;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.UserResult;
import com.lewisapp.bookletter.main.MainActivity;

public class EmailLoginActivity extends ActionBarActivity {

	EditText editText_email;
	EditText editText_pass;
	TextView findPass;
	public final int REQUESTCODE_SENDEMAIL = 105;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email_login);
		editText_email = (EditText) findViewById(R.id.editText_id_email);
		editText_pass = (EditText) findViewById(R.id.editText_id_password);
		Button btn = (Button) findViewById(R.id.button_login);
		findPass = (TextView) findViewById(R.id.find_password);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				final String email = editText_email.getText().toString();
				final String password = editText_pass.getText().toString();

				NetworkManager.getInstnace().login(EmailLoginActivity.this,
						email, password,
						PropertyManager.getInstnace().getRegistrationId(),
						new OnResultListener<UserResult>() {

							@Override
							public void onSuccess(UserResult dataFromServer) {

								// TODO Auto-generated method stub
								if (dataFromServer.success.equals("1")) {
									Intent intent = new Intent(
											EmailLoginActivity.this,
											MainActivity.class);
									MyUserManager.getInstance().setMyUserData(
											dataFromServer.user);


									PropertyManager.getInstnace().setUserEmail(
											email);
									PropertyManager.getInstnace().setPassword(
											password);
									MyUserManager.getInstance().doneLogin();
									intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
											| Intent.FLAG_ACTIVITY_NEW_TASK);
									startActivity(intent);
								
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

		btn = (Button) findViewById(R.id.button_signupgo);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(EmailLoginActivity.this,
						SignupActivity.class);
				startActivity(intent);
				overridePendingTransition(0, 0);
			}
		});

		findPass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(EmailLoginActivity.this,
						FindPasswordActivity.class);
				startActivityForResult(intent, REQUESTCODE_SENDEMAIL);
				overridePendingTransition(0, 0);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE_SENDEMAIL
				&& resultCode == Activity.RESULT_OK) {
			String email = data.getStringExtra("email");
			editText_email.setText(email);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.email_login, menu);
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
