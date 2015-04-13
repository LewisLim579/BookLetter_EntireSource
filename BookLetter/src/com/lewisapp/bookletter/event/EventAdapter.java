package com.lewisapp.bookletter.event;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class EventAdapter extends BaseAdapter {
	ArrayList<EventItemData> items = new ArrayList<EventItemData>();
	Context mContext;

	public EventAdapter(Context context) {
		super();
		// TODO Auto-generated constructor stub
		mContext = context;
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

	public void add(EventItemData item) {
		items.add(item);
		notifyDataSetChanged();
	}

	public void clear() {
		items.clear();
		notifyDataSetChanged();
	}

	public void addAll(ArrayList<EventItemData> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		EventItemView view;
		if (convertView == null) {
			view = new EventItemView(mContext);
		} else {
			view = (EventItemView) convertView;

		}
		view.setEventData(items.get(position));
		return view;
	}
}
