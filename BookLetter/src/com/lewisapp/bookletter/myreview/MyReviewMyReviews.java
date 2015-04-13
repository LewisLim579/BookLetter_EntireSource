package com.lewisapp.bookletter.myreview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.readreview.ShowReviewActivity;
import com.lewisapp.bookletter.request_list.ResultRequest;
import com.lewisapp.bookletter.review_list.ResultReview;
import com.lewisapp.bookletter.review_list.ReviewAdapter;
import com.lewisapp.bookletter.review_list.ReviewItemData;
import com.lewisapp.bookletter.DataManager;

public class MyReviewMyReviews extends Fragment {

	ListView listViewMyReview;
	ReviewAdapter mAdapter;
	String orderMode;
	RadioGroup radioGroupOrder;

	PullToRefreshListView refreshView;
	Handler mHandler = new Handler();
	long startTime;
	int start;
	public static final int REQUESTCODE_MYREVIEWMODIFY=108;
	public static final int ONCESHOWDISPLAY_TOTALMYREIVIEW = 10;
	public static final String ORDER_RECENT = "revNum";
	public static final String ORDER_LIKED = "likeCnt";

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_my_review_my_reviews,
				container, false);
		start = 1;
		radioGroupOrder = (RadioGroup) view
				.findViewById(R.id.radioGroup_fororder);

		mAdapter = new ReviewAdapter(getActivity());

		refreshView = (PullToRefreshListView) view
				.findViewById(R.id.listView_myreviews);
		listViewMyReview = refreshView.getRefreshableView();
		refreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> rView) {
				// TODO Auto-generated method stub

				int forcheck = (start * ONCESHOWDISPLAY_TOTALMYREIVIEW);
				start++;
				int total = mAdapter.getTotalCount();
				if (forcheck <= total) {
					//
					startTime = SystemClock.uptimeMillis();
					NetworkManager.getInstnace().getMyReview(getActivity(),
							orderMode, start,
							new OnResultListener<ResultReview>() {

								@Override
								public void onSuccess(
										final ResultReview dataFromServer) {
									// TODO Auto-generated method stub
									long currentTime = SystemClock
											.uptimeMillis();
									int delta = (int) (currentTime - startTime);
									if (delta < 500) {
										mHandler.postDelayed(new Runnable() {
											public void run() {
												mAdapter.addAll(dataFromServer.reviews.lists);
												refreshView.onRefreshComplete();
											}
										}, 500 - delta);
									} else {
										mAdapter.addAll(dataFromServer.reviews.lists);
										refreshView.onRefreshComplete();
									}

								}

								@Override
								public void onFail(int code) {
									// TODO Auto-generated method stub

								}
							});
				} else {
					Toast.makeText(getActivity(), "불러올 목록이 없습니다",
							Toast.LENGTH_SHORT).show();
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							refreshView.onRefreshComplete();
						}
					});
				}
			}
		});
		listViewMyReview.setAdapter(mAdapter);

		listViewMyReview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ReviewItemData data = (ReviewItemData) listViewMyReview
						.getItemAtPosition(position);
				Intent intent = new Intent(getActivity(),
						ShowReviewActivity.class);
				intent.putExtra("revNum", data.revNum);
				startActivityForResult(intent, REQUESTCODE_MYREVIEWMODIFY);
				getActivity().overridePendingTransition(0, 0);
			}
		});
		radioGroupOrder
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

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
		orderMode = ORDER_RECENT;
//		initData();

		return view;
	}

	void initData() {
		NetworkManager.getInstnace().getMyReview(getActivity(), orderMode, 1,
				new OnResultListener<ResultReview>() {

					@Override
					public void onSuccess(ResultReview reviewItem) {
						// TODO Auto-generated method stub
						if(reviewItem!=null){
						mAdapter.clear();
						mAdapter.setTotalCount(Integer.parseInt(reviewItem.totalCount));
						mAdapter.addAll(reviewItem.reviews.lists);
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
		if (requestCode == REQUESTCODE_MYREVIEWMODIFY
				&& resultCode == Activity.RESULT_OK) {
			initData();
		}
	}
}
