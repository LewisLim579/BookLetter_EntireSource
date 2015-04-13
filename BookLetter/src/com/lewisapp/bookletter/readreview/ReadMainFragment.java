package com.lewisapp.bookletter.readreview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.dialog.NeedLoginMyPageFragment;
import com.lewisapp.bookletter.main.MainActivity;
import com.lewisapp.bookletter.review_list.ResultReview;
import com.lewisapp.bookletter.review_list.ReviewAdapter;
import com.lewisapp.bookletter.review_list.ReviewItemData;
import com.lewisapp.bookletter.writereview.WriteReviewActivity;

public class ReadMainFragment extends Fragment {

	ListView listView_review;

	ReviewAdapter mAdapter;
	public static String keyword;

	Button btn_search_book_writer;
	Button btn_search_keyword;
	RadioGroup radioGroup_Order;

	String searchMode;
	String orderMode;
	public static final String ORDER_RECENT = "revNum";
	public static final String ORDER_LIKED = "likeCnt";
	LinearLayout layout_buttons;
	Handler mHandler = new Handler();

	public static final int REQUESTCODE_SOMETHINGHAPPENREVIEW = 101;
	public static final int REQUESTCODE_WRITEREVIEW = 0;

	public static final String SEARCHMODE_TITLE_AUTOR = "0";
	public static final String SEARCHMODE_KEYWORD = "1";

	long startTime;
	PullToRefreshListView refreshView;
	int start;
	public static final int ONCESHOWDISPLAY_TOTALREVIEW = 10;

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
		View view = inflater.inflate(R.layout.fragment_read_main, container,
				false);

		start = 1;
		refreshView = (PullToRefreshListView) view
				.findViewById(R.id.listView_review);
		layout_buttons = (LinearLayout) view.findViewById(R.id.layout_buttons);

		// listView_review = (ListView) view.findViewById(R.id.listView_review);
		mAdapter = new ReviewAdapter(getActivity());

		btn_search_book_writer = (Button) view
				.findViewById(R.id.button_title_writer);
		btn_search_keyword = (Button) view.findViewById(R.id.button_keyword);
		radioGroup_Order = (RadioGroup) view
				.findViewById(R.id.radioGroup_order);

