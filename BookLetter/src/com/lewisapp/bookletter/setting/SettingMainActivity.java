package com.lewisapp.bookletter.setting;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.UserManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.BasicResult;
import com.lewisapp.bookletter.FontManager;
import com.lewisapp.bookletter.PropertyManager;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.UserResult;
import com.lewisapp.bookletter.main.MainActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SettingMainActivity extends ActionBarActivity {
	TestAdapter mAdapter;
	HListView listView;
	LinearLayout layout_settingback;
	LinearLayout layout_userbackgroundlist;
	boolean isShowBacklist = false;
	ImageView imageViewProfile;
	TextView textViewUserNickNamebig;
	TextView textViewUserNickname;
	TextView textViewuserEmail;
	TextView textViewFollowerCount;
	ImageLoader mLoader;
	ImageButton imageButtonchangenick;
	ImageButton imageButtonChangeProfile;
	ImageButton imageButtonChangeNickapply;
	ImageButton imageButtonChangeBack;
	ImageButton imageButtonSaveChangeNick;
	EditText editText_changenick;
	CheckBox checkBox_gcm;
	Bitmap bm;
	LinearLayout layout_normalnick;
	boolean isFirst = true;
	LinearLayout layout_changenick;
	public static final int REQUEST_CODE_CROP = 0;
	File mSavedFile;
	String userbackindex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_main);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mLoader = ImageLoader.getInstance();
		imageButtonChangeProfile = (ImageButton) findViewById(R.id.imageButton_changeprofile);
		imageViewProfile = (ImageView) findViewById(R.id.imageView_profile);
		imageButtonchangenick = (ImageButton) findViewById(R.id.imageButton_changenick);
		layout_normalnick = (LinearLayout) findViewById(R.id.layout_normalnick);
		layout_changenick = (LinearLayout) findViewById(R.id.layout_changenickname);
		imageButtonSaveChangeNick = (ImageButton) findViewById(R.id.imageButton_savenick);
		editText_changenick = (EditText) findViewById(R.id.editText_changenickname);
		textViewUserNickname = (TextView) findViewById(R.id.textView_listnickname);
		textViewUserNickNamebig = (TextView) findViewById(R.id.textView_usernickname);
		checkBox_gcm = (CheckBox) findViewById(R.id.checkBox_gcm);
		checkBox_gcm.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					PropertyManager.getInstnace().setIspush(true);
					if (!isFirst)
						Toast.makeText(SettingMainActivity.this,
								"푸쉬알림이 허용되엇습니다", 0).show();
					isFirst = false;
				} else if (!isChecked) {
					PropertyManager.getInstnace().setIspush(false);
					Toast.makeText(SettingMainActivity.this, "푸쉬알림이 해제되엇습니다", 0)
							.show();
				}

			}
		});
		imageButtonChangeProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				photoPickerIntent.setType("image/*");
				photoPickerIntent.putExtra("crop", "true");
				photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						getTempUri());
				photoPickerIntent.putExtra("outputFormat",
						Bitmap.CompressFormat.JPEG.toString());

				startActivityForResult(photoPickerIntent, REQUEST_CODE_CROP);
				// NetworkManager.getInstnace().changeProfile(SettingMainActivity.this,
				// profile, new OnResultListener<BasicResult>() {
				//
				// @Override
				// public void onSuccess(BasicResult dataFromServer) {
				// // TODO Auto-generated method stub
				//
				// }
				//
				// @Override
				// public void onFail(int code) {
				// // TODO Auto-generated method stub
				//
				// }
				// });
			}
		});

		imageButtonChangeBack = (ImageButton) findViewById(R.id.imageButton_changeback);
		layout_settingback = (LinearLayout) findViewById(R.id.layout_settingback);
		layout_userbackgroundlist = (LinearLayout) findViewById(R.id.layout_userbacklist);
		textViewuserEmail = (TextView) findViewById(R.id.textView_useremail);
		layout_userbackgroundlist.setVisibility(View.GONE);
		editText_changenick = (EditText) findViewById(R.id.editText_changenickname);

		imageButtonchangenick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layout_changenick.setVisibility(View.VISIBLE);
				layout_normalnick.setVisibility(View.GONE);
				Toast.makeText(SettingMainActivity.this, "변경하실 닉네임을 입력하세요.", 0)
						.show();

			}
		});

		imageButtonSaveChangeNick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// textViewUserNickname.setText(editText_changenick.getText()
				// .toString());
				layout_changenick.setVisibility(View.GONE);
				layout_normalnick.setVisibility(View.VISIBLE);
				NetworkManager.getInstnace().editKeyword(
						SettingMainActivity.this,
						editText_changenick.getText().toString(),
						new OnResultListener<UserResult>() {

							@Override
							public void onSuccess(UserResult dataFromServer) {

								if (dataFromServer.success.equals("1")) {
									// TODO Auto-generated method stub
									textViewUserNickname
											.setText(dataFromServer.user.userNick);
									textViewUserNickNamebig
											.setText(dataFromServer.user.userNick);
									MyUserManager.getInstance().setMyUserData(
											dataFromServer.user);
									Toast.makeText(
											SettingMainActivity.this,
											dataFromServer.user.userNick
													+ "로 닉네임이 변경되었습니다.", 0)
											.show();
								} else {
									Toast.makeText(SettingMainActivity.this,
											dataFromServer.message, 0).show();
								}

							}

							@Override
							public void onFail(int code) {
								// TODO Auto-generated method stub

							}
						});

			}
		});
		imageButtonChangeBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				if (!isShowBacklist) {
					Toast.makeText(SettingMainActivity.this,
							"선택하신 배경이 다른 사용자들에게 보여집니다", 0).show();
					layout_userbackgroundlist.setVisibility(View.VISIBLE);
					isShowBacklist = true;
					Animation anim = AnimationUtils.loadAnimation(
							SettingMainActivity.this, R.anim.abc_fade_in);
					layout_userbackgroundlist.startAnimation(anim);

				} else {
					Animation anim = AnimationUtils.loadAnimation(
							SettingMainActivity.this, R.anim.abc_fade_out);
					layout_userbackgroundlist.startAnimation(anim);
					layout_userbackgroundlist.setVisibility(View.GONE);
					isShowBacklist = false;

				}
			}
		});

		layout_settingback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				if (!isShowBacklist) {
					Toast.makeText(SettingMainActivity.this,
							"선택하신 배경이 다른 사용자들에게 보여집니다", 0).show();
					layout_userbackgroundlist.setVisibility(View.VISIBLE);
					isShowBacklist = true;
					Animation anim = AnimationUtils.loadAnimation(
							SettingMainActivity.this, R.anim.abc_fade_in);
					layout_userbackgroundlist.startAnimation(anim);

				} else {
					Animation anim = AnimationUtils.loadAnimation(
							SettingMainActivity.this, R.anim.abc_fade_out);
					layout_userbackgroundlist.startAnimation(anim);
					layout_userbackgroundlist.setVisibility(View.GONE);
					isShowBacklist = false;

				}
			}
		});

		mAdapter = new TestAdapter(this, R.layout.test_item_1);
		mAdapter.add(R.drawable.back0);
		mAdapter.add(R.drawable.back1);
		mAdapter.add(R.drawable.back2);
		mAdapter.add(R.drawable.back3);
		mAdapter.add(R.drawable.back4);
		mAdapter.add(R.drawable.back5);
		mAdapter.add(R.drawable.back6);
		mAdapter.add(R.drawable.back7);
		mAdapter.add(R.drawable.back8);
		mAdapter.add(R.drawable.back9);
		mAdapter.add(R.drawable.back10);

		listView = (HListView) findViewById(R.id.hListView1);
		listView.setAdapter(mAdapter);
		layout_settingback.setBackgroundResource(R.drawable.back0);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				// TODO Auto-generated method stub

				// 네트워크에 전송 밑 set user data

				NetworkManager.getInstnace().editBackground(
						SettingMainActivity.this, position + "",
						new OnResultListener<BackGroundResult>() {

							@Override
							public void onSuccess(
									BackGroundResult dataFromServer) {
								// TODO Auto-generated method stub

								layout_settingback
										.setBackgroundResource(mAdapter
												.getItem(position));
								MyUserManager.getInstance().getMyUserData().userBackground = dataFromServer.userBackground;
							}

							@Override
							public void onFail(int code) {
								// TODO Auto-generated method stub

							}
						});

			}
		});

		Button btn = (Button) findViewById(R.id.button_logout);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				NetworkManager.getInstnace().logout(SettingMainActivity.this,
						new OnResultListener<UserResult>() {

							@Override
							public void onSuccess(UserResult dataFromServer) {
								// TODO Auto-generated method stub
								PropertyManager.getInstnace().setPassword("");
								PropertyManager.getInstnace().setUserEmail("");
								PropertyManager.getInstnace().setFacebookId("");
								// PropertyManager.getInstnace()
								// .setRegistrationId("");
								// TODO Auto-generated method stub
								Intent intent = new Intent(
										SettingMainActivity.this,
										MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
										| Intent.FLAG_ACTIVITY_NEW_TASK);
								MyUserManager.getInstance().setMyUserData(null);

								MyUserManager.getInstance().doLogout();
								startActivity(intent);
								finish();
								overridePendingTransition(0, 0);
							}

							@Override
							public void onFail(int code) {
								// TODO Auto-generated method stub
								//
							}
						});

			}
		});
		// TODO Auto-generated method stub

		init();
		if (savedInstanceState != null) {
			String file = savedInstanceState.getString("filename");
			if (file != null) {
				mSavedFile = new File(file);
			}
		}

	}

	private void init() {
		textViewUserNickname.setText(MyUserManager.getInstance()
				.getMyUserData().userNick);
		textViewUserNickNamebig.setText(MyUserManager.getInstance()
				.getMyUserData().userNick);

		userbackindex = MyUserManager.getInstance().getMyUserData().userBackground;
		if (userbackindex.equals("0")) {
			layout_settingback.setBackgroundResource(R.drawable.back0);
		} else if (userbackindex.equals("1")) {
			layout_settingback.setBackgroundResource(R.drawable.back1);
		} else if (userbackindex.equals("2")) {
			layout_settingback.setBackgroundResource(R.drawable.back2);
		} else if (userbackindex.equals("3")) {
			layout_settingback.setBackgroundResource(R.drawable.back3);
		} else if (userbackindex.equals("4")) {
			layout_settingback.setBackgroundResource(R.drawable.back4);
		} else if (userbackindex.equals("5")) {
			layout_settingback.setBackgroundResource(R.drawable.back5);
		} else if (userbackindex.equals("6")) {
			layout_settingback.setBackgroundResource(R.drawable.back6);
		} else if (userbackindex.equals("7")) {
			layout_settingback.setBackgroundResource(R.drawable.back7);
		} else if (userbackindex.equals("8")) {
			layout_settingback.setBackgroundResource(R.drawable.back8);
		} else if (userbackindex.equals("9")) {
			layout_settingback.setBackgroundResource(R.drawable.back9);
		} else if (userbackindex.equals("10")) {
			layout_settingback.setBackgroundResource(R.drawable.back10);
		}

		mLoader.displayImage(
				MyUserManager.getInstance().getMyUserData().userImg,
				imageViewProfile);

		textViewuserEmail
				.setText(MyUserManager.getInstance().getMyUserData().userEmail);
		boolean ispush = PropertyManager.getInstnace().getisIspush();
		if (ispush)
			checkBox_gcm.setChecked(true);
		else
			checkBox_gcm.setChecked(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			finish();
			overridePendingTransition(0, 0);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class TestAdapter extends ArrayAdapter<Integer> {

		LayoutInflater mInflater;
		int mResource;

		public TestAdapter(Context context, int resourceId) {
			super(context, resourceId);
			mInflater = LayoutInflater.from(context);
			mResource = resourceId;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public Integer getItem(int position) {
			// TODO Auto-generated method stub
			return super.getItem(position);
		}

		@Override
		public long getItemId(int position) {
			return getItem(position).hashCode();
		}

		@Override
		public int getItemViewType(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (null == convertView) {
				convertView = mInflater.inflate(mResource, parent, false);
			}

			ImageView image = (ImageView) convertView.findViewById(R.id.image);
			image.setImageResource(getItem(position));

			LayoutParams params = convertView.getLayoutParams();
			params.width = getResources().getDimensionPixelSize(
					R.dimen.item_size_1);

			return convertView;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_CROP && resultCode == RESULT_OK) {
			bm = BitmapFactory.decodeFile(mSavedFile.getAbsolutePath());
			NetworkManager.getInstnace().changeProfile(
					SettingMainActivity.this, mSavedFile,
					new OnResultListener<UserImageChangeResult>() {

						@Override
						public void onSuccess(
								UserImageChangeResult dataFromServer) {
							// TODO Auto-generated method stub
							MyUserManager.getInstance().setMyUserImg(
									dataFromServer.user.userImg);
							imageViewProfile.setImageBitmap(bm);
							Log.i("mytesting", MyUserManager.getInstance()
									.getMyUserData().userImg);
							// mLoader.displayImage(MyUserManager.getInstance().getMyUserData().userImg,
							// imageViewProfile);
							// mLoader.displayImage(dataFromServer.user.userImg,
							// imageViewProfile);
						}

						@Override
						public void onFail(int code) {
							// TODO Auto-generated method stub

						}
					});

		}
	}

	private Uri getTempUri() {
		mSavedFile = new File(Environment.getExternalStorageDirectory(),
				"temp_" + System.currentTimeMillis() / 1000);
		return Uri.fromFile(mSavedFile);
	}

	@Override
	public View onCreateView(String name, @NonNull Context context,
			@NonNull AttributeSet attrs) {

		View view = super.onCreateView(name, context, attrs);
		if (view == null && name.equals("TextView")) {
			TextView tv = new TextView(context, attrs);
			tv.setTypeface(FontManager.getInstance().getTypeface(context,
					FontManager.getInstance().FONT_NAME_NOTOSANS));
			view = tv;
		}

		if (view == null && name.equals("Button")) {
			Button btn = new Button(context, attrs);
			btn.setTypeface(FontManager.getInstance().getTypeface(context,
					FontManager.getInstance().FONT_NAME_NOTOSANS));
			view = btn;
		}

		if (view == null && name.equals("EditText")) {
			EditText et = new EditText(context, attrs);
			et.setTypeface(FontManager.getInstance().getTypeface(context,
					FontManager.getInstance().FONT_NAME_NOTOSANS));
			view = et;
		}
		return view;

	}
}
