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
import com.lewisapp.bookletter.comment_list.CommentItemData;
import com.lewisapp.bookletter.comment_list.CommentItemView;
import com.lewisapp.bookletter.comment_list.CommentItemView.OnCommentDeleteClickListener;
import com.lewisapp.bookletter.writereview.WriteReviewActivity;

public class DeleteCommentDialogFragmentyet extends DialogFragment {

	TextView tvTitle;
	TextView tvMessage;
	Button btnOk;
	Button btnCancle;
	
	public interface OnCommentDeleteClickListener {
		public void onCommentDeleteClick();
	}

	OnCommentDeleteClickListener mCommentDeleteClickListener;

	public void setOnCommentdeleteListener(OnCommentDeleteClickListener listener) {
		mCommentDeleteClickListener = listener;
	}
	
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
		tvTitle.setText("댓글 삭제");
		tvMessage.setText("이 댓글을 북레터에서 영구적으로\n삭제 하시겠습니까?");
		btnOk.setText("삭제");

		btnCancle.setText("취소");
		 
		 btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mCommentDeleteClickListener != null) {
					mCommentDeleteClickListener.onCommentDeleteClick();
				}
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
