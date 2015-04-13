package com.lewisapp.bookletter.writereview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.searchbook_list.Book;
import com.lewisapp.bookletter.searchbook_list.BookAdapter;
import com.lewisapp.bookletter.searchbook_list.BookItem;

public class WriteReviewSelectBookListFragment extends Fragment {

	EditText editText_Searchbook;
	Button button_Searchbook;
	PullToRefreshListView refreshView;
	ListView listView_Searchbook;
	BookAdapter bookAdapter;
	InputMethodManager mIMM;
	Handler mHandler = new Handler();
	long startTime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		bookAdapter = new BookAdapter(getActivity());
		mIMM = ((InputMethodManager) getActivity().getSystemService(
				Context.INPUT_METHOD_SERVICE));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(
				R.layout.fragment_write_review_select_book_list, container,
				false);
		refreshView = (PullToRefreshListView) view
				.findViewById(R.id.listView_books);
		// listView_Searchbook = (ListView)
		// view.findViewById(R.id.listView_books);
		refreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> rView) {
				// String label =
				// DateUtils.formatDateTime(getApplicationContext(),
				// System.currentTimeMillis(),
				// DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE |
				// DateUtils.FORMAT_ABBREV_ALL);
				// refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				String keyword = bookAdapter.getKeyword();
				if (keyword != null && !keyword.equals("")) {
					int start = bookAdapter.getCount() + 1;
					int total = bookAdapter.getTotalCount();
					if (start <= total) {
						startTime = SystemClock.uptimeMillis();
						NetworkManager.getInstnace().getNaverBook(
								getActivity(), keyword, start,
								new OnResultListener<Book>() {

									@Override
									public void onSuccess(
											final Book dataFromServer) {
										// TODO Auto-generated method stub
										long currentTime = SystemClock
												.uptimeMillis();
										int delta = (int) (currentTime - startTime);
										if (delta < 2000) {
											mHandler.postDelayed(
													new Runnable() {
														public void run() {
															bookAdapter
																	.addAll(dataFromServer.item);
															refreshView
																	.onRefreshComplete();
														}
													}, 2000 - delta);
										} else {
											bookAdapter
													.addAll(dataFromServer.item);
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
						// runOnUiThread(new Runnable() {
						//
						// @Override
						// public void run() {
						// refreshView.onRefreshComplete();
						// }
						// });
					}
				}

			}
		});

		listView_Searchbook = refreshView.getRefreshableView();

		editText_Searchbook = (EditText) view
				.findViewById(R.id.editText_booksearch);

		editText_Searchbook.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					final String keyword = editText_Searchbook.getText()
							.toString();
					if (keyword != null && !keyword.equals("")) {
						NetworkManager.getInstnace().getNaverBook(
								getActivity(), keyword, 1,
								new OnResultListener<Book>() {

									@Override
									public void onSuccess(Book book) {
										// TODO Auto-generated method stub
										if (book.total != 0) {
//											mIMM.hideSoftInputFromWindow(
//													null,
//													InputMethodManager.HIDE_NOT_ALWAYS);
											
											mIMM.hideSoftInputFromWindow(
													editText_Searchbook
															.getWindowToken(), 0);
											bookAdapter.clear();
											bookAdapter.addAll(book.item);
											bookAdapter
													.setTotalCount(book.total);
											bookAdapter.setKeyword(keyword);
										} else {
											Toast.makeText(
													getActivity(),
													keyword
															+ "에 대한 검색 결과가 없습니다.",
													0).show();
										}
									}

									@Override
									public void onFail(int code) {
										// TODO Auto-generated method stub
										Toast.makeText(getActivity(),
												"fail...", Toast.LENGTH_SHORT)
												.show();
									}
								});
					}

					// Perform action on key press

					return true;
				}
				return false;
			}
		});

		listView_Searchbook.setAdapter(bookAdapter);

		listView_Searchbook.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				BookItem tmpItem = (BookItem) bookAdapter.getItem(position - 1);

				((WriteReviewSelectParentFragment) getParentFragment())
						.seeBookDetail(tmpItem);

			}
		});
		button_Searchbook = (Button) view.findViewById(R.id.button_booksearch);
		//
		// button_Searchbook.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// mIMM.hideSoftInputFromWindow(null,
		// InputMethodManager.HIDE_NOT_ALWAYS);
		// final String keyword = editText_Searchbook.getText().toString();
		// if (keyword != null && !keyword.equals("")) {
		// NetworkManager.getInstnace().getNaverBook(getActivity(),
		// keyword, 1, new OnResultListener<Book>() {
		//
		// @Override
		// public void onSuccess(Book book) {
		// // TODO Auto-generated method stub
		//
		// bookAdapter.clear();
		// bookAdapter.addAll(book.item);
		// bookAdapter.setTotalCount(book.total);
		// bookAdapter.setKeyword(keyword);
		// // editText_Searchbook.setText("");
		//
		// }
		//
		// @Override
		// public void onFail(int code) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		// }
		// }
		// });

		return view;
	}
}
