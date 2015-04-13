package com.lewisapp.bookletter.myfollower_list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.comment_list.CommentItemData;
import com.lewisapp.bookletter.comment_list.CommentItemView;
import com.lewisapp.bookletter.comment_list.CommentItemView.OnCommentDeleteClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FollowerItemView extends FrameLayout {

	ImageView imageViewUserImg;
	TextView textViewUserName;
	TextView textViewUserReviewCount;
	TextView textViewFollowerCount;
	ImageButton imageButtonFollowbutton;
	FollowerItemData mItem;
	ImageLoader mLoader;
	public FollowerItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_myfollowerlist,
				this);
		mLoader = ImageLoader.getInstance();
		imageViewUserImg = (ImageView) findViewById(R.id.imageView_otheruserimg);
		textViewUserName = (TextView) findViewById(R.id.textView_userName);
		textViewUserReviewCount = (TextView) findViewById(R.id.textView_reviewcount);
		textViewFollowerCount = (TextView) findViewById(R.id.textView_followercount);
		
		imageButtonFollowbutton = (ImageButton) findViewById(R.id.imageButton_deletefollower);
		imageButtonFollowbutton.setFocusable(false);
		imageButtonFollowbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mFollowerDeleteClickListener != null) {
					mFollowerDeleteClickListener.onFollowerDeleteClick(
							FollowerItemView.this, mItem);

				}
			}
		});
	}

	
	public interface OnFollowerDeleteClickListener {
		public void onFollowerDeleteClick(View v, FollowerItemData data);
	}

	OnFollowerDeleteClickListener mFollowerDeleteClickListener;

	public void setOnFollowerdeleteListener(
			OnFollowerDeleteClickListener listener) {
		mFollowerDeleteClickListener = listener;
	}

	public void setFollowerItemData(FollowerItemData item) {
		mItem = item;
		// ImageView imageViewUserImg;
		textViewUserName.setText(item.userNick);
		textViewUserReviewCount.setText("리뷰수 " + item.revCnt);
		textViewFollowerCount.setText("팔로워 수 " + item.folCnt);
		mLoader.displayImage(item.userImg,imageViewUserImg);
	}

}
