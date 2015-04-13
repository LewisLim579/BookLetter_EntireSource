package com.lewisapp.bookletter.event;

import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.myfollower_list.FollowerItemData;
import com.lewisapp.bookletter.myfollower_list.FollowerItemView;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class EventItemView extends FrameLayout {
	
	TextView tv_title;
	TextView tv_date;
	EventItemData mItem;
	public EventItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_eventlist,
				this);
		tv_title=(TextView)findViewById(R.id.textView_eventtitle);
		tv_date=(TextView)findViewById(R.id.textView_eventdate);
	}
	public void setEventData(EventItemData item) {
		mItem = item;
		// ImageView imageViewUserImg;
		tv_title.setText(item.notTitle);
		tv_date.setText(item.notDate);
	}
}
