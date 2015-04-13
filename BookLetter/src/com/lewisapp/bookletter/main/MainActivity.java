package com.lewisapp.bookletter.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.lewisapp.bookletter.FontManager;
import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.dialog.NeedLoginMyPageFragment;
import com.lewisapp.bookletter.event.EventMainFragment;
import com.lewisapp.bookletter.myreview.MyReviewMainFragment;
import com.lewisapp.bookletter.readreview.ReadMainFragment;
import com.lewisapp.bookletter.requestreview.RequestMainFragment;
import com.lewisapp.bookletter.wordcloud.CloudMainFragment;

public class MainActivity extends SlidingFragmentActivity implements
		SideMenuFragment.MenuCallback {

	SlidingMenu sm;
	private static final String TAG_CLOUD = "cloud";
	private static final String TAG_READREVIEW = "readreview";
	private static final String TAG_REQUESTREVIEW = "requestreview";
	private static final String TAG_MYREVIEW = "myreview";
	private static final String TAG_EVENT = "event";

	private String currentTag = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.menu_container);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new CloudMainFragment()).commit();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.menu_container, new SideMenuFragment()).commit();
			currentTag = TAG_CLOUD;
		} else {
			String tag = savedInstanceState.getString("tag");
			if (tag.equals(TAG_CLOUD)) {
				currentTag = TAG_CLOUD;
			} else if (tag.equals(TAG_READREVIEW)) {
				currentTag = TAG_READREVIEW;
			} else if (tag.equals(TAG_REQUESTREVIEW)) {
				currentTag = TAG_REQUESTREVIEW;
			} else if (tag.equals(TAG_MYREVIEW)) {
				currentTag = TAG_MYREVIEW;
			} else if (tag.equals(TAG_EVENT)) {
				currentTag = TAG_EVENT;
			} else {
				currentTag = TAG_CLOUD;
			}
		}
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.myhamberger);
		sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.sliding_menu_shadow);
		sm.setFadeDegree(0.5f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		setSlidingActionBarEnabled(false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			toggle();
			return true;
		}
		return false;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("tag", currentTag);
	}

	@Override
	public void onBackPressed() {

		if (currentTag.equals(TAG_CLOUD)) {
			if (!isBackPressed) {
				isBackPressed = true;
				Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT)
						.show();
				mHandler.sendEmptyMessageDelayed(MESSAGE_TIME_OUT_BACK_KEY,
						TIME_BACK_KEY);
			} else {
				mHandler.removeMessages(MESSAGE_TIME_OUT_BACK_KEY);
				super.onBackPressed();
			}
		} else {
			currentTag = TAG_CLOUD;
			super.onBackPressed();
		}

	}

	boolean isBackPressed = false;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_TIME_OUT_BACK_KEY:
				isBackPressed = false;
				break;
			}
		}
	};
	private static final int MESSAGE_TIME_OUT_BACK_KEY = 0;
	private static final int TIME_BACK_KEY = 2000;

	public void cloudCallReviews(String keyword) {
		// selectMenu(SideMenuFragment.MENU_READREVIEW);

		Fragment f = null;
		boolean isAdd = false;
		boolean backMain = false;
		if (currentTag != TAG_READREVIEW) {
			currentTag = TAG_READREVIEW;
			backMain = true;

			Bundle bundle = new Bundle();
			bundle.putString("keyword", keyword);

			bundle.putString("order", "1");
			f = new ReadMainFragment();
			f.setArguments(bundle);

			isAdd = true;
		}
		if (backMain) {
			getSupportFragmentManager().popBackStack(null,
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
		if (isAdd) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, f).addToBackStack(null).commit();
		}
		showContent();

	}

	@Override
	public void selectMenu(int menuId) {
		// TODO Auto-generated method stub

		Fragment f = null;
		boolean isAdd = false;
		boolean backMain = false;
		switch (menuId) {
		case SideMenuFragment.MENU_CLOUD:
			if (currentTag != TAG_CLOUD) {
				currentTag = TAG_CLOUD;
				backMain = true;

			}
			break;
		case SideMenuFragment.MENU_READREVIEW:
			if (currentTag != TAG_READREVIEW) {
				currentTag = TAG_READREVIEW;
				backMain = true;

				f = new ReadMainFragment();

				isAdd = true;
			}
			break;

		case SideMenuFragment.MENU_REQUESTREVIEW:
			if (currentTag != TAG_REQUESTREVIEW) {
				currentTag = TAG_REQUESTREVIEW;
				backMain = true;
				f = new RequestMainFragment();
				isAdd = true;
			}
			break;

		case SideMenuFragment.MENU_MYREVIEW:

			if (MyUserManager.getInstance().isLogin()) {
				if (currentTag != TAG_MYREVIEW) {
					currentTag = TAG_MYREVIEW;
					backMain = true;
					f = new MyReviewMainFragment();
					isAdd = true;
				}
			} else {
				NeedLoginMyPageFragment dialog = new NeedLoginMyPageFragment();
				dialog.show(getSupportFragmentManager(), "로그인");
			}
			break;

		case SideMenuFragment.MENU_EVENT:
			if (currentTag != TAG_EVENT) {
				currentTag = TAG_EVENT;
				backMain = true;
				f = new EventMainFragment();
				isAdd = true;
			}
			break;
		}
		if (backMain) {
			getSupportFragmentManager().popBackStack(null,
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
		if (isAdd) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, f).addToBackStack(null).commit();
		}
		showContent();
	}

	//
	// public void callReview(String message){
	//
	// Fragment f = null;
	// if (currentTag != TAG_READREVIEW) {
	// currentTag = TAG_READREVIEW;
	// backMain = true;
	// f = new ReadMainFragment();
	// isAdd = true;
	// }
	// if (backMain) {
	// getSupportFragmentManager().popBackStack(null,
	// FragmentManager.POP_BACK_STACK_INCLUSIVE);
	// }
	// if (isAdd) {
	// getSupportFragmentManager().beginTransaction()
	// .replace(R.id.container, f).addToBackStack(null).commit();
	// }
	// showContent();
	// }

	@Override
	public View onCreateView(String name, @NonNull Context context,
			@NonNull AttributeSet attrs) {

		View view = super.onCreateView(name, context, attrs);
		if (view == null && name.equals("TextView")) {
			// TextView tv = new TextView(context, attrs);
			// tv.setTypeface(FontManager.getInstance().getTypeface(context,
			// FontManager.getInstance().FONT_NAME_NOTOSANS));
			// view = tv;

			int[] ids = { android.R.attr.fontFamily };
			TypedArray ta = context.obtainStyledAttributes(attrs, ids);
			String fontName = ta.getString(0);
			ta.recycle();
			int[] ids2 = { android.R.attr.textStyle };
			TypedArray ta2 = context.obtainStyledAttributes(attrs, ids2);
			int textType = ta2.getInt(0, 0);
			ta2.recycle();
			Typeface tf = FontManager.getInstance().getTypeface(context,
					fontName);
			if (tf != null) {
				TextView tv = new TextView(context, attrs);
				tv.setTypeface(tf, textType);
				return tv;
			}
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
