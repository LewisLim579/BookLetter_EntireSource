package com.lewisapp.bookletter.writereview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;

import com.lewisapp.bookletter.DataManager;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.searchbook_list.BookItem;

public class WriteReviewSelectParentFragment extends Fragment {

	private static final String LIST_TAG = "tab_list";
	private static final String DETAIL_TAG = "tab_detail";
	WriteReviewSelectBookListFragment listbookFragment;
	//WriteReviewSelectBookInfoFragment detailbookFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(
				R.layout.fragment_write_review_select_parent, container, false);

		listbookFragment = new WriteReviewSelectBookListFragment();
		//detailbookFragment = new WriteReviewSelectBookInfoFragment();

		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.add(R.id.child_container, listbookFragment, LIST_TAG);
		ft.addToBackStack(null); 
		ft.commit();
		
		
	
		return view;
	}
	
	public void dopopStack(){
		getChildFragmentManager().popBackStack();
	}
	

	
	public void seeBookDetail(BookItem item){
		Fragment f = getChildFragmentManager().findFragmentByTag(
				DETAIL_TAG);
		if (f == null) {
			FragmentTransaction ft = getChildFragmentManager()
					.beginTransaction();
			f = new WriteReviewSelectBookInfoFragment();
			
			
			
			Bundle bookItemBundle=new Bundle();

			bookItemBundle.putString("bookdata_img", item.image);
//			bookItemBundle.putString("bookdata_title", DataManager.getInstance().myfromHtmltoStringTitle(item.title));
//			bookItemBundle.putString("bookdata_autor", DataManager.getInstance().myfromHtmltoStringTitle(item.author));
			bookItemBundle.putString("bookdata_title", item.title);
			bookItemBundle.putString("bookdata_autor", item.author);
			bookItemBundle.putString("bookdata_link", item.link);
			bookItemBundle.putString("bookdata_desc", item.description);
			bookItemBundle.putString("bookdata_isbn", item.isbn);
			f.setArguments(bookItemBundle);
		
			ft.addToBackStack(null);
			
			ft.replace(R.id.child_container, f, DETAIL_TAG);
			ft.commit();
			//listbookFragment = null;
		}
	}

}
