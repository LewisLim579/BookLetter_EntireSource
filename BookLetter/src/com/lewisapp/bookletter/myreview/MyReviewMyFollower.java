package com.lewisapp.bookletter.myreview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.dialog.DeleteFollowerDialogFragmentyet;
import com.lewisapp.bookletter.dialog.DeleteFollowerDialogFragmentyet.OnFollowerDeleteClickListener;
import com.lewisapp.bookletter.myfollower_list.FollowerItemData;
import com.lewisapp.bookletter.myfollower_list.FollowerResult;
import com.lewisapp.bookletter.myfollower_list.FollowsAdapter;
import com.lewisapp.bookletter.myfollower_list.FollowsAdapter.OnAdapterItemClickListener;
import com.lewisapp.bookletter.otheruserreviewlist.OtherReviewListActivity;
import com.lewisapp.bookletter.readreview.FollowStateResult;

public class MyReviewMyFollower extends Fragment {

	public static final String ORDER_REVCNT = "revCnt";
	public static final String ORDER_FOLCNT = "folCnt";

	FollowsAdapter followerAdapter;
	ListView listViewMyFollower;
	RadioGroup orderRadio;

	public String orderMode;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_my_review_my_follower,
				container, false);

		listViewMyFollower = (ListView) view
				.findViewById(R.id.listView_follower);
		orderRadio = (RadioGroup) view.findViewById(R.id.radioGroup_myfollower);
		followerAdapter = new FollowsAdapter(getActivity());

		followerAdapter
				.setOnAdapterItemClickListener(new OnAdapterItemClickListener() {

					@Override
					public void onAdapterItemClick(FollowsAdapter adapter,
							View view, FollowerItemData item) {
						// TODO Auto-generated method stub

						final String userNum2 = item.userNum2;
						DeleteFollowerDialogFragmentyet dialog = new DeleteFollowerDialogFragmentyet();
						dialog.show(getChildFragmentManager(), "팔로워삭제");
						dialog.setOnFollowerdeleteListener(new OnFollowerDeleteClickListener() {

							@Override
							public void onFollowerDeleteClick() {
								// TODO Auto-generated method stub
								NetworkManager
										.getInstnace()
										.clickFollow(
												getActivity(),
												userNum2,
												"1",
												new OnResultListener<FollowStateResult>() {

													@Override
													public void onSuccess(

															FollowStateResult dataFromServer) {
														// TODO
														// Auto-generated
														// method stub
														initData();

													}

													@Override
													public void onFail(int code) {
														// TODO
														// Auto-generated
														// method stub

													}
												});
							}

							@Override
							public void onFollowerDeleteCancleClick() {
								// TODO Auto-generated method stub

							}
						});

					}
				});
		listViewMyFollower.setAdapter(followerAdapter);
		listViewMyFollower.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				FollowerItemData data = (FollowerItemData) listViewMyFollower
						.getItemAtPosition(position);
				Intent intent = new Intent(getActivity(),
						OtherReviewListActivity.class);
				intent.putExtra("userNum", data.userNum2);
				startActivity(intent);
				getActivity().overridePendingTransition(0, 0);

			}
		});

		orderRadio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.radio_myfollower_revcnt) {
					orderMode = ORDER_REVCNT;
					initData();
				} else if (checkedId == R.id.radio_myfollower_folcnt) {
					orderMode = ORDER_FOLCNT;
					initData();
				}

			}
		});

		orderMode = ORDER_REVCNT;
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}

	void initData() {
		NetworkManager.getInstnace().getMyFollow(getActivity(), orderMode,
				new OnResultListener<FollowerResult>() {

					@Override
					public void onSuccess(FollowerResult followItem) {
						// TODO Auto-generated method stub
						if (followItem != null) {
							followerAdapter.clear();

							followerAdapter.addAll(followItem.follows.lists);
						}
					}

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub

					}
				});
	}

}
