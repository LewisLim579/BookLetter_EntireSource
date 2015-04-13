package com.lewisapp.bookletter.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.lewisapp.bookletter.R;

public class DeleteFollowerDialogFragmentyet extends DialogFragment {

	TextView tvTitle;
	TextView tvMessage;
	Button btnOk;
	Button btnCancle;

	public interface OnFollowerDeleteClickListener {
		public void onFollowerDeleteClick();

		public void onFollowerDeleteCancleClick();
	}

	OnFollowerDeleteClickListener mFollowerDeleteClickListener;

	public void setOnFollowerdeleteListener(
			OnFollowerDeleteClickListener listener) {
		mFollowerDeleteClickListener = listener;
	}

	public void setOnFollowerdeleteCancleListener(
			OnFollowerDeleteClickListener listener) {
		mFollowerDeleteClickListener = listener;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

		View view = inflater.inflate(R.layout.dialog_bookletter_custom,
				container, false);
		tvTitle = (TextView) view.findViewById(R.id.textView_dialogTitle);
		tvMessage = (TextView) view.findViewById(R.id.textView_dialogMessage);
		btnOk = (Button) view.findViewById(R.id.button_dialogok);
		btnCancle = (Button) view.findViewById(R.id.button_dialogcancle);
		tvTitle.setText("팔로우 해제");
		tvMessage.setText("이 대상자를 팔로우 목록에서\n제외시키겠습니까?");
		btnOk.setText("해제");

		btnCancle.setText("취소");

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mFollowerDeleteClickListener != null) {
					mFollowerDeleteClickListener.onFollowerDeleteClick();
				}
				dismiss();
			}

		});

		btnCancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (mFollowerDeleteClickListener != null) {
					mFollowerDeleteClickListener.onFollowerDeleteCancleClick();
				}
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
