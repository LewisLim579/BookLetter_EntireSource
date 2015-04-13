package com.lewisapp.bookletter;

import android.content.Context;
import android.content.SharedPreferences;

public class PropertyManager {
	private static PropertyManager instance;

	public static PropertyManager getInstnace() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}

	private static final String PREF_NAME = "mysettting";
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;


	private PropertyManager() {
		mPrefs = MyApplication.getContext().getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		mEditor = mPrefs.edit();
	}

	private static final String FIELD_USER_EMAIL = "useremail";

	private String mUserEmail;

	public void setUserEmail(String email) {
		mUserEmail = email;
		mEditor.putString(FIELD_USER_EMAIL, email); // 앞의 태그로 email 세팅.
		mEditor.commit();
	}

	public String getUserEmail() {
		if (mUserEmail == null) {
			mUserEmail = mPrefs.getString(FIELD_USER_EMAIL, ""); // 태그로설정된거 가져오고
																	// 없으면 비워두기.
		}
		return mUserEmail;
	}
	
	private static final String FIELD_FACEBOOK = "facebookId";
	private String facebookId;
	public void setFacebookId(String userId) {
		this.facebookId = userId;
		mEditor.putString(FIELD_FACEBOOK, userId);
		mEditor.commit();
	}
	
	public String getFacebookId() {
		if (facebookId == null) {
			facebookId = mPrefs.getString(FIELD_FACEBOOK, "");
		}
		return facebookId;
	}

	private static final String FIELD_PASSWORD = "password";
	private String mPassword;

	public String getPassword() {
		if (mPassword == null) {
			mPassword = mPrefs.getString(FIELD_PASSWORD, "");
		}
		return mPassword;
	}

	public void setPassword(String password) {
		mPassword = password;
		mEditor.putString(FIELD_PASSWORD, password);
		mEditor.commit();
	}


	private static final String FIELD_ISPUSH = "ispush";
	private boolean ispush;

	public boolean getisIspush() {
		this.ispush=mPrefs.getBoolean(FIELD_ISPUSH, true);
		return this.ispush;
	}

	public void setIspush(boolean ispush) {
		this.ispush = ispush;
		mEditor.putBoolean(FIELD_ISPUSH, ispush);
		mEditor.commit();
	}

	private String regId;
	private static final String REG_ID = "regId";
	
	public void setRegistrationId(String regId) {
		this.regId = regId;
		mEditor.putString(REG_ID, regId);
		mEditor.commit();
	}
	
	public String getRegistrationId() {
		if (regId == null) {
			regId = mPrefs.getString(REG_ID, "");
		}
		return regId;
	}
	
	private Boolean isRegistered;
	private static final String IS_REGISTER = "register";
	
	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
		mEditor.putBoolean(IS_REGISTER, isRegistered);
		mEditor.commit();
	}
	
	public boolean isRegistered() {
		if (isRegistered == null) {
			isRegistered = mPrefs.getBoolean(IS_REGISTER, false);
		}
		return isRegistered;
	}
	
}
