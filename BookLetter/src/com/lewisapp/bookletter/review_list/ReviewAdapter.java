package com.lewisapp.bookletter.review_list;

import java.util.ArrayList;

import com.lewisapp.bookletter.searchbook_list.BookItem;
import com.lewisapp.bookletter.searchbook_list.BookItemView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ReviewAdapter extends BaseAdapter {
	ArrayList<ReviewItemData> items = new ArrayList<ReviewItemData>();
	Context mContext;
	String keyword;

	int totalCount;

	public ReviewAdapter(Context context) {
		super();
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	public void setTotalCount(int total) {
		totalCount = total;
	}

	public void setKeyword(String key) {
		keyword = key;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void add(ReviewItemData item) {
		items.add(item);
		notifyDataSetChanged();
	}

	public void clear() {
		items.clear();
		notifyDataSetChanged();
	}

	public void addAll(ArrayList<ReviewItemData> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ReviewtItemView view;
		if (convertView == null) {
			view = new ReviewtItemView(mContext);
		} else {
			view = (ReviewtItemView) convertView;
		}
		view.setReviewItem(items.get(position), keyword);
		return view;
	}

}
