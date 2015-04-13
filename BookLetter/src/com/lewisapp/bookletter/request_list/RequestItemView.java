package com.lewisapp.bookletter.request_list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lewisapp.bookletter.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class RequestItemView extends FrameLayout {
	ImageLoader mLoader;
	ImageView imageViewUserimg;
	TextView textViewReqTitle;
	TextView textViewReqWriterDate;
	TextView textViewReqContent;
	TextView textViewAnswerCount;
	ImageView imageView_letter;

	public RequestItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mLoader = ImageLoader.getInstance();
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_requestlist,
				this);
		imageViewUserimg = (ImageView) findViewById(R.id.imageView_userimg);
		textViewReqTitle = (TextView) findViewById(R.id.textView_reqtitle);
		textViewReqWriterDate = (TextView) findViewById(R.id.textView_reqwriter_date);
		textViewReqContent = (TextView) findViewById(R.id.textView_reqcontent);
		textViewAnswerCount = (TextView) findViewById(R.id.textView_answercount);
		imageView_letter = (ImageView) findViewById(R.id.imageView_lettericon);

	}

	RequestItemData mItem;

	public void setRequestItem(RequestItemData item) {
		mItem = item;
		textViewReqTitle.setText(item.reqTitle);
		textViewReqWriterDate.setText(item.userNick + " | " + item.reqDate);
		textViewReqContent.setText(item.reqContent);
		mLoader.displayImage(item.userImg, imageViewUserimg);
		textViewAnswerCount.setText(item.revCnt);
		if (item.revCnt != null) {

			if (item.revCnt.equals("0")) {
				textViewAnswerCount.setTextColor(getResources().getColor(
						R.color.letternothing));
				imageView_letter.setImageResource(R.drawable.letterimg_nothing);

			} else {

				imageView_letter.setImageResource(R.drawable.letterimg_have);
				textViewAnswerCount.setTextColor(getResources().getColor(
						R.color.letterhave));
			}
		} else {
			textViewAnswerCount.setTextColor(getResources().getColor(
					R.color.letternothing));
			imageView_letter.setImageResource(R.drawable.letterimg_nothing);
		}

	}

}
