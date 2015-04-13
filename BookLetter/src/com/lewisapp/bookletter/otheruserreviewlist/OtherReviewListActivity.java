package com.lewisapp.bookletter.otheruserreviewlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lewisapp.bookletter.FontManager;
import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.dialog.DeleteFollowerDialogFragmentyet;
import com.lewisapp.bookletter.dialog.DeleteFollowerDialogFragmentyet.OnFollowerDeleteClickListener;
import com.lewisapp.bookletter.readreview.FollowStateResult;
import com.lewisapp.bookletter.readreview.ShowReviewActivity;
import com.lewisapp.bookletter.review_list.ResultReview;
import com.lewisapp.bookletter.review_list.ReviewAdapter;
import com.lewisapp.bookletter.review_list.ReviewItemData;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OtherReviewListActivity extends ActionBarActivity {

	ReviewAdapter folReviewAdapter;
	ListView listViewFollowerReview;

	ImageView imageViewUserImg;
	TextView textViewUserName;
	TextView textViewUserReviewCount;
	TextView textViewFollowerCount;
	ImageLoader mLoader;
	String userNum;
	CheckBox checkbox_follower;
	RadioGroup radioGroup;
	String isFollower;
	User user;

	LinearLayout layout_back;
	String orderMode;

	long startTime;
	PullToRefreshListView refreshView;
	Handler mHandler = new Handler();
	int start;

	public static final String ORDER_RECENT = "revNum";
	public static final String ORDER_LIKED = "likeCnt";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		start = 1;
		mLoader = ImageLoader.getInstance();
		userNum = getIntent().getStringExtra("userNum");
		setContentView(R.layout.activity_follower_review_list);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		folReviewAdapter = new ReviewAdapter(OtherReviewListActivity.this);

		orderMode = ORDER_RECENT;
		View headerViewFollowerInfo = getLayoutInflater().inflate(
				R.layout.headerview_followinfo, null);

		View headerViewOrderSecond = getLayoutInflater().inflate(
				R.layout.headerview_orderheader, null);
		layout_back = (LinearLayout) headerViewFollowerInfo
				.findViewById(R.id.layout_otherback);
		checkbox_follower = (CheckBox) headerViewFollowerInfo
				.findViewById(R.id.checkBox_follower);
		refreshView = (PullToRefreshListView) findViewById(R.id.listView_myfollower_review);
		refreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> rView) {
				// TODO Auto-generated method stub
				int forcheck = (start * 10);
				start++;
				int total = folReviewAdapter.getTotalCount();
				if (forcheck <= total) {

					startTime = SystemClock.uptimeMillis();
					NetworkManager.getInstnace().getOtherReviewList(
							OtherReviewListActivity.this, userNum, orderMode,
							start,
							new OnResultListener<ResultOtherUseReviews>() {

								@Override
								public void onSuccess(
										final ResultOtherUseReviews refreshData) {
									// TODO Auto-generated method stub
									// TODO Auto-generated method stub
									long currentTime = SystemClock
											.uptimeMillis();
									int delta = (int) (currentTime - startTime);
									if (delta < 500) {
										mHandler.postDelayed(new Runnable() {
											public void run() {
												folReviewAdapter
														.addAll(refreshData.reviews.lists);
												refreshView.onRefreshComplete();
											}
										}, 500 - delta);
									} else {
										folReviewAdapter
												.addAll(refreshData.reviews.lists);
										refreshView.onRefreshComplete();
									}
								}

								@Override
								public void onFail(int code) {
									// TODO Auto-generated method stub

								}
							});
				} else {
					Toast.makeText(OtherReviewListActivity.this,
							"불러올 목록이 없습니다", Toast.LENGTH_SHORT).show();
					OtherReviewListActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							refreshView.onRefreshComplete();
						}
					});
				}
			}
		});
		listViewFollowerReview = refreshView.getRefreshableView();
		checkbox_follower
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(
							final CompoundButton buttonView, boolean isChecked) {
						// TODO Auto-generated method stub
						if (MyUserManager.getInstance().isLogin()) {
							// TODO Auto-generated method stub
							if (buttonView.getId() == R.id.checkBox_follower) {
								if (isChecked) {

									if (!isFollower.equals("1")) {
										String userNum2 = userNum;

										NetworkManager
												.getInstnace()
												.clickFollow(
														OtherReviewListActivity.this,
														userNum2,
														isFollower,
														new OnResultListener<FollowStateResult>() {

															@Override
															public void onSuccess(

																	FollowStateResult dataFromServer) {
																// TODO
																// Auto-generated
																// method stub

																isFollower = dataFromServer.follow.followState;

															}

															@Override
															public void onFail(
																	int code) {
																// TODO
																// Auto-generated
																// method stub

															}
														});

									}
								} else {

									DeleteFollowerDialogFragmentyet dialog = new DeleteFollowerDialogFragmentyet();
									dialog.show(getSupportFragmentManager(),
											"팔로우삭제");
									dialog.setOnFollowerdeleteListener(new OnFollowerDeleteClickListener() {

										@Override
										public void onFollowerDeleteClick() {
											// TODO Auto-generated method stub
											if (!isFollower.equals("0")) {
												String userNum2 = userNum;

												NetworkManager
														.getInstnace()
														.clickFollow(
																OtherReviewListActivity.this,
																userNum2,
																isFollower,
																new OnResultListener<FollowStateResult>() {

																	@Override
																	public void onSuccess(
																			FollowStateResult dataFromServer) {
																		// TODO
																		// Auto-generated
																		// method
																		// stub

																		isFollower = dataFromServer.follow.followState;
																		buttonView
																				.setChecked(false);
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
											}
										}

										@Override
										public void onFollowerDeleteCancleClick() {
											// TODO Auto-generated method stub
											checkbox_follower.setChecked(true);
										}
									});
								}
							}
						}
					}
				});

		radioGroup = (RadioGroup) headerViewOrderSecond
				.findViewById(R.id.radioGroup_fororder);

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.radio_orderrecent) {
					orderMode = ORDER_RECENT;
					initData();
				} else if (checkedId == R.id.radio_orderlike) {
					orderMode = ORDER_LIKED;
					initData();
				}

			}
		});
		textViewUserName = (TextView) headerViewFollowerInfo
				.findViewById(R.id.textView_userName);
		textViewUserReviewCount = (TextView) headerViewFollowerInfo
				.findViewById(R.id.textView_reviewcount);
		textViewFollowerCount = (TextView) headerViewFollowerInfo
				.findViewById(R.id.textView_followercount);
		imageViewUserImg = (ImageView) headerViewFollowerInfo
				.findViewById(R.id.imageView_otheruserimg);

		listViewFollowerReview.addHeaderView(headerViewFollowerInfo);

		listViewFollowerReview.addHeaderView(headerViewOrderSecond);
		listViewFollowerReview.setAdapter(folReviewAdapter);

		initData();

		listViewFollowerReview
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						ReviewItemData data = (ReviewItemData) listViewFollowerReview
								.getItemAtPosition(position);
						Intent intent = new Intent(
								OtherReviewListActivity.this,
								ShowReviewActivity.class);
						intent.putExtra("revNum", data.revNum);
						startActivity(intent);
						overridePendingTransition(0, 0);
					}
				});

	}

	void initData() {

		NetworkManager.getInstnace().getOtherReviewList(
				OtherReviewListActivity.this, userNum, orderMode, 1,
				new OnResultListener<ResultOtherUseReviews>() {

					@Override
					public void onSuccess(ResultOtherUseReviews dataFromServer) {
						// TODO Auto-generated method stub
						getSupportActionBar().setTitle(dataFromServer.user.userNick+"님이 작성한 리뷰");
						user = dataFromServer.user;
						folReviewAdapter.clear();
						folReviewAdapter.addAll(dataFromServer.reviews.lists);

						String backindex = dataFromServer.user.userBackground;
						if (backindex.equals("0")) {
							layout_back.setBackgroundResource(R.drawable.back0);
						} else if (backindex.equals("1")) {
							layout_back.setBackgroundResource(R.drawable.back1);
						} else if (backindex.equals("2")) {
							layout_back.setBackgroundResource(R.drawable.back2);
						} else if (backindex.equals("3")) {
							layout_back.setBackgroundResource(R.drawable.back3);
						} else if (backindex.equals("4")) {
							layout_back.setBackgroundResource(R.drawable.back4);
						} else if (backindex.equals("5")) {
							layout_back.setBackgroundResource(R.drawable.back5);
						} else if (backindex.equals("6")) {
							layout_back.setBackgroundResource(R.drawable.back6);
						} else if (backindex.equals("7")) {
							layout_back.setBackgroundResource(R.drawable.back7);
						} else if (backindex.equals("8")) {
							layout_back.setBackgroundResource(R.drawable.back8);
						} else if (backindex.equals("9")) {
							layout_back.setBackgroundResource(R.drawable.back9);
						} else if (backindex.equals("10")) {
							layout_back
									.setBackgroundResource(R.drawable.back10);
						}

						// isFollower = dataFromServer.user.followState;
						textViewUserName.setText(dataFromServer.user.userNick);
						textViewUserReviewCount.setText("리뷰 수 "
								+ dataFromServer.user.revCnt);
						textViewFollowerCount.setText("팔로워 수 "
								+ dataFromServer.user.folCnt);
						folReviewAdapter.setTotalCount(Integer
								.parseInt(dataFromServer.totalCount)); // 임시
						mLoader.displayImage(dataFromServer.user.userImg,
								imageViewUserImg);
						isFollower = dataFromServer.user.followState;
						if (isFollower.equals("1")) {
							checkbox_follower.setChecked(true);
						} else if (isFollower.equals("0"))
							checkbox_follower.setChecked(false);
					}

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub

					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.follower_review, menu);
		return true;
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
}
