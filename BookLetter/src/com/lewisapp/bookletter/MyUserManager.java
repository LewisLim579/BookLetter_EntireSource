package com.lewisapp.bookletter;


public class MyUserManager {

	private static MyUserManager instance;

	public static MyUserManager getInstance() {
		if (instance == null) {
			instance = new MyUserManager();
		}
		return instance;
	}

	private boolean isLogin;
	
	public static UserData myUserData=new UserData();
	


	public  UserData getMyUserData() {
		return myUserData;
	}
	
	public void setMyUserImg(String url){
		myUserData.userImg=url;
	}

	public void setMyUserData(UserData myUserDataFromServer) {
		myUserData = myUserDataFromServer;
	}

	private MyUserManager() {

	}

	public void doneLogin() {
		isLogin = true;
	}

	public void doLogout() {
		isLogin = false;
	}

	public boolean isLogin() {
		return isLogin;
	}

}
