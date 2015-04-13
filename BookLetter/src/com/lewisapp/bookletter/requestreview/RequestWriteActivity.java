package com.lewisapp.bookletter.requestreview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lewisapp.bookletter.BasicResult;
import com.lewisapp.bookletter.FontManager;
import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class RequestWriteActivity extends ActionBarActivity {
	ImageView userImg;
	TextView userNick;
	EditText editText_requestTitle;
	EditText editText_requestContent;
	String reqNum;
	ImageLoader mLoader;
	boolean modifyMode = false;
	Button button_requestwrite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_write);
		getSupportActionBar().setTitle("리뷰요청하기");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mLoader = ImageLoader.getInstance();
		userImg = (ImageView) findViewById(R.id.imageView_userImg);
		userNick = (TextView) findViewById(R.id.textView_userName);
		mLoader.displayImage(
				MyUserManager.getInstance().getMyUserData().userImg, userImg);
		userNick.setText(MyUserManager.getInstance().getMyUserData().userNick);
		editText_requestTitle = (EditText) findViewById(R.id.editText_requesttitle);
		editText_requestContent = (EditText) findViewById(R.id.editText_requestcontent);
		button_requestwrite = (Button) findViewById(R.id.button_upload_answer);
		reqNum = getIntent().getStringExtra("reqNum");
		if (reqNum != null) {
			modifyMode = true;
			getSupportActionBar().setTitle("수정하기 ");
			button_requestwrite.setText("수정 완료");
			NetworkManager.getInstnace().modifyReqeust(
					RequestWriteActivity.this, reqNum,
					new OnResultListener<RequestModifyResult>() {
						@Override
						public void onSuccess(RequestModifyResult dataFromServer) {
							// TODO Auto-generated method stub
							if (dataFromServer.success.equals("1")) {
								editText_requestTitle
										.setText(dataFromServer.reqreview.reqTitle);
								editText_requestContent
										.setText(dataFromServer.reqreview.reqContent);

							}

						}

						@Override
						public void onFail(int code) {
							// TODO Auto-generated method stub

						}
					});
		}

		button_requestwrite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (modifyMode) {
					String title = editText_requestTitle.getText()
							.toString();
					String content = editText_requestContent.getText()
							.toString();

					if (title.equals("")) {
						Toast.makeText(RequestWriteActivity.this,
								"제목을 입력해 주세요", 0).show();
					} else if (content.equals("")) {
						Toast.makeText(RequestWriteActivity.this,
								"내용을 입력해 주세요", 0).show();
					} else {

				
						NetworkManager.getInstnace().modifySaveReqeust(
								RequestWriteActivity.this, reqNum, title,
								content, new OnResultListener<BasicResult>() {

									@Override
									public void onSuccess(
											BasicResult dataFromServer) {
										// TODO Auto-generated method stub
										if (dataFromServer.success.equals("1")) {
											Intent data = new Intent();
											setResult(Activity.RESULT_OK, data);
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

				}

				else {
					// TODO Auto-generated method stub
					String title = editText_requestTitle.getText().toString();
					String content = editText_requestContent.getText()
							.toString();

					if (title.equals("")) {
						Toast.makeText(RequestWriteActivity.this,
								"제목을 입력해 주세요", 0).show();
					} else if (content.equals("")) {
						Toast.makeText(RequestWriteActivity.this,
								"내용을 입력해 주세요", 0).show();
					} else {

						NetworkManager.getInstnace().WriteRequest(
								RequestWriteActivity.this, title, content,
								new OnResultListener<RequestResult>() {

									@Override
									public void onSuccess(
											RequestResult dataFromServer) {

										// TODO Auto-generated method stub
										if (dataFromServer.message.equals("OK")) {
											Intent data = new Intent();
											setResult(Activity.RESULT_OK, data);
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

				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.request_write, menu);
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
