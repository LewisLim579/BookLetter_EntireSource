package com.lewisapp.bookletter.login;

import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.PropertyManager;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.UserResult;
import com.lewisapp.bookletter.main.MainActivity;

public class SplashActivity extends Activity {

	Handler mHandler = new Handler();
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	private static final String SENDER_ID = "178740230395";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

	}

	Runnable nextAction = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			// PropertyManager.getInstnace().setFacebookId("");
			// PropertyManager.getInstnace().setUserEmail("");
			String email = PropertyManager.getInstnace().getUserEmail();
			String facebookId = PropertyManager.getInstnace().getFacebookId();
			if (!email.equals("")) {// 최근기록이 이메일 로그인

				String password = PropertyManager.getInstnace().getPassword();
				NetworkManager.getInstnace().login(SplashActivity.this, email,
						password,
						PropertyManager.getInstnace().getRegistrationId(),
						new OnResultListener<UserResult>() {

							@Override
							public void onSuccess(UserResult dataFromServer) {
								// TODO Auto-generated method stub
								if (dataFromServer.success.equals("1")) {
									Intent intent = new Intent(
											SplashActivity.this,
											MainActivity.class);

									MyUserManager.getInstance().setMyUserData(
											dataFromServer.user);
									Log.i("startactivity", "이메일로그인 스플래쉬");
									MyUserManager.getInstance().doneLogin();
									startActivity(intent);

									finish(); // 자동로그인된 데이터가있고, 일치해서 로그인 성공하여,
												// 메인으로 이동 //이메일로그인
									overridePendingTransition(0, 0);
								}
							}

							@Override
							public void onFail(int code) {
								// TODO Auto-generated method stub

							}
						});
			} else if (!facebookId.equals("")) { // 최근기록이 페이스북로그인

				login(new StatusCallback() {

					@Override
					public void call(Session session, SessionState state,
							Exception exception) {
						// TODO Auto-generated method stub

						if (session.isOpened()) {

							Request.newMeRequest(session,
									new GraphUserCallback() {

										@Override
										public void onCompleted(GraphUser user,
												Response response) {

											// TODO Auto-generated method stub
											if (user != null) {
												String userId = user.getId();
												if (PropertyManager
														.getInstnace()
														.getFacebookId()
														.equals(userId)) {
													String accessToken = Session
															.getActiveSession()
															.getAccessToken();
													NetworkManager
															.getInstnace()
															.loginFacebook(
																	SplashActivity.this,
																	accessToken,
																	PropertyManager
																			.getInstnace()
																			.getRegistrationId(),
																	new OnResultListener<UserResult>() {

																		@Override
																		public void onSuccess(
																				UserResult dataFromServer) {
																			// TODO
																			// Auto-generated
																			// method
																			// stub
																			if (dataFromServer.success
																					.equals("1")) {
																				Intent intent = new Intent(
																						SplashActivity.this,
																						MainActivity.class);
																				MyUserManager
																						.getInstance()
																						.setMyUserData(
																								dataFromServer.user);

																				MyUserManager
																						.getInstance()
																						.doneLogin(); // 페북로그인성공!
																				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
																						| Intent.FLAG_ACTIVITY_NEW_TASK);

																				startActivity(intent);

																				Log.i("startactivity",
																						"페북로그인 스플래 쉬");
																				finish();
																				overridePendingTransition(
																						0,
																						0);
																			} else if (dataFromServer.message
																					.equals("unregisteredID")) {
																				Intent intent = new Intent(
																						SplashActivity.this,
																						MainActivity.class);
																				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
																						| Intent.FLAG_ACTIVITY_NEW_TASK);
																				startActivity(intent);
																				finish(); // 로그인안하고
																							// 일단
																							// 메인으로
																							// 간다.
																				overridePendingTransition(
																						0,
																						0);
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
												} else {

													Intent intent = new Intent(
															SplashActivity.this,
															MainActivity.class);
													intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
															| Intent.FLAG_ACTIVITY_NEW_TASK);

													startActivity(intent);
													finish(); // 로그인안하고 일단 메인으로
																// 간다.
													overridePendingTransition(
															0, 0);
												}
											}

										}

									}).executeAsync();
						}
					}
				});

			} else {// 자동로그인 안되어 있다.

				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						Intent intent = new Intent(SplashActivity.this,
								MainActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
								| Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);

						finish();
						overridePendingTransition(0, 0);
					}
				}, 1000);

			}
		}
	};

	GoogleCloudMessaging gcm;

	private void registerInBackground() {
		new AsyncTask<Void, Integer, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging
								.getInstance(SplashActivity.this);
					}
					String regid = gcm.register(SENDER_ID);

					PropertyManager.getInstnace().setRegistrationId(regid);

				} catch (IOException ex) {
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				runOnUiThread(nextAction);
			}
		}.execute(null, null, null);
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
						resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST);
				dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {

					}
				});
				dialog.show();
			} else {
				// To Do...
				finish();
			}
			return false;
		}
		return true;
	}

	private void login(Session.StatusCallback callback) {
		Session session = Session.getActiveSession();

		if (session != null && session.isOpened()) {
			session.addCallback(callback);
			callback.call(session, null, null);
			return;
		}

		if (session == null) {
			session = Session.openActiveSessionFromCache(this);
			if (session != null && session.isOpened()) {
				session.addCallback(callback);
				return;
			}
		}

		Session.openActiveSession(this, true, callback);
	}

	boolean isFirst = true;

	@Override
	protected void onResume() {
		super.onResume();
		if (checkPlayServices()) {
			String regId = PropertyManager.getInstnace().getRegistrationId();
			if (!regId.equals("")) {
				runOnUiThread(nextAction);
			} else {
				registerInBackground();
			}
		} else {
			if (isFirst) {
				isFirst = false;
			} else {
				// Toast...
			}
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().onActivityResult(this, arg0, arg1, arg2);
		}
	}

}
