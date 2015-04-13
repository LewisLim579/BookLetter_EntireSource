package com.lewisapp.bookletter.request_list;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class RequestAdapter extends BaseAdapter {
	ArrayList<RequestItemData> items = new ArrayList<RequestItemData>();
	Context mContext;
	int totalCount;
	
	public RequestAdapter(Context context) {
		super();
		// TODO Auto-generated constructor stub
		mContext=context;
	}
	
	public void setTotalCount(int total) {
		totalCount =total;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	public void add(RequestItemData item) {
		items.add(item);
		notifyDataSetChanged();
	}

	public void clear() {
		items.clear();
		notifyDataSetChanged();
	}

	public void addAll(ArrayList<RequestItemData> items) {
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
		RequestItemView view;
		if (convertView == null) {
			view = new RequestItemView(mContext);
		} else {
			view = (RequestItemView) convertView;
		}
		view.setRequestItem(items.get(position));
		return view;
	}

}
