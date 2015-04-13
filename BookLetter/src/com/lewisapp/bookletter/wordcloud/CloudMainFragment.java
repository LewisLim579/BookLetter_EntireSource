package com.lewisapp.bookletter.wordcloud;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.main.MainActivity;

public class CloudMainFragment extends Fragment {

	FrameLayout layout;
	String test = "";
	Button keyword1;
	Button keyword2;
	Button keyword3;
	Button keyword4;
	Button keyword5;
	Button keyword6;
	Button keyword7;
	Button keyword8;
	Button keyword9;
	Button keyword10;
	Button keyword11;
	Button keyword12;
	Button keyword13;
	Button keyword14;
	Button keyword15;
	Button keyword16;
	Button keyword17;
	Button keyword18;
	Button keyword19;
	Button keyword20;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_cloud_main, container,
				false);
		layout = (FrameLayout) view.findViewById(R.id.layoutFrame);

		keyword1 = (Button) view.findViewById(R.id.button1);
		keyword2 = (Button) view.findViewById(R.id.button2);
		keyword3 = (Button) view.findViewById(R.id.button3);
		keyword4 = (Button) view.findViewById(R.id.button4);
		keyword5 = (Button) view.findViewById(R.id.button5);
		keyword6 = (Button) view.findViewById(R.id.button6);
		keyword7 = (Button) view.findViewById(R.id.button7);
		keyword8 = (Button) view.findViewById(R.id.button8);
		keyword9 = (Button) view.findViewById(R.id.button9);
		keyword10 = (Button) view.findViewById(R.id.button10);
		keyword11 = (Button) view.findViewById(R.id.button11);
		keyword12 = (Button) view.findViewById(R.id.button12);
		keyword13 = (Button) view.findViewById(R.id.button13);
		keyword14 = (Button) view.findViewById(R.id.button14);
		keyword15 = (Button) view.findViewById(R.id.button15);
		keyword16 = (Button) view.findViewById(R.id.button16);
		keyword17 = (Button) view.findViewById(R.id.button17);
		keyword18 = (Button) view.findViewById(R.id.button18);
		keyword19 = (Button) view.findViewById(R.id.button19);
		keyword20 = (Button) view.findViewById(R.id.button20);
		loadKeywords();

		keyword1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				((MainActivity) getActivity()).cloudCallReviews(keyword1
						.getText().toString());

			}
		});
		keyword2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword2
						.getText().toString());
			}
		});
		keyword3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword3
						.getText().toString());
			}
		});
		keyword4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword4
						.getText().toString());
			}
		});
		keyword5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword5
						.getText().toString());
			}
		});
		keyword6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword6
						.getText().toString());
			}
		});
		keyword7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword7
						.getText().toString());
			}
		});
		keyword8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword8
						.getText().toString());
			}
		});
		keyword9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword9
						.getText().toString());
			}
		});
		keyword10.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword10
						.getText().toString());
			}
		});
		keyword11.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword11
						.getText().toString());
			}
		});
		keyword12.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword12
						.getText().toString());
			}
		});
		keyword13.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword13
						.getText().toString());
			}
		});
		keyword14.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword14
						.getText().toString());
			}
		});
		keyword15.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword15
						.getText().toString());
			}
		});
		keyword16.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword16
						.getText().toString());
			}
		});
		keyword17.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword17
						.getText().toString());
			}
		});
		keyword18.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword18
						.getText().toString());
			}
		});
		keyword19.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword19
						.getText().toString());
			}
		});
		keyword20.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).cloudCallReviews(keyword20
						.getText().toString());
			}
		});

		return view;
	}

	private void loadKeywords() {

		NetworkManager.getInstnace().getTotalKeywords(getActivity(),
				new OnResultListener<KeywordResult>() {

					@Override
					public void onSuccess(KeywordResult dataFromServer) {
						// TODO Auto-generated method stub

						keyword1.setText(dataFromServer.keywords.key1);
						keyword2.setText(dataFromServer.keywords.key2);
						keyword3.setText(dataFromServer.keywords.key3);
						keyword4.setText(dataFromServer.keywords.key4);
						keyword5.setText(dataFromServer.keywords.key5);
						keyword6.setText(dataFromServer.keywords.key6);
						keyword7.setText(dataFromServer.keywords.key7);
						keyword8.setText(dataFromServer.keywords.key8);
						keyword9.setText(dataFromServer.keywords.key9);
						keyword10.setText(dataFromServer.keywords.key10);
						keyword11.setText(dataFromServer.keywords.key11);
						keyword12.setText(dataFromServer.keywords.key12);
						keyword13.setText(dataFromServer.keywords.key13);
						keyword14.setText(dataFromServer.keywords.key14);
						keyword15.setText(dataFromServer.keywords.key15);
						keyword16.setText(dataFromServer.keywords.key16);
						keyword17.setText(dataFromServer.keywords.key17);
						keyword18.setText(dataFromServer.keywords.key18);
						keyword19.setText(dataFromServer.keywords.key19);
						keyword20.setText(dataFromServer.keywords.key20);
					}

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub

					}
				});

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("testing", "onResume");
		getActionBar().setTitle("북레터");
	}

	ActionBar actionBar;

	public ActionBar getActionBar() {
		if (actionBar == null) {
			actionBar = ((ActionBarActivity) getActivity())
					.getSupportActionBar();
		}
		return actionBar;
	}
	

}
