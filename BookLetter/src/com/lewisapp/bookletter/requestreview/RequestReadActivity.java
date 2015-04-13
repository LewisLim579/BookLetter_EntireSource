package com.lewisapp.bookletter.requestreview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lewisapp.bookletter.BasicResult;
import com.lewisapp.bookletter.DataManager;
import com.lewisapp.bookletter.FontManager;
import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.dialog.DeleteReviewDialogFragmentyet;
import com.lewisapp.bookletter.dialog.DeleteReviewDialogFragmentyet.OnReviewDeleteClickListener;
import com.lewisapp.bookletter.dialog.NeedLoginMyPageFragment;
import com.lewisapp.bookletter.otheruserreviewlist.OtherReviewListActivity;
import com.lewisapp.bookletter.readreview.ShowReviewActivity;
import com.lewisapp.bookletter.review_list.ReviewAdapter;
import com.lewisapp.bookletter.review_list.ReviewItemData;
import com.lewisapp.bookletter.writereview.WriteReviewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class RequestReadActivity extends ActionBarActivity {
	ListView listView_requestAnswerReview;
	ReviewAdapter reqAnswerReviewAdapter;

	TextView textView_reqtitle;
	TextView textView_reqcontent;
	TextView textView_username;
	TextView textView_date;
	ImageView imageView_userimg;
	ImageLoader mLoader;

	LinearLayout layoutGoother;
	ImageButton button_writeAnswerReview;
	LinearLayout layout_own_buttons;

	ImageButton button_modify;
	ImageButton button_delete;
	RequestDetailData data;
	String reqNum;

	public static final int REQUEST_ANSWERCODE = 2;
	public static final int REQUEST_SOMETHINGHAPPENCODE = 3;
	public static final int REQUEST_MODIFY = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_read);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		reqNum = getIntent().getStringExtra("reqNum");
		mLoader = ImageLoader.getInstance();
		listView_requestAnswerReview = (ListView) findViewById(R.id.listView_request_answer);
		reqAnswerReviewAdapter = new ReviewAdapter(RequestReadActivity.this);

		View reqContentHeaderView = getLayoutInflater().inflate(
				R.layout.headerview_requestcontent, null);
		View secondHeaderView = getLayoutInflater().inflate(
				R.layout.headerview_requestanswerstart, null);

		secondHeaderView.setClickable(false);
		layoutGoother = (LinearLayout) reqContentHeaderView
				.findViewById(R.id.layoutgoother);
		layoutGoother.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!data.userNum.equals(MyUserManager.getInstance()
						.getMyUserData().userNum)) {
					Intent intent = new Intent(RequestReadActivity.this,
							OtherReviewListActivity.class);
					intent.putExtra("userNum", data.userNum);
					startActivity(intent);
					overridePendingTransition(0, 0);
				}	
			}
		});
		imageView_userimg = (ImageView) reqContentHeaderView
				.findViewById(R.id.imageView_userimg);

