package com.lewisapp.bookletter.dialog;

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
import com.lewisapp.bookletter.writereview.WriteReviewActivity;

public class CancleWriteDialogFragment extends DialogFragment {

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
		tvTitle.setText("작성 취소");
		tvMessage.setText("작성한 내용이 저장되지 않습니다.\n리뷰작성을 취소 하시겠습니까?");
		btnOk.setText("작성취소");

		btnCancle.setText("돌아가기");
		 
		 btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((WriteReviewActivity) getActivity()).goReselectBook();
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
