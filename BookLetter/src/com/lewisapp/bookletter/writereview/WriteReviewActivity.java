package com.lewisapp.bookletter.writereview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lewisapp.bookletter.FontManager;
import com.lewisapp.bookletter.R;

public class WriteReviewActivity extends ActionBarActivity {

	private static final String BOOKINFO_TAG = "tab_bookinfo";
	private static final String WRITE_TAG = "tab_write";

	WriteReviewSelectParentFragment bookFragment;
	WriteReviewFragment writeFragment;

	String reqNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_review);
		bookFragment = new WriteReviewSelectParentFragment();
		writeFragment = new WriteReviewFragment();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		reqNum = getIntent().getStringExtra("reqNum");

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container_write, bookFragment, BOOKINFO_TAG)
					.commit();
		}
	}

	public void goWriteReivew(Bundle bundle) {

		Bundle goWriteBundle = bundle;
		goWriteBundle.putString("reqNum", reqNum);
		writeFragment.setArguments(goWriteBundle);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container_write, writeFragment, WRITE_TAG)
				.commit();
	}

	public void goReselectBook() {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container_write, bookFragment, BOOKINFO_TAG)
				.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.write_review, menu);
		return true;
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
