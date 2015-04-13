package com.lewisapp.bookletter.searchbook_list;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BookAdapter extends BaseAdapter {

	ArrayList<BookItem> items = new ArrayList<BookItem>();
	Context mContext;

	int totalCount;
	String keyword;

	public BookAdapter(Context context) {
		super();
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	public void setTotalCount(int total) {
		totalCount = total;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	public void add(BookItem item) {
		items.add(item);
		notifyDataSetChanged();
	}

	public void clear() {
		items.clear();
		notifyDataSetChanged();
	}

	public void addAll(ArrayList<BookItem> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
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
		BookItemView view;
		if (convertView == null) {
			view = new BookItemView(mContext);
		} else {
			view = (BookItemView) convertView;
		}
		view.setBookItem(items.get(position));
		return view;
	}

}
