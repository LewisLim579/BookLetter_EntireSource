package com.lewisapp.bookletter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import android.content.Context;

import com.begentgroup.xmlparser.XMLParser;
import com.google.gson.Gson;
import com.lewisapp.bookletter.event.EventDetailResult;
import com.lewisapp.bookletter.event.EventResult;
import com.lewisapp.bookletter.myfollower_list.FollowerResult;
import com.lewisapp.bookletter.otheruserreviewlist.ResultOtherUseReviews;
import com.lewisapp.bookletter.readreview.FollowStateResult;
import com.lewisapp.bookletter.readreview.LikeResult;
import com.lewisapp.bookletter.readreview.ReviewDetailResult;
import com.lewisapp.bookletter.readreview.ReviewModifyResult;
import com.lewisapp.bookletter.request_list.ResultRequest;
import com.lewisapp.bookletter.requestreview.RequestDetailResult;
import com.lewisapp.bookletter.requestreview.RequestModifyResult;
import com.lewisapp.bookletter.requestreview.RequestResult;
import com.lewisapp.bookletter.review_list.ResultReview;
import com.lewisapp.bookletter.searchbook_list.Book;
import com.lewisapp.bookletter.setting.BackGroundResult;
import com.lewisapp.bookletter.setting.UserImageChangeResult;
import com.lewisapp.bookletter.wordcloud.KeywordResult;
import com.lewisapp.bookletter.writereview.WriteResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class NetworkManager {
	private static NetworkManager instance;

	public static NetworkManager getInstnace() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}

	AsyncHttpClient client;

	private NetworkManager() {

		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);
			MySSLSocketFactory socketFactory = new MySSLSocketFactory(
					trustStore);
			socketFactory
					.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			client = new AsyncHttpClient();
			client.setSSLSocketFactory(socketFactory);
			client.setCookieStore(new PersistentCookieStore(MyApplication
					.getContext()));
			client.setTimeout(30000);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
	}

	public HttpClient getHttpClient() {
		return client.getHttpClient();
	}

	public interface OnResultListener<T> {
		public void onSuccess(T dataFromServer);

		public void onFail(int code);
	}

	public static final String NAVERSERVERURL = "http://openapi.naver.com";
	public static final String NAVERBOOKURL = NAVERSERVERURL + "/search";

	public void getNaverBook(Context context, String keyword, int start,
			final OnResultListener<Book> listener) {
		RequestParams params = new RequestParams();
		params.put("key", "93ec004ad677bdf7e2140f9e84421a33");
		params.put("target", "book");
		params.put("query", keyword);
		params.put("display", "10");
		params.put("start", start + "");

		client.get(context, NAVERBOOKURL, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						// TODO Auto-generated method stub
						InputStream is = new ByteArrayInputStream(responseBody);
						XMLParser xmlParser = new XMLParser();
						Book book = xmlParser
								.fromXml(is, "channel", Book.class);

						listener.onSuccess(book);

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub
						listener.onFail(statusCode);

					}
				});
	}

	Gson gson = new Gson();
	public static final String SERVER = "https://54.64.26.191";

	public static final String REVIEWLIST = SERVER + "/review";

	public void getReviewList(Context context, String keyword, String order,
			int page, String search,
			final OnResultListener<ResultReview> listener) {

		RequestParams params = new RequestParams();
		if (keyword != null)
			params.put("keyword", keyword);

		params.put("order", order);
		params.put("page", page + "");
		if (search != null)
			params.put("search", search);

		client.post(REVIEWLIST, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				ResultReview result = gson.fromJson(responseString,
						ResultReview.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String EDITKEYWORD = SERVER + "/setting/edit";

	public void editKeyword(Context context, String userNick,
			final OnResultListener<UserResult> listener) {

		RequestParams params = new RequestParams();
		params.put("userNick", userNick);

		client.post(EDITKEYWORD, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				UserResult result = gson.fromJson(responseString,
						UserResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String CHANGEPROFILE = SERVER
			+ "/setting/changeprofile";

	public void changeProfile(Context context, File profile,
			final OnResultListener<UserImageChangeResult> listener) {

		RequestParams params = new RequestParams();
		try {
			params.put("profile", profile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.post(CHANGEPROFILE, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				UserImageChangeResult result = gson.fromJson(responseString,
						UserImageChangeResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String CHANGEPASSWORD = SERVER
			+ "/setting/changepassword";

	public void changePassword(Context context, String userEmail,
			final OnResultListener<BasicResult> listener) {

		RequestParams params = new RequestParams();

		params.put("userEmail", userEmail);

		client.post(CHANGEPASSWORD, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				BasicResult result = gson.fromJson(responseString,
						BasicResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String EDITBACKGROUND = SERVER
			+ "/setting/changebackground";

	public void editBackground(Context context, String userBackground,
			final OnResultListener<BackGroundResult> listener) {

		RequestParams params = new RequestParams();
		params.put("userBackground", userBackground);

		client.post(EDITBACKGROUND, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				BackGroundResult result = gson.fromJson(responseString,
						BackGroundResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String SIGNUP = SERVER + "/setting/signup";

	public void signUp(Context context, String userNick, String userEmail,
			String userPw, String userSex, String userImg, String userGcm,
			final OnResultListener<UserResult> listener) {

		RequestParams params = new RequestParams();
		params.put("userNick", userNick);
		params.put("userEmail", userEmail);
		params.put("userPw", userPw);
		params.put("userSex", userSex);
		params.put("userGcm", userGcm);
		// params.put("userImg", userImg);

		client.post(SIGNUP, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				UserResult result = gson.fromJson(responseString,
						UserResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String SIGNUPFACEBOOK = SERVER
			+ "/setting/signupfacebook";

	public void signUpFacebook(Context context, String userNick,
			String userEmail, String userSex, String userImg, String userGcm,
			final OnResultListener<UserResult> listener) {

		RequestParams params = new RequestParams();
		params.put("userNick", userNick);
		params.put("userEmail", userEmail);
		params.put("userSex", userSex);
		params.put("userImg", userImg);
		params.put("userGcm", userGcm);

		client.post(SIGNUPFACEBOOK, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				UserResult result = gson.fromJson(responseString,
						UserResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String LOGIN = SERVER + "/setting/login";// 임시

	public void login(Context context, String userEmail, String userPw,
			String userGcm, final OnResultListener<UserResult> listener) {

		RequestParams params = new RequestParams();
		params.put("userEmail", userEmail);
		params.put("userPw", userPw);
		params.put("userGcm", userGcm);

		client.post(LOGIN, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				UserResult result = gson.fromJson(responseString,
						UserResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String LOGINFACEBOOK = SERVER
			+ "/setting/loginfacebook";// 임시

	public void loginFacebook(Context context, String accessToken,
			String userGcm, final OnResultListener<UserResult> listener) {

		RequestParams params = new RequestParams();
		params.put("userGcm", userGcm);
		params.put("accessToken", accessToken);
		params.put("DB", "1");

		client.post(LOGINFACEBOOK, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub

				UserResult result = gson.fromJson(responseString,
						UserResult.class);
				listener.onSuccess(result);

				//

			}

		});

	}

	public static final String LOGOUT = SERVER + "/setting/logout";// 임시

	public void logout(Context context,
			final OnResultListener<UserResult> listener) {

		RequestParams params = new RequestParams();

		client.post(LOGOUT, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				UserResult result = gson.fromJson(responseString,
						UserResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String KEYWORDS = SERVER + "/keyword";

	public void getTotalKeywords(Context context,
			final OnResultListener<KeywordResult> listener) {

		RequestParams params = new RequestParams();

		client.post(KEYWORDS, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				KeywordResult result = gson.fromJson(responseString,
						KeywordResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String REVIEWDETAIL = SERVER + "/review/detail";

	public void getDetailReview(Context context, String revNum,
			final OnResultListener<ReviewDetailResult> listener) {

		RequestParams params = new RequestParams();

		// params.put("revNum", "1");
		params.put("revNum", revNum);

		client.post(REVIEWDETAIL, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				ReviewDetailResult result = gson.fromJson(responseString,
						ReviewDetailResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String REVIEWMODIFY = SERVER + "/review/editform";

	public void modifyReviewFrom(Context context, String revNum,
			final OnResultListener<ReviewModifyResult> listener) {

		RequestParams params = new RequestParams();

		params.put("revNum", revNum);

		client.post(REVIEWMODIFY, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				ReviewModifyResult result = gson.fromJson(responseString,
						ReviewModifyResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String REVIEWMODIFYSAVE = SERVER + "/review/edit";

	public void modifyReviewSave(Context context, String revNum,
			String revTitle, String revContent, String revLevel, String key1,
			String key2, String key3, String bookISBN, String bookTitle,
			String bookImg, String bookAuth,
			final OnResultListener<BasicResult> listener) {

		RequestParams params = new RequestParams();

		params.put("revNum", revNum);
		params.put("revTitle", revTitle);
		params.put("revContent", revContent);
		params.put("revLevel", revLevel);
		params.put("key1", key1);
		if (key2 != null)
			params.put("key2", key2);
		if (key3 != null)
			params.put("key3", key3);

		params.put("bookISBN", bookISBN);

		params.put("bookTitle", bookTitle);

		params.put("bookImg", bookImg);

		params.put("bookAuth", bookAuth);

		client.post(REVIEWMODIFYSAVE, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				BasicResult result = gson.fromJson(responseString,
						BasicResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String REVIEWDELETE = SERVER + "/review/del";

	public void deleteReview(Context context, String revNum,
			final OnResultListener<BasicResult> listener) {

		RequestParams params = new RequestParams();

		// params.put("revNum", "1");
		params.put("revNum", revNum);

		client.post(REVIEWDELETE, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				BasicResult result = gson.fromJson(responseString,
						BasicResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String REVIEWLIKE = SERVER + "/review/like/click";

	public void clickLike(Context context, String revNum, String likeState,
			final OnResultListener<LikeResult> listener) {

		RequestParams params = new RequestParams();

		// params.put("revNum", "1");
		params.put("revNum", revNum);
		params.put("likeState", likeState);

		client.post(REVIEWLIKE, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				LikeResult result = gson.fromJson(responseString,
						LikeResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String USERFOLLOW = SERVER + "/review/follow/click";

	public void clickFollow(Context context, String userNum2,
			String followState,
			final OnResultListener<FollowStateResult> listener) {

		RequestParams params = new RequestParams();

		// params.put("revNum", "1");
		params.put("userNum2", userNum2);
		params.put("followState", followState);

		client.post(USERFOLLOW, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub

				FollowStateResult result = gson.fromJson(responseString,
						FollowStateResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String WRITECOMMENT = SERVER + "/review/comment";

	public void writeComment(Context context, String revNum, String comContent,
			final OnResultListener<BasicResult> listener) {

		RequestParams params = new RequestParams();

		// params.put("revNum", "1");
		params.put("revNum", revNum);
		params.put("comContent", comContent);

		client.post(WRITECOMMENT, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				BasicResult result = gson.fromJson(responseString,
						BasicResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String DELETECOMMENT = SERVER + "/review/comment/del";

	public void deleteComment(Context context, String revNum, String comNum,
			final OnResultListener<BasicResult> listener) {

		RequestParams params = new RequestParams();

		// params.put("revNum", "1");
		params.put("revNum", revNum);
		params.put("comNum", comNum);

		client.post(DELETECOMMENT, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				BasicResult result = gson.fromJson(responseString,
						BasicResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String WRITEREVIEW = SERVER + "/review/write";

	public void WriteReview(Context context, String revTitle,
			String revContent, String revLevel, String key1, String key2,
			String key3, String bookISBN, String bookTitle, String bookImg,
			String bookAuth, String reqNum,
			final OnResultListener<WriteResult> listener) {

		RequestParams params = new RequestParams();

		params.put("revTitle", revTitle);
		params.put("revContent", revContent);
		params.put("revLevel", revLevel);
		params.put("key1", key1);
		params.put("key2", key2);
		params.put("key3", key3);
		params.put("bookISBN", bookISBN);
		params.put("bookTitle", DataManager.getInstance()
				.myfromHtmltoStringTitle(bookTitle));
		params.put("bookImg", bookImg);
		params.put("bookAuth", DataManager.getInstance()
				.myfromHtmltoStringTitle(bookAuth));
		params.put("reqNum", reqNum);

		client.post(WRITEREVIEW, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				WriteResult result = gson.fromJson(responseString,
						WriteResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String WRITEREQUEST = SERVER + "/reqreview/write";

	public void WriteRequest(Context context, String reqTitle,
			String reqContent, final OnResultListener<RequestResult> listener) {

		RequestParams params = new RequestParams();

		params.put("reqTitle", reqTitle);
		params.put("reqContent", reqContent);

		client.post(WRITEREQUEST, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				RequestResult result = gson.fromJson(responseString,
						RequestResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String REQUESTLIST = SERVER + "/reqreview";

	public void getTotalRequest(Context context, int page,
			final OnResultListener<ResultRequest> listener) {

		RequestParams params = new RequestParams();
		params.put("page", page + "");

		client.post(REQUESTLIST, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				ResultRequest result = gson.fromJson(responseString,
						ResultRequest.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String REQUESTMODIFY = SERVER + "/reqreview/editform";

	public void modifyReqeust(Context context, String reqNum,
			final OnResultListener<RequestModifyResult> listener) {

		RequestParams params = new RequestParams();

		params.put("reqNum", reqNum);

		client.post(REQUESTMODIFY, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				RequestModifyResult result = gson.fromJson(responseString,
						RequestModifyResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String REQUESTMODIFYSAVE = SERVER + "/reqreview/edit";

	public void modifySaveReqeust(Context context, String reqNum,
			String reqTitle, String reqContent,
			final OnResultListener<BasicResult> listener) {

		RequestParams params = new RequestParams();

		params.put("reqNum", reqNum);
		params.put("reqTitle", reqTitle);
		params.put("reqContent", reqContent);

		client.post(REQUESTMODIFYSAVE, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				BasicResult result = gson.fromJson(responseString,
						BasicResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String REQUESTWDETAIL = SERVER + "/reqreview/detail";

	public void getDetailRequest(Context context, String reqNum,
			final OnResultListener<RequestDetailResult> listener) {

		RequestParams params = new RequestParams();

		params.put("reqNum", reqNum);

		client.post(REQUESTWDETAIL, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				RequestDetailResult result = gson.fromJson(responseString,
						RequestDetailResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String DELETEREQUEST = SERVER + "/reqreview/del";

	public void deleteRequest(Context context, String reqNum,
			final OnResultListener<BasicResult> listener) {

		RequestParams params = new RequestParams();

		params.put("reqNum", reqNum);

		client.post(DELETEREQUEST, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				BasicResult result = gson.fromJson(responseString,
						BasicResult.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String MYREVIEWLIST = SERVER + "/mypage/review";

	public void getMyReview(Context context, String order, int page,
			final OnResultListener<ResultReview> listener) {

		RequestParams params = new RequestParams();
		params.put("order", order);
		params.put("page", page + "");

		client.post(MYREVIEWLIST, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				ResultReview result = gson.fromJson(responseString,
						ResultReview.class);
				if (result != null) {
					listener.onSuccess(result);
				}
			}

		});

	}

	public static final String MYFOLLOWLIST = SERVER + "/mypage/follow";

	public void getMyFollow(Context context, String order,
			final OnResultListener<FollowerResult> listener) {

		RequestParams params = new RequestParams();

		params.put("order", order);

		client.post(MYFOLLOWLIST, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				FollowerResult result = gson.fromJson(responseString,
						FollowerResult.class);
				if (result != null) {
					listener.onSuccess(result);
				}
			}

		});

	}

	public static final String OTHERREVIEWLIST = SERVER
			+ "/mypage/follow/review";

	public void getOtherReviewList(Context context, String userNum,
			String order, int page,
			final OnResultListener<ResultOtherUseReviews> listener) {

		RequestParams params = new RequestParams();
		params.put("userNum", userNum);
		params.put("page", page + "");
		params.put("order", order);

		client.post(OTHERREVIEWLIST, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				ResultOtherUseReviews result = gson.fromJson(responseString,
						ResultOtherUseReviews.class);
				listener.onSuccess(result);

			}

		});

	}

	public static final String MYREQUESTLIST = SERVER + "/mypage/reqreview";

	public void getMyRequestList(Context context, int page,
			final OnResultListener<ResultRequest> listener) {

		RequestParams params = new RequestParams();
		params.put("page", page + "");

		client.post(MYREQUESTLIST, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				ResultRequest result = gson.fromJson(responseString,
						ResultRequest.class);
				if (result != null) {
					listener.onSuccess(result);
				}

			}

		});

	}

	public static final String NOTICELIST = SERVER + "/notice";

	public void getNoticeList(Context context,
			final OnResultListener<EventResult> listener) {

		RequestParams params = new RequestParams();

		client.post(NOTICELIST, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				EventResult result = gson.fromJson(responseString,
						EventResult.class);
				if (result != null) {
					listener.onSuccess(result);
				}

			}

		});

	}

	public static final String NOTICEDETAIL = SERVER + "/notice/detail";

	public void getNoticeDetail(Context context, String notNum,
			final OnResultListener<EventDetailResult> listener) {

		RequestParams params = new RequestParams();
		params.put("notNum", notNum);

		client.post(NOTICEDETAIL, params, new TextHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				// TODO Auto-generated method stub

				listener.onFail(statusCode);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				// TODO Auto-generated method stub
				EventDetailResult result = gson.fromJson(responseString,
						EventDetailResult.class);
				if (result != null) {
					listener.onSuccess(result);
				}

			}

		});

	}
	// public static final String WRITEREVIEW = SERVER + "/review/write";
	// public void putReview(Context context, String revTitle,
	// String revContent,String revLevel,String key1, String key2, String key3,
	// final OnResultListener<ResultReview> listener) {
	//
	// RequestParams params = new RequestParams();
	// params.put("page", page);
	// params.
	//
	// client.post(MYREQUESTLIST, params, new TextHttpResponseHandler() {
	//
	// @Override
	// public void onFailure(int statusCode, Header[] headers,
	// String responseString, Throwable throwable) {
	// // TODO Auto-generated method stub
	//
	// listener.onFail(statusCode);
	// }
	//
	// @Override
	// public void onSuccess(int statusCode, Header[] headers,
	// String responseString) {
	// // TODO Auto-generated method stub
	// ResultRequest result = gson.fromJson(responseString,
	// ResultRequest.class);
	// listener.onSuccess(result);
	//
	// }
	//
	// });
	//
	// }

}
