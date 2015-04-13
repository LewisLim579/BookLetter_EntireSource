package com.lewisapp.bookletter.event;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;

public class EventActivity extends ActionBarActivity {

	TextView tv_title;
	TextView tv_date;
	TextView tv_content;
	ImageView img_eventImg;
	TextView tv_email;
	String notNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		tv_title = (TextView) findViewById(R.id.textView_eventtitle);
		tv_date = (TextView) findViewById(R.id.textView_eventdate);
		tv_content = (TextView) findViewById(R.id.textView_eventcontent);

		// tv_content.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Uri uri=Uri.parse("mailto:jiyoung45god@gmail.com");
		// Intent i=new Intent(Intent.ACTION_SENDTO,uri);
		// startActivity(i);
		// }
		// });
		notNum = getIntent().getStringExtra("notNum");
		init();

	}

	private void init() {
		NetworkManager.getInstnace().getNoticeDetail(EventActivity.this,
				notNum, new OnResultListener<EventDetailResult>() {

					@Override
					public void onSuccess(EventDetailResult dataFromServer) {
						// TODO Auto-generated method stub
						if (dataFromServer.message.equals("OK")) {
							tv_title.setText(dataFromServer.notice.notTitle);
							tv_date.setText(dataFromServer.notice.notDate);

							tv_content.setText(Html
									.fromHtml(dataFromServer.notice.notContent));
						}
					}

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub

					}
				});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(0, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event, menu);
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
}
