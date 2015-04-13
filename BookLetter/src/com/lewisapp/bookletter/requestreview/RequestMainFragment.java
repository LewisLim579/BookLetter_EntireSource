package com.lewisapp.bookletter.requestreview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.dialog.NeedLoginMyPageFragment;
import com.lewisapp.bookletter.request_list.RequestAdapter;
import com.lewisapp.bookletter.request_list.RequestItemData;
import com.lewisapp.bookletter.request_list.ResultRequest;
import com.lewisapp.bookletter.writereview.WriteReviewActivity;

public class RequestMainFragment extends Fragment {
	PullToRefreshListView refreshView;
	Handler mHandler = new Handler();
	long startTime;
	int start;
	ListView listView_request;
	RequestAdapter reqAdapter;
	public static final int REQUEST_CHECKCODE = 0;
	public static final int REQUEST_SOMETHINGHAPPEN = 5;
	public static final int ONCESHOWDISPLAY_TOTALREQUEST = 10;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_request_main, container,
				false);
		start = 1;
		refreshView = (PullToRefreshListView) view
				.findViewById(R.id.listView_requestlist);
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
					NetworkManager.getInstnace().getTotalRequest(getActivity(),
							start, new OnResultListener<ResultRequest>() {

								@Override
								public void onSuccess(
										final ResultRequest dataFromServer) {
									// TODO Auto-generated method stub
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
					Toast.makeText(getActivity(), "no more item",
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

		reqAdapter = new RequestAdapter(getActivity());
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
				startActivityForResult(tmpintent, REQUEST_SOMETHINGHAPPEN);
				getActivity().overridePendingTransition(0, 0);
			}
		});

		initData();
		return view;
	}

	private void initData() {

		NetworkManager.getInstnace().getTotalRequest(getActivity(), 1,
				new OnResultListener<ResultRequest>() {

					@Override
					public void onSuccess(ResultRequest requestItem) {
						// TODO Auto-generated method stub
						reqAdapter.clear();
						reqAdapter.setTotalCount(Integer
								.parseInt(requestItem.totalCount));
						reqAdapter.addAll(requestItem.reqreviews.lists);

					}

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getActionBar().setTitle("책 추천요청");
		initData();

	}

	ActionBar actionBar;

	public ActionBar getActionBar() {
		if (actionBar == null) {
			actionBar = ((ActionBarActivity) getActivity())
					.getSupportActionBar();
		}
		return actionBar;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		// super.onCreateOptionsMenu(menu, inflater);

		inflater.inflate(R.menu.request_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.item_write) {

			if (MyUserManager.getInstance().isLogin()) {
				Intent intent = new Intent(getActivity(),
						RequestWriteActivity.class);
				startActivityForResult(intent, REQUEST_CHECKCODE);
				return true;
			} else {
				NeedLoginMyPageFragment dialog = new NeedLoginMyPageFragment();
				dialog.show(getChildFragmentManager(), "로그인");

			}

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CHECKCODE
				&& resultCode == Activity.RESULT_OK) {
			initData();
		} else if (requestCode == REQUEST_SOMETHINGHAPPEN
				&& resultCode == Activity.RESULT_OK) {
			initData();
		}
	}

}
