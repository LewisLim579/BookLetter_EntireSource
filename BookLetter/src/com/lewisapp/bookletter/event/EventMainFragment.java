package com.lewisapp.bookletter.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;

public class EventMainFragment extends Fragment {

	ListView lv;
	EventAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_event_main, container,
				false);
		mAdapter = new EventAdapter(getActivity());
		lv = (ListView) view.findViewById(R.id.listView_event);
		lv.setAdapter(mAdapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				EventItemData tmpData = (EventItemData) mAdapter
						.getItem(position);
				Intent intent = new Intent(getActivity(), EventActivity.class);
				intent.putExtra("notNum", tmpData.notNum);
				startActivity(intent);
				getActivity().overridePendingTransition(0, 0);
			}
		});
		init();
		return view;
	}

	private void init() {
		NetworkManager.getInstnace().getNoticeList(getActivity(),
				new OnResultListener<EventResult>() {

					@Override
					public void onSuccess(EventResult dataFromServer) {
						// TODO Auto-generated method stub

						if (dataFromServer != null) {
							mAdapter.clear();

							mAdapter.addAll(dataFromServer.notices.lists);
						}
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
		getActionBar().setTitle("이벤트/공지");
	}

	ActionBar actionBar;

	public ActionBar getActionBar() {
		if (actionBar == null) {
			actionBar = ((ActionBarActivity) getActivity())
					.getSupportActionBar();
		}
		return actionBar;
	}
}
