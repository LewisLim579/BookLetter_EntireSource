package com.lewisapp.bookletter.readreview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lewisapp.bookletter.DataManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.searchbook_list.Book;
import com.lewisapp.bookletter.searchbook_list.BookItem;
import com.lewisapp.bookletter.writereview.WriteReviewActivity;
import com.lewisapp.bookletter.writereview.WriteReviewSelectParentFragment;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShowReviewBookInfoFragment extends Fragment {
	ImageView imageView_img;
	TextView textView_title;
	TextView textView_autor;
	TextView textView_desc;
	ImageButton button_link;
	ImageLoader mLoader;
	String ISBN;
	String urlLink;
	LinearLayout layout_bookcover;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_show_review_book_info,
				container, false);
		layout_bookcover = (LinearLayout) view
				.findViewById(R.id.layout_bookcover);
		imageView_img = (ImageView) view
				.findViewById(R.id.imageView_bookbigimg);
		textView_title = (TextView) view
				.findViewById(R.id.textView_bookinfo_title);
		textView_autor = (TextView) view
				.findViewById(R.id.textView_bookinfo_autor);
		textView_desc = (TextView) view.findViewById(R.id.textView_description);
		mLoader = ImageLoader.getInstance();

		int random = (int) (Math.random() * 7);
		if (random == 0) {
			layout_bookcover.setBackgroundResource(R.drawable.sbookcover1);
		} else if (random == 1) {
			layout_bookcover.setBackgroundResource(R.drawable.sbookcover2);
		} else if (random == 2) {
			layout_bookcover.setBackgroundResource(R.drawable.sbookcover3);
		} else if (random == 3) {
			layout_bookcover.setBackgroundResource(R.drawable.sbookcover4);
		} else if (random == 4) {
			layout_bookcover.setBackgroundResource(R.drawable.sbookcover5);
		} else if (random == 5) {
			layout_bookcover.setBackgroundResource(R.drawable.sbookcover6);
		} else if (random == 6) {
			layout_bookcover.setBackgroundResource(R.drawable.sbookcover7);
		}

		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					((ShowReviewActivity) getActivity()).dopopStack();
					return true;
				}
				return false;
			}
		});

		ISBN = getArguments().getString("isbn");
		initBook();

		button_link = (ImageButton) view.findViewById(R.id.button_link);
		button_link.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(urlLink));
				startActivity(i);
				getActivity().overridePendingTransition(0, 0);
			}
		});

		return view;
	}

	private void initBook() {
		NetworkManager.getInstnace().getNaverBook(getActivity(), ISBN, 1,
				new OnResultListener<Book>() {

					@Override
					public void onSuccess(Book book) {
						// TODO Auto-generated method stub
						BookItem bookDetail = book.item.get(0);

						
						mLoader.displayImage(bookDetail.image, imageView_img);
						
						if(bookDetail.title!=null){
						textView_title.setText(DataManager.getInstance()
								.myfromHtmltoStringTitle(bookDetail.title));
						}
						if(bookDetail.author!=null){
						textView_autor.setText(Html.fromHtml(bookDetail.author));
						}
						if(bookDetail.description!=null){
						textView_desc.setText(Html
								.fromHtml(bookDetail.description));
						}
						urlLink = bookDetail.link;

					}

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "fail...",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

}
