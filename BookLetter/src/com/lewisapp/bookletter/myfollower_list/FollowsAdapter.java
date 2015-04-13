package com.lewisapp.bookletter.myfollower_list;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FollowsAdapter extends BaseAdapter implements
		FollowerItemView.OnFollowerDeleteClickListener {
	ArrayList<FollowerItemData> items = new ArrayList<FollowerItemData>();
	Context mContext;

	public interface OnAdapterItemClickListener {
		public void onAdapterItemClick(FollowsAdapter adapter, View view, FollowerItemData item);
	}
	
	OnAdapterItemClickListener mListener;
	
	public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
		mListener = listener;
	}
	
	public FollowsAdapter(Context context) {
		super();
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	public void add(FollowerItemData item) {
		items.add(item);
		notifyDataSetChanged();
	}

	public void clear() {
		items.clear();
		notifyDataSetChanged();
	}

	public void addAll(ArrayList<FollowerItemData> items) {
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
		FollowerItemView view;
		if (convertView == null) {
			view = new FollowerItemView(mContext);
			view.setOnFollowerdeleteListener(this);
		} else {
			view = (FollowerItemView) convertView;
		
		}
		view.setFollowerItemData(items.get(position));
		return view;
	}


	
	@Override
	public void onFollowerDeleteClick(View v, FollowerItemData data) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (mListener != null) {
			mListener.onAdapterItemClick(this, v, data);
		}
	}


}
