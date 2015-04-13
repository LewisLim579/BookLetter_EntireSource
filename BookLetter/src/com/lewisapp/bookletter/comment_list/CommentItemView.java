package com.lewisapp.bookletter.comment_list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CommentItemView extends FrameLayout {
	ImageLoader mLoader;
	ImageView imageViewUserimg;
	TextView textViewUsername;
	TextView textViewComment;
	TextView textViewCommnetDate;

	ImageButton imageButtonCommentDelete;
	CommentItemData mData;

	public CommentItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mLoader = ImageLoader.getInstance();
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_commentlist,
				this);
		imageButtonCommentDelete = (ImageButton) findViewById(R.id.imageButton_commentdelete);
		imageViewUserimg = (ImageView) findViewById(R.id.imageView_userimg);
		textViewUsername = (TextView) findViewById(R.id.textView_usernickname);
		textViewComment = (TextView) findViewById(R.id.textView_comment);

		textViewCommnetDate = (TextView) findViewById(R.id.textView_commentdate);
		imageButtonCommentDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				if (mCommentDeleteClickListener != null) {
					mCommentDeleteClickListener.onCommentDeleteClick(
							CommentItemView.this, mData);
				}
			}
		});
	}

	public interface OnCommentDeleteClickListener {
		public void onCommentDeleteClick(View v, CommentItemData data);
	}

	OnCommentDeleteClickListener mCommentDeleteClickListener;

	public void setOnCommentdeleteListener(OnCommentDeleteClickListener listener) {
		mCommentDeleteClickListener = listener;
	}

	public void setCommentItem(CommentItemData data) {
		mData = data;
		mLoader.displayImage(data.userImg, imageViewUserimg);
		textViewUsername.setText(data.userNick);
		textViewComment.setText(data.comContent);
		textViewCommnetDate.setText(data.comDate.substring(0, 10));

		if (MyUserManager.getInstance().getMyUserData()!= null) {

			if (data.userNum
					.equals(MyUserManager.getInstance().getMyUserData().userNum)) {
				imageButtonCommentDelete.setVisibility(View.VISIBLE);
			} else
				imageButtonCommentDelete.setVisibility(View.GONE);
		} else
			imageButtonCommentDelete.setVisibility(View.GONE);
	}

}