//		imageView_userimg.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (DataManager.getInstance().myMode) {
//					layout_own_buttons.setVisibility(View.VISIBLE);
//					button_writeAnswerReview.setVisibility(View.GONE);
//					DataManager.getInstance().myMode = false;
//				} else {
//					layout_own_buttons.setVisibility(View.GONE);
//					button_writeAnswerReview.setVisibility(View.VISIBLE);
//					DataManager.getInstance().myMode = true;
//				}
//			}
//		});
		textView_reqtitle = (TextView) reqContentHeaderView
				.findViewById(R.id.textView_reqtitle);
		textView_reqcontent = (TextView) reqContentHeaderView
				.findViewById(R.id.textView_reqcontent);
		textView_username = (TextView) reqContentHeaderView
				.findViewById(R.id.textView_username);
		imageView_userimg = (ImageView) reqContentHeaderView
				.findViewById(R.id.imageView_userimg);
		button_writeAnswerReview = (ImageButton) reqContentHeaderView
				.findViewById(R.id.button_writeReviewAnswer);

		textView_date = (TextView) reqContentHeaderView
				.findViewById(R.id.textView_revdate);
		layout_own_buttons = (LinearLayout) reqContentHeaderView
				.findViewById(R.id.layout_ownrequestbuttons);

		button_modify = (ImageButton) reqContentHeaderView
				.findViewById(R.id.button_requestmodify);

		button_delete = (ImageButton) reqContentHeaderView
				.findViewById(R.id.button_requestdelete);

		layout_own_buttons.setVisibility(View.GONE);
		button_writeAnswerReview.setVisibility(View.GONE);

		listView_requestAnswerReview.addHeaderView(reqContentHeaderView);

		listView_requestAnswerReview.addHeaderView(secondHeaderView,reqAnswerReviewAdapter,false);
		listView_requestAnswerReview.setAdapter(reqAnswerReviewAdapter);

		initData();

		listView_requestAnswerReview
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						ReviewItemData data = (ReviewItemData) listView_requestAnswerReview
								.getItemAtPosition(position);
						Intent tmpintent = new Intent(RequestReadActivity.this,
								ShowReviewActivity.class);
						tmpintent.putExtra("revNum", data.revNum);
						startActivity(tmpintent);
						overridePendingTransition(0, 0);
					}
				});

		button_writeAnswerReview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!MyUserManager.getInstance().isLogin()) {
					NeedLoginMyPageFragment dialog = new NeedLoginMyPageFragment();
					dialog.show(getSupportFragmentManager(), "로그인");

				} else {
					Intent goWriteIntent = new Intent(RequestReadActivity.this,
							WriteReviewActivity.class);
					goWriteIntent.putExtra("reqNum", reqNum);
					startActivityForResult(goWriteIntent, REQUEST_ANSWERCODE);
					overridePendingTransition(0, 0);
				}
			}
		});

		button_modify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RequestReadActivity.this,
						RequestWriteActivity.class);
				intent.putExtra("reqNum", reqNum);
				startActivityForResult(intent, REQUEST_MODIFY);
				overridePendingTransition(0, 0);
			}
		});

		button_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DeleteReviewDialogFragmentyet dialog = new DeleteReviewDialogFragmentyet();
				dialog.show(getSupportFragmentManager(), "리뷰삭제");
				dialog.setOnReviewdeleteListener(new OnReviewDeleteClickListener() {

					@Override
					public void onReviewDeleteClick() {
						// TODO Auto-generated method stub
						NetworkManager.getInstnace().deleteRequest(
								RequestReadActivity.this, reqNum,
								new OnResultListener<BasicResult>() {

									@Override
									public void onSuccess(
											BasicResult dataFromServer) {
										// TODO Auto-generated method stub
										if (dataFromServer.success.equals("1")) {
											setResult(Activity.RESULT_OK);
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
				});
				// TODO Auto-generated method stub

			}
		});

	}

	private void initData() {
		NetworkManager.getInstnace().getDetailRequest(RequestReadActivity.this,
				reqNum, new OnResultListener<RequestDetailResult>() {

					@Override
					public void onSuccess(RequestDetailResult dataFromServer) {
						// TODO Auto-generated method stub
						//

						data = dataFromServer.reqreview;
						if (MyUserManager.getInstance().isLogin()) {
							if (MyUserManager.getInstance().getMyUserData().userNum == data.userNum) {
								getSupportActionBar().setTitle(
										dataFromServer.reqreview.userNick
												+ "님의 요청글");
								layout_own_buttons.setVisibility(View.VISIBLE);
							} else {
								getSupportActionBar().setTitle(
										dataFromServer.reqreview.userNick
												+ "님에게 책을 추천해주세요");
								button_writeAnswerReview
										.setVisibility(View.VISIBLE);
							}
						} else {
							button_writeAnswerReview
									.setVisibility(View.VISIBLE);
							layout_own_buttons.setVisibility(View.GONE);
						}
						textView_reqcontent.setText(data.reqContent);
						textView_username.setText(data.userNick);
						textView_reqtitle.setText(data.reqTitle);
						textView_date.setText(data.reqDate);
						mLoader.displayImage(data.userImg, imageView_userimg);

						reqAnswerReviewAdapter.clear();
						for (ReviewItemData revdata : dataFromServer.comments.lists) {
							reqAnswerReviewAdapter.add(revdata);
						}

					}

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_ANSWERCODE
				&& resultCode == Activity.RESULT_OK) {
			initData();
		} else if (requestCode == REQUEST_MODIFY
				&& resultCode == Activity.RESULT_OK) {
			setResult(Activity.RESULT_OK);
			initData();
		}
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