		radioGroup_Order
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (checkedId == R.id.radio_recent) {
							orderMode = ORDER_RECENT;
							getReviewData(keyword, ORDER_RECENT, 1, searchMode);
						} else if (checkedId == R.id.radio_liked) {
							orderMode = ORDER_LIKED;
							getReviewData(keyword, ORDER_LIKED, 1, searchMode);
						}

					}
				});

		Bundle bundle = getArguments();
		keyword = "";
		orderMode = ORDER_RECENT;
		if (bundle != null) {
			keyword = bundle.getString("keyword");
			searchMode = bundle.getString("order");

		} else {
			searchMode = SEARCHMODE_TITLE_AUTOR;

		}

		getReviewData(keyword, orderMode, 1, searchMode); // 초기세팅

		listView_review = refreshView.getRefreshableView();
		listView_review.setAdapter(mAdapter);

		listView_review.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ReviewItemData data = (ReviewItemData) listView_review
						.getItemAtPosition(position);

				Intent tmpintent = new Intent(getActivity(),
						ShowReviewActivity.class);
				tmpintent.putExtra("revNum", data.revNum);

				startActivityForResult(tmpintent,
						REQUESTCODE_SOMETHINGHAPPENREVIEW);
				getActivity().overridePendingTransition(0, 0);
			}
		});

		btn_search_book_writer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_search_book_writer.setSelected(true);
				btn_search_keyword.setSelected(false);
				searchMode = SEARCHMODE_TITLE_AUTOR;
			}
		});
		btn_search_keyword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_search_book_writer.setSelected(false);
				btn_search_keyword.setSelected(true);
				searchMode = SEARCHMODE_KEYWORD;

			}
		});
		btn_search_book_writer.setSelected(true);
		btn_search_keyword.setSelected(false);

		refreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> rView) {
				// TODO Auto-generated method stub

				int forcheck = (start * ONCESHOWDISPLAY_TOTALREVIEW);
				start++;
				int total = mAdapter.getTotalCount();
				if (forcheck <= total) {
//					Toast.makeText(
//							getActivity(),
//							"start" + start + "  수" + mAdapter.getCount()
//									+ "  total" + mAdapter.getTotalCount(), 0)
//							.show();
					startTime = SystemClock.uptimeMillis();
					NetworkManager.getInstnace().getReviewList(getActivity(),
							keyword, orderMode, start, searchMode,
							new OnResultListener<ResultReview>() {

								@Override
								public void onSuccess(
										final ResultReview refreshData) {

									// TODO Auto-generated method stub
									long currentTime = SystemClock
											.uptimeMillis();
									int delta = (int) (currentTime - startTime);
									if (delta < 500) {
										mHandler.postDelayed(new Runnable() {
											public void run() {
												mAdapter.addAll(refreshData.reviews.lists);
												refreshView.onRefreshComplete();
											}
										}, 500 - delta);
									} else {
										mAdapter.addAll(refreshData.reviews.lists);
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

		return view;
	}

	private void getReviewData(String keyword, String order, int page,
			String searchMode) { // 정렬하고
		// page수정해야된다.
		NetworkManager.getInstnace().getReviewList(getActivity(), keyword,
				order, page, searchMode, new OnResultListener<ResultReview>() {

					@Override
					public void onSuccess(ResultReview reviewItem) {
						// TODO Auto-generated method stub

						mAdapter.setKeyword(ReadMainFragment.keyword);
						mAdapter.clear();
						mAdapter.setTotalCount(Integer
								.parseInt(reviewItem.totalCount)); // 임시
						mAdapter.addAll(reviewItem.reviews.lists);

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

		Bundle b = getArguments();
		if (b == null) {
			getActionBar().setTitle("리뷰보기");
		} else {
			String keyword = b.getString("keyword");
			getActionBar().setTitle(keyword);
		}
		// getReviewData(keyword, ORDER_RECENT, 1, searchMode);

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
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.item_write) {

			if (MyUserManager.getInstance().isLogin()) {
				Intent intent = new Intent(getActivity(),
						WriteReviewActivity.class);
				startActivityForResult(intent, REQUESTCODE_WRITEREVIEW);
				return true;
			} else {
				NeedLoginMyPageFragment dialog = new NeedLoginMyPageFragment();
				dialog.show(getChildFragmentManager(), "로그인");

			}

		} else if (id == R.id.item_search) {

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE_WRITEREVIEW
				&& resultCode == Activity.RESULT_OK) {
			getReviewData("", ORDER_RECENT, 1, searchMode); // 초기세팅
		} else if (requestCode == REQUESTCODE_SOMETHINGHAPPENREVIEW
				&& resultCode == Activity.RESULT_OK) {
			getReviewData("", ORDER_RECENT, 1, searchMode); // 초기세팅
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);

		inflater.inflate(R.menu.review_menu, menu);

		final MenuItem item = menu.findItem(R.id.item_search);
		final MenuItem itemwrite = menu.findItem(R.id.item_write);
		SearchView searchView = (SearchView) item.getActionView();
		searchView.setQueryHint("리뷰검색");

		MenuItemCompat.setOnActionExpandListener(item,
				new OnActionExpandListener() {

					@Override
					public boolean onMenuItemActionExpand(MenuItem arg0) {
						// TODO Auto-generated method stub
						layout_buttons.setVisibility(View.VISIBLE);
						Animation anim = AnimationUtils.loadAnimation(
								getActivity(), R.anim.abc_slide_in_top);
						layout_buttons.startAnimation(anim);
						return true;
					}

					@Override
					public boolean onMenuItemActionCollapse(MenuItem arg0) {
						// TODO Auto-generated method stub
						Animation anim = AnimationUtils.loadAnimation(
								getActivity(), R.anim.abc_slide_out_top);
						layout_buttons.startAnimation(anim);
						layout_buttons.setVisibility(View.GONE);
						return true;
					}
				});

		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				keyword = query;
				getReviewData(keyword, orderMode, 1, searchMode);
				if (keyword != null)
					getActionBar().setTitle(keyword);
				else
					getActionBar().setTitle("전체 리뷰보기");

				item.collapseActionView(); // 자동으로 닫게 만들기
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				newText = newText.isEmpty() ? "" : "Query so far : " + newText;

				return false;
			}

		});

	}
}
