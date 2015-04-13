package com.lewisapp.bookletter.login;

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
import android.widget.TextView;
import android.widget.Toast;

import com.lewisapp.bookletter.BasicResult;
import com.lewisapp.bookletter.FontManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;

public class FindPasswordActivity extends ActionBarActivity {

	EditText et_email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_password);
		et_email = (EditText) findViewById(R.id.editText_id_email);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		Button btn = (Button) findViewById(R.id.button_sendemail);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String emailAddress = et_email.getText().toString();

				NetworkManager.getInstnace().changePassword(
						FindPasswordActivity.this, emailAddress,
						new OnResultListener<BasicResult>() {

							@Override
							public void onSuccess(BasicResult dataFromServer) {
								// TODO Auto-generated method stub
								if (dataFromServer.success.equals("1")) {
									Toast.makeText(FindPasswordActivity.this,
											"설정 페이지 전송 완료", 0).show();
									Intent intent = new Intent();
									intent.putExtra("email", emailAddress);
									setResult(RESULT_OK, intent);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find_password, menu);
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
