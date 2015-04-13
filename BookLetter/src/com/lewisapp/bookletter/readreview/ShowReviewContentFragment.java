package com.lewisapp.bookletter.readreview;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.lewisapp.bookletter.BasicResult;
import com.lewisapp.bookletter.DataManager;
import com.lewisapp.bookletter.MyUserManager;
import com.lewisapp.bookletter.NetworkManager;
import com.lewisapp.bookletter.NetworkManager.OnResultListener;
import com.lewisapp.bookletter.R;
import com.lewisapp.bookletter.comment_list.CommentAdapter;
import com.lewisapp.bookletter.comment_list.CommentAdapter.OnAdapterItemClickListener;
import com.lewisapp.bookletter.comment_list.CommentItemData;
import com.lewisapp.bookletter.dialog.DeleteCommentDialogFragmentyet;
import com.lewisapp.bookletter.dialog.DeleteCommentDialogFragmentyet.OnCommentDeleteClickListener;
import com.lewisapp.bookletter.dialog.DeleteFollowerDialogFragmentyet;
import com.lewisapp.bookletter.dialog.DeleteFollowerDialogFragmentyet.OnFollowerDeleteClickListener;
import com.lewisapp.bookletter.dialog.DeleteReviewDialogFragmentyet;
import com.lewisapp.bookletter.dialog.DeleteReviewDialogFragmentyet.OnReviewDeleteClickListener;
import com.lewisapp.bookletter.dialog.NeedLoginMyPageFragment;
import com.lewisapp.bookletter.otheruserreviewlist.OtherReviewListActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShowReviewContentFragment extends Fragment {

	ListView listView_reveiews;
	CommentAdapter comAdapter;
	Button btn;

	ImageView imageview_high;
	ImageView imageview_mid;
	ImageView imageview_low;
	TextView textViewKeyword1;
	TextView textViewKeyword2;
	TextView textViewKeyword3;

	TextView textViewToTitle;
	TextView textViewRevContent;

	TextView textViewWriter;

	TextView textViewDate;
	TextView textViewLikeCount;

	Button buttonLike;
	Button buttonFollow;
	ImageButton buttonModify;
	ImageButton buttonDelete;
	ImageButton buttonShare;

	ImageView userImage;
	ImageLoader mLoader;
	String revNum;

	LinearLayout layoutOther;
	LinearLayout layoutOwn;
	FrameLayout layoutCover;

	LinearLayout layoutGoother;
	EditText editText_commentContent;
	Button button_writeComment;

	CheckBox checkbox_like;
	CheckBox checkbox_follower;
	ReviewDetailData reviewDetail;

	String isLike;
	String isFollower;
	public static final int REQUESTCODE_REVIEWMODIFY = 108;

	private UiLifecycleHelper uiHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		comAdapter = new CommentAdapter(getActivity());

		uiHelper = new UiLifecycleHelper(getActivity(), null);
		uiHelper.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_show_review_content,
				container, false);
		mLoader = ImageLoader.getInstance();
		revNum = getArguments().getString("revNum");
		final InputMethodManager mInputMethodManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		listView_reveiews = (ListView) view.findViewById(R.id.listView_comment);
		View contentHeaderView = getLayoutInflater(getArguments()).inflate(
				R.layout.headerview_reviewcontent, null);
		layoutCover = (FrameLayout) contentHeaderView
				.findViewById(R.id.layout_cover);
		layoutGoother = (LinearLayout) contentHeaderView
				.findViewById(R.id.layoutgoother);
		layoutGoother.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!reviewDetail.userNum.equals(MyUserManager.getInstance()
						.getMyUserData().userNum)) {
					Intent intent = new Intent(getActivity(),
							OtherReviewListActivity.class);
					intent.putExtra("userNum", reviewDetail.userNum);
					startActivity(intent);
				}
			}
		});
		imageview_high = (ImageView) contentHeaderView
				.findViewById(R.id.imageView_high);
		imageview_mid = (ImageView) contentHeaderView
				.findViewById(R.id.imageView_mid);
		imageview_low = (ImageView) contentHeaderView
				.findViewById(R.id.imageView_low);

		textViewKeyword1 = (TextView) contentHeaderView
				.findViewById(R.id.textView_keyword1);
		textViewKeyword2 = (TextView) contentHeaderView
				.findViewById(R.id.textView_keyword2);
		textViewKeyword3 = (TextView) contentHeaderView
				.findViewById(R.id.textView_keyword3);
		textViewToTitle = (TextView) contentHeaderView
				.findViewById(R.id.textView_To);
		textViewRevContent = (TextView) contentHeaderView
				.findViewById(R.id.textView_reviewcontent);
		textViewWriter = (TextView) contentHeaderView
				.findViewById(R.id.textView_writer);
		textViewDate = (TextView) contentHeaderView
				.findViewById(R.id.textView_revdate);
		textViewLikeCount = (TextView) contentHeaderView
				.findViewById(R.id.textView_likecount);
		checkbox_follower = (CheckBox) contentHeaderView
				.findViewById(R.id.checkBox_follower);
		checkbox_like = (CheckBox) contentHeaderView
				.findViewById(R.id.checkBox_like);

		userImage = (ImageView) contentHeaderView
				.findViewById(R.id.imageView_userimg);

		checkbox_like = (CheckBox) contentHeaderView
				.findViewById(R.id.checkBox_like);
		checkbox_follower = (CheckBox) contentHeaderView
				.findViewById(R.id.checkBox_follower);

		buttonModify = (ImageButton) contentHeaderView
				.findViewById(R.id.button_modify);
		buttonDelete = (ImageButton) contentHeaderView
				.findViewById(R.id.button_delete);
		buttonShare = (ImageButton) contentHeaderView
				.findViewById(R.id.button_share);

		buttonModify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent modifyIntent = new Intent(getActivity(),
						ReviewDetailModifyActivity.class);
				modifyIntent.putExtra("revNum", revNum);
				startActivityForResult(modifyIntent, REQUESTCODE_REVIEWMODIFY);

			}
		});

		buttonDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DeleteReviewDialogFragmentyet dialog = new DeleteReviewDialogFragmentyet();
				dialog.show(getChildFragmentManager(), "리뷰삭제");

				dialog.setOnReviewdeleteListener(new OnReviewDeleteClickListener() {

					@Override
					public void onReviewDeleteClick() {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub
						NetworkManager.getInstnace().deleteReview(
								getActivity(), revNum,
								new OnResultListener<BasicResult>() {

									@Override
									public void onSuccess(
											BasicResult dataFromServer) {
										// TODO Auto-generated method stub
										Log.i("testing", dataFromServer.success
												+ " 메시지 "
												+ dataFromServer.message);
										if (dataFromServer.success.equals("1")) {
											getActivity().setResult(
													Activity.RESULT_OK);
											getActivity().finish();

										}

									}

									@Override
									public void onFail(int code) {
										// TODO Auto-generated method stub
										Log.i("testing", "실패했다");
									}
								});
					}
				});

			}
		});

		buttonShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// if (isLogin()) {
				// Session.getActiveSession().addCallback(postCallback);
				// postCallback.call(Session.getActiveSession(), null, null);
				// } else {
				// login(postCallback);
				// }

				FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
						getActivity()).setName("To "+reviewDetail.revTitle)

				.setCaption("사용자 경험기반 책 추천 서비스  북 레터")
						.setDescription(reviewDetail.revContent)
						.setPicture(reviewDetail.bookImg)
						.setLink("http://naver.com").build();
				uiHelper.trackPendingDialogCall(shareDialog.present());

			}
		});
		layoutOther = (LinearLayout) contentHeaderView
				.findViewById(R.id.layout_other_buttons);

		layoutOwn = (LinearLayout) contentHeaderView
				.findViewById(R.id.layout_my_buttons);

		layoutOther.setVisibility(View.GONE);
		layoutOwn.setVisibility(View.GONE);
		layoutCover.setVisibility(View.GONE);

		View commentHeaderView = getLayoutInflater(getArguments()).inflate(
				R.layout.headerview_comment_write, null);

		editText_commentContent = (EditText) commentHeaderView
				.findViewById(R.id.editText_commentcontent);
		button_writeComment = (Button) commentHeaderView
				.findViewById(R.id.button_writecomment);
		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		editText_commentContent
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						// TODO Auto-generated method stub
						if (actionId == EditorInfo.IME_ACTION_SEND) {
							// TODO Auto-generated method stub
							final String commentContent = editText_commentContent
									.getText().toString();
							NetworkManager.getInstnace().writeComment(
									getActivity(), revNum, commentContent,
									new OnResultListener<BasicResult>() {

										@Override
										public void onSuccess(
												BasicResult dataFromServer) {
											// TODO Auto-generated method stub

											if (dataFromServer.success
													.equals("1")) {
												mInputMethodManager
														.hideSoftInputFromWindow(
																editText_commentContent
																		.getWindowToken(),
																0);
												init();

												editText_commentContent
														.setText("");

											}
										}

										@Override
										public void onFail(int code) {
											// TODO Auto-generated method stub

										}
									});
						}
						return false;
					}
				});

		button_writeComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (MyUserManager.getInstance().isLogin()) {

					// TODO Auto-generated method stub
					final String commentContent = editText_commentContent
							.getText().toString();
					NetworkManager.getInstnace().writeComment(getActivity(),
							revNum, commentContent,
							new OnResultListener<BasicResult>() {

								@Override
								public void onSuccess(BasicResult dataFromServer) {
									// TODO Auto-generated method stub

									if (dataFromServer.success.equals("1")) {

										mInputMethodManager.hideSoftInputFromWindow(
												editText_commentContent
														.getWindowToken(), 0);
										init();
										editText_commentContent.setText("");
										listView_reveiews
												.setSelection(comAdapter
														.getCount());
									}
								}

								@Override
								public void onFail(int code) {
									// TODO Auto-generated method stub

								}
							});

				} else {
					NeedLoginMyPageFragment dialog = new NeedLoginMyPageFragment();
					dialog.show(getChildFragmentManager(), "로그인");
				}

			}
		});

		checkbox_follower.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!MyUserManager.getInstance().isLogin()) {
					NeedLoginMyPageFragment dialog = new NeedLoginMyPageFragment();
					dialog.show(getChildFragmentManager(), "로그인");
				}
			}
		});
		checkbox_like.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!MyUserManager.getInstance().isLogin()) {
					NeedLoginMyPageFragment dialog = new NeedLoginMyPageFragment();
					dialog.show(getChildFragmentManager(), "로그인");
				}
			}
		});
		checkbox_like.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (MyUserManager.getInstance().isLogin()) {
					// TODO Auto-generated method stub
					if (buttonView.getId() == R.id.checkBox_like) {
						if (isChecked) {
							if (!isLike.equals("1")) {

								NetworkManager.getInstnace().clickLike(
										getActivity(), revNum, isLike,
										new OnResultListener<LikeResult>() {

											@Override
											public void onSuccess(
													LikeResult dataFromServer) {
												// TODO Auto-generated method
												// stub
												isLike = dataFromServer.like.likeState;
												textViewLikeCount
														.setText(dataFromServer.like.likeCnt
																+ "");

											}

											@Override
											public void onFail(int code) {
												// TODO Auto-generated method
												// stub

											}
										});
							}
						} else {
							if (!isLike.equals("0")) {

								NetworkManager.getInstnace().clickLike(
										getActivity(), revNum, isLike,
										new OnResultListener<LikeResult>() {

											@Override
											public void onSuccess(
													LikeResult dataFromServer) {
												// TODO Auto-generated method
												// stub
												isLike = dataFromServer.like.likeState;

												textViewLikeCount
														.setText(dataFromServer.like.likeCnt
																+ "");

											}

											@Override
											public void onFail(int code) {
												// TODO Auto-generated method
												// stub

											}
										});
							}
						}
					}

				}
			}
		});

		checkbox_follower
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (MyUserManager.getInstance().isLogin()) {
							// TODO Auto-generated method stub
							if (buttonView.getId() == R.id.checkBox_follower) {
								if (isChecked) {

									if (!isFollower.equals("1")) {
										String userNum2 = reviewDetail.userNum;

										NetworkManager
												.getInstnace()
												.clickFollow(
														getActivity(),
														userNum2,
														isFollower,
														new OnResultListener<FollowStateResult>() {

															@Override
															public void onSuccess(

																	FollowStateResult dataFromServer) {
																// TODO
																// Auto-generated
																// method stub

																isFollower = dataFromServer.follow.followState;

															}

															@Override
															public void onFail(
																	int code) {
																// TODO
																// Auto-generated
																// method stub

															}
														});

									}
								} else {

									DeleteFollowerDialogFragmentyet dialog = new DeleteFollowerDialogFragmentyet();
									dialog.show(getChildFragmentManager(),
											"댓글삭제");
									dialog.setOnFollowerdeleteListener(new OnFollowerDeleteClickListener() {

										@Override
										public void onFollowerDeleteClick() {
											// TODO Auto-generated method stub
											if (!isFollower.equals("0")) {
												String userNum2 = reviewDetail.userNum;

												NetworkManager
														.getInstnace()
														.clickFollow(
																getActivity(),
																userNum2,
																isFollower,
																new OnResultListener<FollowStateResult>() {

																	@Override
																	public void onSuccess(
																			FollowStateResult dataFromServer) {
																		// TODO
																		// Auto-generated
																		// method
																		// stub
																	
																		isFollower = dataFromServer.follow.followState;

																	}

																	@Override
																	public void onFail(
																			int code) {
																		// TODO
																		// Auto-generated
																		// method
																		// stub

																	}
																});
											}
										}

										@Override
										public void onFollowerDeleteCancleClick() {
											// TODO Auto-generated method stub
											checkbox_follower.setChecked(true);
										}
									});

								}
							}
						}

					}
				});

		listView_reveiews.addHeaderView(contentHeaderView);
		listView_reveiews.addHeaderView(commentHeaderView);

		listView_reveiews.setAdapter(comAdapter);

		// tmpcode
		//
		// userImage.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (DataManager.getInstance().myMode) {
		// layoutOwn.setVisibility(View.VISIBLE);
		// layoutOther.setVisibility(View.GONE);
		// DataManager.getInstance().myMode = false;
		// } else {
		// layoutOwn.setVisibility(View.GONE);
		// layoutOther.setVisibility(View.VISIBLE);
		// DataManager.getInstance().myMode = true;
		// }
		// }
		// });

		listView_reveiews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
			}
		});

		comAdapter
				.setOnAdapterItemClickListener(new OnAdapterItemClickListener() {

					@Override
					public void onAdapterItemClick(CommentAdapter adapter,
							View view, final CommentItemData item) {

						DeleteCommentDialogFragmentyet dialog = new DeleteCommentDialogFragmentyet();
						dialog.show(getChildFragmentManager(), "댓글삭제");
						
						
						dialog.setOnCommentdeleteListener(new OnCommentDeleteClickListener() {

							@Override
							public void onCommentDeleteClick() {
								// TODO Auto-generated method stub
								NetworkManager.getInstnace().deleteComment(
										getActivity(), revNum, item.comNum,
										new OnResultListener<BasicResult>() {

											@Override
											public void onSuccess(
													BasicResult dataFromServer) {
												// TODO Auto-generated method
												// stub
												init();

											}

											@Override
											public void onFail(int code) {
												Toast.makeText(getActivity(),
														"실패 ", 0).show();
												// TODO Auto-generated method
												// stub
											}
										});
							}
						});

						// TODO Auto-generated method stub

					}
				});

		// layoutOther.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// if (!MyUserManager.getInstance().isLogin()) {
		// NeedLoginMyPageFragment dialog = new NeedLoginMyPageFragment();
		// dialog.show(getChildFragmentManager(), "로그인");
		//
		// }
		// }
		// });

		layoutCover.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!MyUserManager.getInstance().isLogin()) {
					NeedLoginMyPageFragment dialog = new NeedLoginMyPageFragment();
					dialog.show(getChildFragmentManager(), "로그인");

				}
			}
		});

