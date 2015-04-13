package com.lewisapp.bookletter.writereview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lewisapp.bookletter.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class WriteReviewSelectBookInfoFragment extends Fragment {

	ImageView imageView_img;
	TextView textView_title;
	TextView textView_autor;
	TextView textView_desc;
	ImageButton button_link;
	ImageLoader mLoader;
	Button button_gowritereveiw;
	LinearLayout layout_bookcover;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.fragment_review_select_book_info,
				container, false);
		layout_bookcover = (LinearLayout) view
				.findViewById(R.id.layout_bookcover);
		
		int random = (int) (Math.random() * 7); 
		if(random==0){
		layout_bookcover.setBackgroundResource(R.drawable.sbookcover1);
		}else if(random==1){
			layout_bookcover.setBackgroundResource(R.drawable.sbookcover2);
		}else if(random==2){
			layout_bookcover.setBackgroundResource(R.drawable.sbookcover3);
		}else if(random==3){
			layout_bookcover.setBackgroundResource(R.drawable.sbookcover4);
		}else if(random==4){
			layout_bookcover.setBackgroundResource(R.drawable.sbookcover5);
		}else if(random==5){
			layout_bookcover.setBackgroundResource(R.drawable.sbookcover6);
		}else if(random==6){
			layout_bookcover.setBackgroundResource(R.drawable.sbookcover7);
		}
		
		
		
		
		view.setFocusableInTouchMode(true);
		view.requestFocus();

		view.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					((WriteReviewSelectParentFragment) getParentFragment())
							.dopopStack();
					return true;
				}
				return false;
			}
		});
		final Bundle bundle = getArguments();

		button_link = (ImageButton) view.findViewById(R.id.button_link);
		button_link.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(bundle
						.getString("bookdata_link")));
				startActivity(i);
				getActivity().overridePendingTransition(0, 0);
			}
		});

		button_gowritereveiw = (Button) view
				.findViewById(R.id.button_gowritereview);
		button_gowritereveiw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((WriteReviewActivity) getActivity()).goWriteReivew(bundle);
			}
		});
		imageView_img = (ImageView) view
				.findViewById(R.id.imageView_bookbigimg);
		textView_title = (TextView) view
				.findViewById(R.id.textView_bookinfo_title);
		textView_autor = (TextView) view
				.findViewById(R.id.textView_bookinfo_autor);
		textView_desc = (TextView) view.findViewById(R.id.textView_description);
		mLoader = ImageLoader.getInstance();

		
		mLoader.displayImage(bundle.getString("bookdata_img"), imageView_img);
		
		if(bundle.getString("bookdata_title")!=null)
		textView_title
				.setText(Html.fromHtml(bundle.getString("bookdata_title")));
		if(bundle.getString("bookdata_autor")!=null)
		textView_autor
				.setText(Html.fromHtml(bundle.getString("bookdata_autor")));
		if(bundle.getString("bookdata_desc")!=null)
		textView_desc.setText(Html.fromHtml(bundle.getString("bookdata_desc")));

		return view;
	}
}
