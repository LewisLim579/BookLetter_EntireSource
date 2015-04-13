package com.lewisapp.bookletter.review_list;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lewisapp.bookletter.FontManager;
import com.lewisapp.bookletter.MyApplication;
import com.lewisapp.bookletter.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ReviewtItemView extends FrameLayout {
	ImageLoader mLoader;
	ImageView imageView_thumnail;
	TextView textViewToPerson;
	TextView textViewBookName;
	TextView textViewAuthor;
	TextView reviewContent;
	TextView textViewKeyword1;
	TextView textViewKeyword2;
	TextView textViewKeyword3;
	TextView textViewDate;
	TextView textViewWriter;
	TextView textViewtLikeCount;
	TextView textViewCommentCount;

	public ReviewtItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_reviewlist,
				this);
		imageView_thumnail = (ImageView) findViewById(R.id.imageView_bookthumnail);
		mLoader = ImageLoader.getInstance();

		textViewToPerson = (TextView) findViewById(R.id.textView_toperson);
		textViewBookName = (TextView) findViewById(R.id.textView_bookname);
		textViewAuthor = (TextView) findViewById(R.id.textView_author);
		reviewContent = (TextView) findViewById(R.id.textView_reviewcontent);
		textViewKeyword1 = (TextView) findViewById(R.id.textView_keyword1);
		textViewKeyword2 = (TextView) findViewById(R.id.textView_keyword2);
		textViewKeyword3 = (TextView) findViewById(R.id.textView_keyword3);
		textViewDate = (TextView) findViewById(R.id.textView_date);
		textViewWriter = (TextView) findViewById(R.id.textView_username);
		textViewtLikeCount = (TextView) findViewById(R.id.textView_likecount);
		textViewCommentCount = (TextView) findViewById(R.id.textView_commentcount);

	}

	ReviewItemData mItem;

	public void setReviewItem(ReviewItemData item, String keyword) {
		mItem = item;

		Typeface tmpTypeface = FontManager.getInstance().getTypeface(
				MyApplication.getContext(), FontManager.FONT_NAME_NOTOSANS);

		if (keyword != null && !keyword.equals("")) {
			if (item.bookTitle.contains(keyword)) {
				textViewBookName.setTypeface(tmpTypeface, Typeface.BOLD);
			} else {
				textViewBookName.setTypeface(tmpTypeface, Typeface.NORMAL);
			}
			if (item.bookTitle.contains(keyword)) {
				textViewAuthor.setTypeface(tmpTypeface, Typeface.BOLD);
			} else {
				textViewAuthor.setTypeface(tmpTypeface, Typeface.NORMAL);
			}
		} else {
			textViewBookName.setTypeface(tmpTypeface, Typeface.NORMAL);
			textViewAuthor.setTypeface(tmpTypeface, Typeface.NORMAL);
		}

		if (item.key2 != null && item.key3 != null) {
			if (keyword != null) {
				if (item.key1.equals(keyword))
					textViewKeyword1.setTypeface(tmpTypeface, Typeface.BOLD);
				else
					textViewKeyword1.setTypeface(tmpTypeface, Typeface.NORMAL);
				if (item.key2.equals(keyword))
					textViewKeyword2.setTypeface(tmpTypeface, Typeface.BOLD);
				else
					textViewKeyword2.setTypeface(tmpTypeface, Typeface.NORMAL);
				if (item.key3.equals(keyword))
					textViewKeyword3.setTypeface(tmpTypeface, Typeface.BOLD);
				else
					textViewKeyword3.setTypeface(tmpTypeface, Typeface.NORMAL);
			} else {
				textViewKeyword1.setTypeface(tmpTypeface, Typeface.NORMAL);
				textViewKeyword2.setTypeface(tmpTypeface, Typeface.NORMAL);
				textViewKeyword3.setTypeface(tmpTypeface, Typeface.NORMAL);
			}

			textViewKeyword1.setText("#" + item.key1);
			textViewKeyword2.setText("#" + item.key2);
			textViewKeyword3.setText("#" + item.key3);
			textViewKeyword2.setVisibility(View.VISIBLE);
			textViewKeyword3.setVisibility(View.VISIBLE);
		} else if (item.key3 == null && item.key2 != null) {
			if (keyword != null) {
				if (item.key1.equals(keyword))
					textViewKeyword1.setTypeface(tmpTypeface, Typeface.BOLD);
				else
					textViewKeyword1.setTypeface(tmpTypeface, Typeface.NORMAL);
				if (item.key2.equals(keyword))
					textViewKeyword2.setTypeface(tmpTypeface, Typeface.BOLD);
				else
					textViewKeyword2.setTypeface(tmpTypeface, Typeface.NORMAL);

			} else {
				textViewKeyword1.setTypeface(tmpTypeface, Typeface.NORMAL);
				textViewKeyword2.setTypeface(tmpTypeface, Typeface.NORMAL);

			}
			textViewKeyword1.setText("#" + item.key1);
			textViewKeyword2.setText("#" + item.key2);
			textViewKeyword2.setVisibility(View.VISIBLE);
			textViewKeyword3.setVisibility(View.GONE);
		} else if (item.key3 == null && item.key2 == null) {
			if (keyword != null) {
				
				if (item.key1.equals(keyword)) {
					textViewKeyword1.setTypeface(tmpTypeface, Typeface.BOLD);
				} else
					textViewKeyword1.setTypeface(tmpTypeface, Typeface.NORMAL);
			} else
				textViewKeyword1.setTypeface(tmpTypeface, Typeface.NORMAL);

			Log.i("testing22", "들어옴");
			textViewKeyword1.setText("#" + item.key1);
			textViewKeyword2.setVisibility(View.GONE);
			textViewKeyword3.setVisibility(View.GONE);
		}
		textViewToPerson.setText("To. " + item.revTitle);
		textViewBookName.setText(item.bookTitle);
		textViewAuthor.setText(item.bookAuth);
		reviewContent.setText(item.revContent);
		textViewDate.setText(item.revDate);
		textViewWriter.setText(item.userNick);
		textViewtLikeCount.setText(item.likeCnt + "");
		textViewCommentCount.setText(item.comCnt + "");
		mLoader.displayImage(item.bookImg, imageView_thumnail);
	}

}
