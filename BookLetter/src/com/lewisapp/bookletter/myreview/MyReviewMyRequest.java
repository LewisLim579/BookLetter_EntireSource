package com.lewisapp.bookletter.myreview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.request_list.RequestAdapter;
import com.lewisapp.bookletter.request_list.RequestItemData;
import com.lewisapp.bookletter.request_list.ResultRequest;
import com.lewisapp.bookletter.requestreview.RequestReadActivity;

public class MyReviewMyRequest extends Fragment {
	PullToRefreshListView refreshView;
	Handler mHandler = new Handler();
	long startTime;
	int start;
	public static final int ONCESHOWDISPLAY_TOTALREQUEST = 10;

	ListView listView_request;
	RequestAdapter reqAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_my_review_my_request,
				container, false);
		start = 1;
		reqAdapter = new RequestAdapter(getActivity());

		refreshView = (PullToRefreshListView) view
				.findViewById(R.id.listView_myrequest);
		listView_request = refreshView.getRefreshableView();

		refreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> rView) {
				// TODO Auto-generated method stub

				int forcheck = (start * ONCESHOWDISPLAY_TOTALREQUEST);
				start++;
				int total = reqAdapter.getTotalCount();
				if (forcheck <= total) {
					//
					startTime = SystemClock.uptimeMillis();

					NetworkManager.getInstnace().getMyRequestList(
							getActivity(), start,
							new OnResultListener<ResultRequest>() {

								@Override
								public void onSuccess(
										final ResultRequest dataFromServer) {
									// TODO Auto-generated method stub
									long currentTime = SystemClock
											.uptimeMillis();
									int delta = (int) (currentTime - startTime);
									if (delta < 500) {
										mHandler.postDelayed(new Runnable() {
											public void run() {
												reqAdapter
														.addAll(dataFromServer.reqreviews.lists);
												refreshView.onRefreshComplete();
											}
										}, 500 - delta);
									} else {
										reqAdapter
												.addAll(dataFromServer.reqreviews.lists);
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

		listView_request.setAdapter(reqAdapter);

		listView_request.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				RequestItemData data = (RequestItemData) listView_request
						.getItemAtPosition(position);
				Intent tmpintent = new Intent(getActivity(),
						RequestReadActivity.class);
				tmpintent.putExtra("reqNum", data.reqNum);
				startActivity(tmpintent);
				getActivity().overridePendingTransition(0, 0);
			}
		});
		return view;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}
	void initData() {

		NetworkManager.getInstnace().getMyRequestList(getActivity(), 1,
				new OnResultListener<ResultRequest>() {

					@Override
					public void onSuccess(ResultRequest dataFromServer) {
						// TODO Auto-generated method stub
						if(dataFromServer!=null){
						reqAdapter.clear();
						reqAdapter.setTotalCount(Integer
								.parseInt(dataFromServer.totalCount)); // 임시
						reqAdapter.addAll(dataFromServer.reqreviews.lists);
						}
					}

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub

					}
				});
	}
}
