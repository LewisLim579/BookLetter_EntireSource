package com.lewisapp.bookletter.myreview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

import com.lewisapp.bookletter.R;

public class MyReviewMainFragment extends Fragment {

	ViewPager pager;
	TabHost tabHost;
	TabsAdapter mAdapter;

	ImageButton tabButton1;

	ImageButton tabButton2;

	ImageButton tabButton3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_my_review_main,
				container, false);

		tabButton1 = new ImageButton(getActivity());
		tabButton2 = new ImageButton(getActivity());
		tabButton3 = new ImageButton(getActivity());
		tabButton1.setBackground(null);
		tabButton2.setBackground(null);
		tabButton3.setBackground(null);
		tabButton1.setImageResource(R.drawable.tabselector_mypage_review);
		tabButton2.setImageResource(R.drawable.tabselector_mypage_follower);
		tabButton3.setImageResource(R.drawable.tabselector_mypage_request);

		tabHost = (TabHost) view.findViewById(android.R.id.tabhost);

		tabHost.setup();

		pager = (ViewPager) view.findViewById(R.id.pager);
		mAdapter = new TabsAdapter(getActivity(), getChildFragmentManager(),
				tabHost, pager);
		mAdapter.addTab(tabHost.newTabSpec("tab1").setIndicator(tabButton1),
				MyReviewMyReviews.class, null);
		mAdapter.addTab(tabHost.newTabSpec("tab2").setIndicator(tabButton2),
				MyReviewMyFollower.class, null);
		mAdapter.addTab(tabHost.newTabSpec("tab3").setIndicator(tabButton3),
				MyReviewMyRequest.class, null);
		// getResources().getDrawable(R.drawable.tabselector_mypage_review)

		pager.setCurrentItem(0);

		if (savedInstanceState != null) {
			mAdapter.onRestoreInstanceState(savedInstanceState);
			String tag = savedInstanceState.getString("tabTag");
			tabHost.setCurrentTabByTag(tag);
		}

		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getActionBar().setTitle("마이페이지");
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		mAdapter.onSaveInstanceState(outState);
		String tag = tabHost.getCurrentTabTag();
		outState.putString("tabTag", tag);
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
