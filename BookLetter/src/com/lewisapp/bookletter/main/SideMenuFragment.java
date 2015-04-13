package com.lewisapp.bookletter.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.login.LoginFirstActivity;
import com.lewisapp.bookletter.setting.SettingMainActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SideMenuFragment extends Fragment {

	public static final int MENU_INVALID = -1;
	public static final int MENU_CLOUD = 0;
	public static final int MENU_READREVIEW = 1;
	public static final int MENU_REQUESTREVIEW = 2;
	public static final int MENU_MYREVIEW = 3;
	public static final int MENU_EVENT = 4;

	ListView listView;
	ArrayAdapter<String> mAdapter;

	TextView textView_needLogin;
	TextView textView_UserName;
	TextView textView_userEmail;
	ImageView imageView_userImg;
	LinearLayout layout_myinfoHeaderView;
	ImageLoader mLoader;
	SideAdapter mySideAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.menu_layout, container, false);
		listView = (ListView) view.findViewById(R.id.listView_sidemenu);

		mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1);
		View headerViewMyInfo = getLayoutInflater(savedInstanceState).inflate(
				R.layout.headerview_sidebar_mypage, null);
		textView_UserName = (TextView) headerViewMyInfo
				.findViewById(R.id.textView_usernick);
		textView_userEmail = (TextView) headerViewMyInfo
				.findViewById(R.id.textView_useremail);
		layout_myinfoHeaderView = (LinearLayout) headerViewMyInfo
				.findViewById(R.id.layout_sidebarheader);
		imageView_userImg = (ImageView) headerViewMyInfo
				.findViewById(R.id.imageVier_userimg);
		textView_needLogin = (TextView) headerViewMyInfo
				.findViewById(R.id.textView_needlogin);
		// 임시 코드


		mLoader = ImageLoader.getInstance();

		headerViewMyInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (!MyUserManager.getInstance().isLogin()) {
					Intent tmpIntent = new Intent(getActivity(),
							LoginFirstActivity.class);
					startActivity(tmpIntent);
					getActivity().overridePendingTransition(0, 0);
				} else {
					Intent tmpIntent = new Intent(getActivity(),
							SettingMainActivity.class);
					startActivity(tmpIntent);
					getActivity().overridePendingTransition(0, 0);
				}
			}
		});
		listView.addHeaderView(headerViewMyInfo);
		ImageView imageview1 = new ImageView(getActivity());
		imageview1.setImageResource(R.drawable.sidehome);
		imageview1.setScaleType(ScaleType.CENTER_CROP);
		listView.addHeaderView(imageview1);

		ImageView imageview2 = new ImageView(getActivity());
		imageview2.setImageResource(R.drawable.sidetotalreview);
		imageview2.setScaleType(ScaleType.CENTER_CROP);
		listView.addHeaderView(imageview2);

		ImageView imageview3 = new ImageView(getActivity());
		imageview3.setImageResource(R.drawable.siderequest);
		imageview3.setScaleType(ScaleType.CENTER_CROP);
		listView.addHeaderView(imageview3);

		ImageView imageview4 = new ImageView(getActivity());
		imageview4.setImageResource(R.drawable.sidemypage);
		imageview4.setScaleType(ScaleType.CENTER_CROP);
		listView.addHeaderView(imageview4);

		ImageView imageview5 = new ImageView(getActivity());
		imageview5.setImageResource(R.drawable.sideevent);
		imageview5.setScaleType(ScaleType.CENTER_CROP);
		listView.addHeaderView(imageview5);

		listView.setAdapter(mAdapter);
		// mAdapter.add("홈");
		// mAdapter.add("리뷰보기");
		// mAdapter.add("리뷰요청");
		// mAdapter.add("내 리뷰보기");
		// mAdapter.add("이벤트");

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (callback != null) {
					int menuId = getMenuId(position);
					if (menuId != MENU_INVALID) {

						callback.selectMenu(menuId);

					}
				}
			}
		});

		return view;
	}

	MenuCallback callback;

	public interface MenuCallback {
		public void selectMenu(int menuId);
	}

	public int getMenuId(int position) {
		switch (position) {
		case 1:
			return MENU_CLOUD;
		case 2:
			return MENU_READREVIEW;
		case 3:
			return MENU_REQUESTREVIEW;
		case 4:
			return MENU_MYREVIEW;
		case 5:
			return MENU_EVENT;
		}
		return MENU_INVALID;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof MenuCallback) {
			callback = (MenuCallback) activity;
		} else {
			// Throws...
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (!MyUserManager.getInstance().isLogin()) {
			textView_UserName.setVisibility(View.GONE);
			textView_userEmail.setVisibility(View.GONE);
			textView_needLogin.setVisibility(View.VISIBLE);
			layout_myinfoHeaderView.setBackgroundResource(R.drawable.back0);
		}

		else {

			textView_UserName.setVisibility(View.VISIBLE);
			textView_userEmail.setVisibility(View.VISIBLE);
			if (MyUserManager.getInstance().getMyUserData().userImg.equals("1")) {
				imageView_userImg
						.setImageResource(R.drawable.nologinuserimgman);
			} else if (MyUserManager.getInstance().getMyUserData().userImg
					.equals("2")) {
				imageView_userImg
						.setImageResource(R.drawable.nologinuserimgman);
			} else {
				Log.i("thursday",
						MyUserManager.getInstance().getMyUserData().userImg);
				mLoader.displayImage(MyUserManager.getInstance()
						.getMyUserData().userImg, imageView_userImg);

			}

			textView_needLogin.setVisibility(View.GONE);
			textView_UserName.setText(MyUserManager.getInstance()
					.getMyUserData().userNick);
			textView_userEmail.setText(MyUserManager.getInstance()
					.getMyUserData().userEmail);
			
			String userbackindex = MyUserManager.getInstance().getMyUserData().userBackground;
			if (userbackindex.equals("0")) {
				layout_myinfoHeaderView.setBackgroundResource(R.drawable.back0);
			} else if (userbackindex.equals("1")) {
				layout_myinfoHeaderView.setBackgroundResource(R.drawable.back1);
			} else if (userbackindex.equals("2")) {
				layout_myinfoHeaderView.setBackgroundResource(R.drawable.back2);
			} else if (userbackindex.equals("3")) {
				layout_myinfoHeaderView.setBackgroundResource(R.drawable.back3);
			} else if (userbackindex.equals("4")) {
				layout_myinfoHeaderView.setBackgroundResource(R.drawable.back4);
			} else if (userbackindex.equals("5")) {
				layout_myinfoHeaderView.setBackgroundResource(R.drawable.back5);
			} else if (userbackindex.equals("6")) {
				layout_myinfoHeaderView.setBackgroundResource(R.drawable.back6);
			} else if (userbackindex.equals("7")) {
				layout_myinfoHeaderView.setBackgroundResource(R.drawable.back7);
			} else if (userbackindex.equals("8")) {
				layout_myinfoHeaderView.setBackgroundResource(R.drawable.back8);
			} else if (userbackindex.equals("9")) {
				layout_myinfoHeaderView.setBackgroundResource(R.drawable.back9);
			} else if (userbackindex.equals("10")) {
				layout_myinfoHeaderView.setBackgroundResource(R.drawable.back10);
			}
		}

	}

}
