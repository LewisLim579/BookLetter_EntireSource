package com.lewisapp.bookletter.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lewisapp.bookletter.R;

public class SignUpDialogFragment extends DialogFragment {

	TextView tv1;
	TextView tv2;
	
	String title;
	String content;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, 0);
		
	}
	public SignUpDialogFragment() {
		super();
		// TODO Auto-generated constructor stub
	
	}
	

	public SignUpDialogFragment(String title, String content) {
		super();
		// TODO Auto-generated constructor stub
		this.title=title;
		this.content=content;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.dialog_bookletter_signup,
				container, false);
		tv1=(TextView)view.findViewById(R.id.textView_title);
		tv2=(TextView)view.findViewById(R.id.textView_content);
		tv1.setText(title);
		tv2.setText(content);
		
		
		
		getDialog().getWindow().setBackgroundDrawable
		(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return view;
	}

	@Override
	public void show(FragmentManager manager, String tag) {
		// TODO Auto-generated method stub
		if (manager.findFragmentByTag(tag) == null) {
			super.show(manager, tag);
		}
	}

}
