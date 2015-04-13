package com.lewisapp.bookletter.login;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.lewisapp.bookletter.FontManager;
import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.PropertyManager;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.UserResult;
import com.lewisapp.bookletter.main.MainActivity;

public class LoginFirstActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login_first);
		ImageButton btn = (ImageButton) findViewById(R.id.button_login_email);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginFirstActivity.this,
						EmailLoginActivity.class);
				
				startActivity(intent);
				overridePendingTransition(0, 0);
			}
		});

		btn = (ImageButton) findViewById(R.id.button_login_facebook);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Session.openActiveSession(LoginFirstActivity.this, true,
						new StatusCallback() {

							@Override
							public void call(Session session,
									SessionState state, Exception exception) {

								if (session.isOpened()) {
									if(!session.getPermissions().contains("email")) {

										String[] PERMISSION_ARRAY_READ = {"email","user_birthday"};

										List<String> PERMISSION_LIST=Arrays.asList(PERMISSION_ARRAY_READ);

										session.requestNewReadPermissions(

										new Session.NewPermissionsRequest(LoginFirstActivity.this, PERMISSION_LIST));

										}
									
									Request.newMeRequest(session,
											new GraphUserCallback() {

												@Override
												public void onCompleted(
														GraphUser user,
														Response response) {
													if (user.getId() != null) {
														final String userId = user
																.getId();
														final String userImg = "http://graph.facebook.com/"
																+ userId
																+ "/picture";
//														final String userEmail = "http://graph.facebook.com/"
//																+ userId
//																+ "/picture";
														final String useremail=(String)user.getProperty("email");
														String accessToken = Session
																.getActiveSession()
																.getAccessToken();
														Log.i("thursday",
																accessToken);
														Log.i("thursday",
																userImg);
														NetworkManager
																.getInstnace()
																.loginFacebook(
																		LoginFirstActivity.this,
																		accessToken,
																		PropertyManager
																				.getInstnace()
																				.getRegistrationId(),
																		new OnResultListener<UserResult>() {

																			@Override
																			public void onSuccess(

																					UserResult dataFromServer) {

																				if (dataFromServer.success
																						.equals("1")) {
																					PropertyManager
																							.getInstnace()
																							.setFacebookId(
																									userId);
																					Intent intent = new Intent(
																							LoginFirstActivity.this,
																							MainActivity.class);
																					MyUserManager
																							.getInstance()
																							.setMyUserData(
																									dataFromServer.user);
																					MyUserManager
																							.getInstance()
																							.doneLogin();
																					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
																							| Intent.FLAG_ACTIVITY_NEW_TASK);
																					startActivity(intent);
																					Log.i("startactivity",
																							"페북로그인2");

																					finish();
																					overridePendingTransition(0, 0);
																				} else if (dataFromServer.success
																						.equals("0")) {

																					Intent intent = new Intent(
																							LoginFirstActivity.this,
																							FacebookInputOtherdataActivity.class);
																					intent.putExtra(
																							"userId",
																							userId);
																					intent.putExtra(
																							"userImg",
																							userImg);
																					intent.putExtra(
																							"userEmail",
																							useremail);

																					startActivity(intent);
																					overridePendingTransition(0, 0);
																				}

																			}

																			@Override
																			public void onFail(
																					int code) {
																				// TODO
																				// Auto-generated
																				// method
																				// stub

																			}

																		});
													}
												}
											}).executeAsync();
								}
							}
						});
			}
		});

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().onActivityResult(this, arg0, arg1, arg2);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
