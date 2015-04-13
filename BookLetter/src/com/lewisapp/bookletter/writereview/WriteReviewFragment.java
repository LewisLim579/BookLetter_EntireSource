package com.lewisapp.bookletter.writereview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class WriteReviewFragment extends Fragment {

	ImageView userImg;
	TextView userName;
	EditText editText_keyword1;
	EditText editText_keyword2;
	EditText editText_keyword3;
	LinearLayout layout_key2;
	LinearLayout layout_key3;
	ImageLoader mLoader;
	EditText editText_tosubject;
	EditText editText_reviewcontent;
	String reqNum;
	RadioGroup radioGroup_level;
	RadioButton radiohigh;
	RadioButton radiomid;
	RadioButton radiolow;
	ImageButton imageButtonPlusKeyword;
	String level;
	int keywordcount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_write_review, container,
				false);
		keywordcount = 1;
		mLoader = ImageLoader.getInstance();
		radioGroup_level = (RadioGroup) view
				.findViewById(R.id.radioGroup_level);
		userImg = (ImageView) view.findViewById(R.id.imageView_userImg);
		userName = (TextView) view.findViewById(R.id.textView_userName);

		mLoader.displayImage(
				MyUserManager.getInstance().getMyUserData().userImg, userImg);
		userName.setText(MyUserManager.getInstance().getMyUserData().userNick);
		radiohigh = (RadioButton) view.findViewById(R.id.radio_levelhigh);
		radiomid = (RadioButton) view.findViewById(R.id.radio_levelmid);
		radiolow = (RadioButton) view.findViewById(R.id.radio_levellow);
		layout_key2 = (LinearLayout) view.findViewById(R.id.layout_keyword2);
		layout_key3 = (LinearLayout) view.findViewById(R.id.layout_keyword3);
		imageButtonPlusKeyword = (ImageButton) view
				.findViewById(R.id.imageButton_keywordplus);
		editText_keyword1 = (EditText) view
				.findViewById(R.id.editText_keyword1);
		editText_keyword2 = (EditText) view
				.findViewById(R.id.editText_keyword2);
		editText_keyword3 = (EditText) view
				.findViewById(R.id.editText_keyword3);
		editText_tosubject = (EditText) view
				.findViewById(R.id.editText_tosubject);
		editText_reviewcontent = (EditText) view
				.findViewById(R.id.editText_reviewcontent);

		final Bundle bundle = getArguments();
		if (bundle.getString("reqNum") != null) {
			reqNum = bundle.getString("reqNum");
		}
		imageButtonPlusKeyword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (keywordcount == 1) {

					layout_key2.setVisibility(View.VISIBLE);
					keywordcount++;
				} else if (keywordcount == 2) {
					layout_key3.setVisibility(View.VISIBLE);
					keywordcount++;
				}
			}
		});

		level = "2";
		radioGroup_level
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub

						if (checkedId == R.id.radio_levelhigh) {
							level = "3";
							radioGroup_level.setBackground(getResources()
									.getDrawable(R.drawable.level_high));

						} else if (checkedId == R.id.radio_levelmid) {
							level = "2";
							radioGroup_level.setBackground(getResources()
									.getDrawable(R.drawable.level_middle));

						} else if (checkedId == R.id.radio_levellow) {
							level = "1";
							radioGroup_level.setBackground(getResources()
									.getDrawable(R.drawable.level_low));

						}

					}
				});

		Button btn = (Button) view.findViewById(R.id.button_reviewupload);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String title = editText_tosubject.getText().toString();
				final String content = editText_reviewcontent.getText()
						.toString();
				final String keyword1 = editText_keyword1.getText().toString();
				final String keyword2 = editText_keyword2.getText().toString();
				final String keyword3 = editText_keyword3.getText().toString();

				if (title.equals("")) {
					Toast.makeText(getActivity(), "제목을 입력해 주세요", 0).show();
				} else if (content.equals("")) {
					Toast.makeText(getActivity(), "내용을 입력해 주세요", 0).show();
				} else if (keyword1.equals("")) {
					Toast.makeText(getActivity(), "키워드를 입랙해주세요", 0).show();
				} else {
					NetworkManager.getInstnace().WriteReview(getActivity(),
							title, content, level, keyword1, keyword2,
							keyword3, bundle.getString("bookdata_isbn"),
							bundle.getString("bookdata_title"),
							bundle.getString("bookdata_img"),
							bundle.getString("bookdata_autor"), reqNum,
							new OnResultListener<WriteResult>() {

								@Override
								public void onSuccess(WriteResult dataFromServer) {
									// TODO Auto-generated method stub
									Log.i("tmoney", "" + dataFromServer.message);
									if (dataFromServer.success.equals("1")) {
										Intent data = new Intent();
										getActivity().setResult(
												Activity.RESULT_OK, data);
										getActivity().finish(); //
										getActivity()
												.overridePendingTransition(0, 0);
									}
								}

								@Override
								public void onFail(int code) {
									// TODO Auto-generated method stub
									Toast.makeText(getActivity(), reqNum + "",
											0).show();
								}
							});

				}

			}
		});

		view.setFocusableInTouchMode(true);
		// view.requestFocus();

		view.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					CancleWriteDialogFragment dialog = new CancleWriteDialogFragment();
					dialog.show(getChildFragmentManager(), "테스트");
					return true;
				}
				return false;
			}
		});

		editText_keyword1.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					CancleWriteDialogFragment dialog = new CancleWriteDialogFragment();
					dialog.show(getChildFragmentManager(), "테스트");
					return true;
				}
				return false;
			}
		});

		editText_keyword2.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					CancleWriteDialogFragment dialog = new CancleWriteDialogFragment();
					dialog.show(getChildFragmentManager(), "테스트");
					return true;
				}
				return false;
			}
		});

		editText_keyword3.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					CancleWriteDialogFragment dialog = new CancleWriteDialogFragment();
					dialog.show(getChildFragmentManager(), "테스트");
					return true;
				}
				return false;
			}
		});

		editText_reviewcontent.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					CancleWriteDialogFragment dialog = new CancleWriteDialogFragment();
					dialog.show(getChildFragmentManager(), "테스트");
					return true;
				}
				return false;
			}
		});

		editText_tosubject.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {

					CancleWriteDialogFragment dialog = new CancleWriteDialogFragment();
					dialog.show(getChildFragmentManager(), "테스트");
					return true;
				}
				return false;
			}
		});

		return view;
	}

}
