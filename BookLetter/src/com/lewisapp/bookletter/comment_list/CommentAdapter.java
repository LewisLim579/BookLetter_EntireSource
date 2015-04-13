package com.lewisapp.bookletter.comment_list;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CommentAdapter extends BaseAdapter implements CommentItemView.OnCommentDeleteClickListener{

	ArrayList<CommentItemData> items = new ArrayList<CommentItemData>();
	Context mContext;
	
	
	public interface OnAdapterItemClickListener {
		public void onAdapterItemClick(CommentAdapter adapter, View view, CommentItemData item);
	}
	
	OnAdapterItemClickListener mListener;
	
	public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
		mListener = listener;
	}
	
	
	public CommentAdapter(Context mContext) {
		super();
		this.mContext = mContext;
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
		CommentItemView view;
		if (convertView == null) {
			view = new CommentItemView(mContext);
			view.setOnCommentdeleteListener(this);
		} else {
			view = (CommentItemView) convertView;
		}
		view.setCommentItem(items.get(position));
		return view;
	}
	
	public void add(CommentItemData item) {
		items.add(item);
		notifyDataSetChanged();
	}

	public void clear() {
		items.clear();
		notifyDataSetChanged();
	}

	public void addAll(ArrayList<CommentItemData> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	@Override
	public void onCommentDeleteClick(View v, CommentItemData data) {

		// TODO Auto-generated method stub
		if (mListener != null) {
			mListener.onAdapterItemClick(this, v,data);
		}
	}

}