//		init();

		return view;
	}
	
	

	private void init() {
		NetworkManager.getInstnace().getDetailReview(getActivity(), revNum,
				new OnResultListener<ReviewDetailResult>() {

					@Override
					public void onSuccess(

					ReviewDetailResult detailReviewCommenttItem) {
						// TODO Auto-generated method stub

						if (detailReviewCommenttItem.success.equals("1")) {
							reviewDetail = detailReviewCommenttItem.review;
							// 이제 여기서 적용 해준다.

							textViewKeyword1.setText("#" + reviewDetail.key1);
							if (reviewDetail.key2 != null)
								textViewKeyword2.setText("#"
										+ reviewDetail.key2);
							if (reviewDetail.key3 != null)
								textViewKeyword3.setText("#"
										+ reviewDetail.key3);
							textViewToTitle.setText("To."
									+ reviewDetail.revTitle);
							textViewRevContent.setText(reviewDetail.revContent);
							textViewWriter.setText(reviewDetail.userNick + "님");
							textViewDate.setText(DataManager.getInstance()
									.myDatetoSimpleDate(reviewDetail.revDate));

							isLike = reviewDetail.likeState;
							isFollower = reviewDetail.followState;

							if (isLike.equals("1")) {
								checkbox_like.setChecked(true);
							} else if (isLike.equals("0"))
								checkbox_like.setChecked(false);
							// 여기까지는 오케이,

							if (isFollower.equals("1")) {
								checkbox_follower.setChecked(true);
							} else if (isFollower.equals("0"))
								checkbox_follower.setChecked(false);
							textViewLikeCount
									.setText(reviewDetail.likeCnt + "");
							mLoader.displayImage(reviewDetail.userImg,
									userImage);
							if (reviewDetail.revLevel.equals("3")) {
								imageview_high.setVisibility(View.VISIBLE);
								imageview_mid.setVisibility(View.GONE);
								imageview_low.setVisibility(View.GONE);
							} else if (reviewDetail.revLevel.equals("2")) {
								imageview_high.setVisibility(View.GONE);
								imageview_mid.setVisibility(View.VISIBLE);
								imageview_low.setVisibility(View.GONE);
							} else if (reviewDetail.revLevel.equals("1")) {

								imageview_high.setVisibility(View.GONE);
								imageview_mid.setVisibility(View.GONE);
								imageview_low.setVisibility(View.VISIBLE);
							}

							((ShowReviewActivity) getActivity())
									.setISBN(reviewDetail.bookISBN);

							comAdapter.clear();
							for (CommentItemData data : detailReviewCommenttItem.comments.lists) {
								comAdapter.add(data);
							}

							if (MyUserManager.getInstance().isLogin()) {
								if (MyUserManager.getInstance().getMyUserData().userNum == reviewDetail.userNum) {
									layoutOwn.setVisibility(View.VISIBLE);
									layoutOther.setVisibility(View.GONE);
					
								} else {
									layoutOther.setVisibility(View.VISIBLE);
									layoutOwn.setVisibility(View.GONE);
							
								}
								layoutCover.setVisibility(View.GONE);

							} else {
								checkbox_follower.setChecked(false);
								checkbox_like.setChecked(false);
								layoutOther.setVisibility(View.VISIBLE);
								layoutOwn.setVisibility(View.GONE);
								checkbox_follower.setEnabled(false);
								checkbox_like.setEnabled(false);
								checkbox_follower.setFocusable(false);
								checkbox_like.setFocusable(false);
								layoutCover.setVisibility(View.VISIBLE);
							}
						} else {
							Log.i("testing", detailReviewCommenttItem.success
									+ "");

						}

					}

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub

					}
				});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE_REVIEWMODIFY
				&& resultCode == Activity.RESULT_OK) {
			getActivity().setResult(Activity.RESULT_OK);
			init();
		}
		if (Session.getActiveSession() != null) {
			Session.getActiveSession().onActivityResult(getActivity(),
					requestCode, resultCode, data);
		}

		uiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {
					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						Log.e("Activity",
								String.format("Error: %s", error.toString()));
					}

					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						Log.i("Activity", "Success!");
					}
				});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!MyUserManager.getInstance().isLogin()) {
			checkbox_follower.setChecked(false);
			checkbox_like.setChecked(false);
		}
		listView_reveiews.setSelection(0);
		uiHelper.onResume();
		init();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	private boolean isLogin() {
		return Session.getActiveSession() != null
				&& Session.getActiveSession().isOpened();
	}

	private void login(StatusCallback callback) {
		Session session = Session.getActiveSession(); // 현재 내가 로그인을 유지하고있는 세션이
														// 있는지

		if (session == null) { // 없다면
			session = Session.openActiveSessionFromCache(getActivity()); // 파일에서
																			// 얻어서
																			// 세션을
			// 만들어 달라
		}

		if (session != null && session.isOpened()) { // 자동로그인
			callback.call(session, null, null);
			return;
		}

		Session.openActiveSession(getActivity(), this, true, callback); // 문제가되는
																		// 경우에
																		// 대해서
																		// 다시
																		// 로그인
		// 시킨다.( 캐시를 다시만들다)
	}

	public static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");

	private boolean isSubsetOf(Collection<String> subset,
			Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}

	Session.StatusCallback postCallback = new Session.StatusCallback() {

		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (session.isOpened()) {
				List<String> permissions = session.getPermissions();
				if (!isSubsetOf(PERMISSIONS, permissions)) {
					session.requestNewPublishPermissions(new NewPermissionsRequest(
							ShowReviewContentFragment.this, PERMISSIONS));
					return;
				}
				Bundle postParams = new Bundle();
				postParams.putString("name", reviewDetail.revTitle);
				postParams.putString("caption", reviewDetail.revDate
						+ " BookLetter");
				postParams.putString("description", reviewDetail.revContent);
				postParams.putString("link",
						"https://developers.facebook.com/android");
				postParams.putString("picture", reviewDetail.bookImg);
				Request request = new Request(session, "me/feed", postParams,
						HttpMethod.POST, new Request.Callback() {

							@Override
							public void onCompleted(Response response) {
								if (response.getGraphObject() != null) {
									JSONObject obj = response.getGraphObject()
											.getInnerJSONObject();
									try {
										String id = obj.getString("id");
										Toast.makeText(getActivity(),
												"id : " + id,
												Toast.LENGTH_SHORT).show();
									} catch (JSONException e) {
										e.printStackTrace();
									}
								} else {
									FacebookRequestError error = response
											.getError();
									Toast.makeText(
											getActivity(),
											"error : "
													+ error.getErrorMessage(),
											Toast.LENGTH_SHORT).show();
								}

							}
						});
				request.executeAsync();

			}
		}
	};
}
