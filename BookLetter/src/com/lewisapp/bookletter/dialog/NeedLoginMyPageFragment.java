package com.lewisapp.bookletter.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.login.LoginFirstActivity;
import com.lewisapp.bookletter.writereview.WriteReviewActivity;

public class NeedLoginMyPageFragment extends DialogFragment {

	TextView tvTitle;
	TextView tvMessage;
	Button btnOk;
	Button btnCancle;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

		View view = inflater.inflate(R.layout.dialog_bookletter_custom,
				container, false);
		tvTitle=(TextView)view.findViewById(R.id.textView_dialogTitle);
		tvMessage=(TextView)view.findViewById(R.id.textView_dialogMessage);
		btnOk = (Button) view.findViewById(R.id.button_dialogok);
		btnCancle = (Button) view.findViewById(R.id.button_dialogcancle);
		tvTitle.setText("로그인");
		tvMessage.setText("북레터 회원만 이용 가능합니다.\n로그인해주세요");
		btnCancle.setText("돌아가기");
		btnOk.setText("로그인");


		 
		 btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent tmpIntent = new Intent(getActivity(),
						LoginFirstActivity.class);
				startActivity(tmpIntent);
				dismiss();
			}
		});

		 
		 btnCancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});

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
