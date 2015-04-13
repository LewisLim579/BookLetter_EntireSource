package com.lewisapp.bookletter.searchbook_list;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lewisapp.bookletter.DataManager;
import com.lewisapp.bookletter.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BookItemView extends FrameLayout {
	ImageView iconView;
	TextView titleView;
	TextView writerView;
	ImageLoader mLoader;

	public BookItemView(Context context) {
		super(context);
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_searchbook,
				this);
		iconView = (ImageView) findViewById(R.id.imageView_bookimg);
		titleView = (TextView) findViewById(R.id.textView_booktitle);
		writerView = (TextView) findViewById(R.id.textView_bookautor);
		mLoader = ImageLoader.getInstance();
	}

	BookItem mItem;

	public void setBookItem(BookItem item) {
		mItem = item;
		if (item.title != null || !item.title.equals(""))
			titleView.setText(DataManager.getInstance()
					.myfromHtmltoStringTitle(item.title));
		if (item.author != null)
			writerView.setText(Html.fromHtml(item.author));

		mLoader.displayImage(item.image, iconView);

	}

}
