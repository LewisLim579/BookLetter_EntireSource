package com.lewisapp.bookletter.readreview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import com.lewisapp.bookletter.FontManager;
import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.dialog.NeedLoginMyPageFragment;
import com.lewisapp.bookletter.requestreview.RequestWriteActivity;
import com.lewisapp.bookletter.writereview.WriteReviewActivity;

public class ShowReviewActivity extends ActionBarActivity {

	private static final String BOOKINFO_TAG = "tab_bookinfo";
	private static final String SHOWREVIEW_TAG = "tab_showreview";
	Button btn_bookinfotab;
	Button btn_reviewcontent_tab;
	ShowReviewBookInfoFragment bookInfoFragment;
	ShowReviewContentFragment contentFragment;

	String ISBN;

	String revNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_review);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		revNum = getIntent().getStringExtra("revNum");

		contentFragment = new ShowReviewContentFragment();
		bookInfoFragment = new ShowReviewBookInfoFragment();

		btn_bookinfotab = (Button) findViewById(R.id.button_tab_bookinfo);
		btn_reviewcontent_tab = (Button) findViewById(R.id.button_tab_reviewcontent);

		btn_reviewcontent_tab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_bookinfotab.setSelected(false);
				btn_reviewcontent_tab.setSelected(true);
				// Fragment f = getSupportFragmentManager().findFragmentByTag(
				// SHOWREVIEW_TAG);
				//
				// if (f == null) {
				// FragmentTransaction ft = getSupportFragmentManager()
				// .beginTransaction();
				//
				// f = contentFragment;
				// ft.replace(R.id.container_showreview, f, SHOWREVIEW_TAG);
				// ft.commit();
				// }
				getSupportFragmentManager().popBackStack();
			}
		});
		btn_bookinfotab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_bookinfotab.setSelected(true);
				btn_reviewcontent_tab.setSelected(false);

				Fragment f = getSupportFragmentManager().findFragmentByTag(
						BOOKINFO_TAG);
				if (f == null) {
					FragmentTransaction ft = getSupportFragmentManager()
							.beginTransaction();
					f = bookInfoFragment;
					Bundle bundle = new Bundle();
					bundle.putString("isbn", ISBN);
					// bundle.putString("revNum", revNum);
					bookInfoFragment.setArguments(bundle);
					ft.replace(R.id.container_showreview, f, BOOKINFO_TAG);
					ft.addToBackStack(null);
					ft.commit();
				}
			}
		});

		Bundle revNumBundle = new Bundle();
		revNumBundle.putString("revNum", revNum);
		contentFragment.setArguments(revNumBundle);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.container_showreview, contentFragment, SHOWREVIEW_TAG);
		ft.commit();
		btn_bookinfotab.setSelected(false);
		btn_reviewcontent_tab.setSelected(true);

	}

	public void dopopStack() {
		getSupportFragmentManager().popBackStack();
		btn_bookinfotab.setSelected(false);
		btn_reviewcontent_tab.setSelected(true);
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_review, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		// if (id == R.id.item_write) {
		//
		// if (MyUserManager.getInstance().isLogin()) {
		// Intent intent = new Intent(ShowReviewActivity.this,
		// WriteReviewActivity.class);
		// startActivity(intent);
		// return true;
		// } else {
		// NeedLoginMyPageFragment dialog = new NeedLoginMyPageFragment();
		// dialog.show(getSupportFragmentManager(), "로그인");
		//
		// }
		//
		// }

		if (id == android.R.id.home) {
			finish();
			overridePendingTransition(0, 0);
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(0, 0);
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

}
