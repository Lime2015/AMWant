package com.lime.mypol.manager;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lime.mypol.application.GlobalApplication;
import com.lime.mypol.result.CheckMemberResult;
import com.lime.mypol.result.CheckTagResult;
import com.lime.mypol.vo.Assemblyman;
import com.lime.mypol.vo.Bill;
import com.lime.mypol.vo.CommitteeMeeting;
import com.lime.mypol.vo.GeneralMeeting;
import com.lime.mypol.vo.MemberInfo;
import com.lime.mypol.vo.PartyHistory;
import com.lime.mypol.vo.Vote;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;

public class NetworkManager {

	private static String TAG = "NetworkManager";

	private static NetworkManager instance;
	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	
	AsyncHttpClient client;
	private NetworkManager() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			MySSLSocketFactory socketFactory = new MySSLSocketFactory(trustStore);
			socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			client = new AsyncHttpClient();
			client.setSSLSocketFactory(socketFactory);			
			client.setCookieStore(new PersistentCookieStore(GlobalApplication.getContext()));
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
	
	public interface OnNetResultListener<T> {
		public void onSuccess(T result);
		public void onFail(int code);
	}


	final String SERVER_URL = "http://52.69.102.82:8080/WAServer";
	final String CHECK_TAG = "/checkTag.do";
	public void checkServerTag(final OnNetResultListener<CheckTagResult> listener){

		Log.d(TAG, "checkServerTag start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		AsyncHttpClient client = new AsyncHttpClient();

		String url = SERVER_URL + CHECK_TAG;
		client.post(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				InputStreamReader is = new InputStreamReader(new ByteArrayInputStream(responseBody));
				Gson gson = new GsonBuilder().create();
				CheckTagResult result = gson.fromJson(is, CheckTagResult.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				listener.onFail(statusCode);
			}
		});

	}

	final String INIT_DATABASE = "/wadb.sql";
	public void requestInitDatabase(final OnNetResultListener<File> listener){

		Log.d(TAG, "requestInitDatabase start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		AsyncHttpClient client = new AsyncHttpClient();

		String url = SERVER_URL + INIT_DATABASE;
		client.post(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				File result=new File(Environment.getExternalStorageDirectory(), "wadb.sql");

				if (result.exists()) {
					result.delete();
				}

				try {
					FileOutputStream fos=new FileOutputStream(result.getPath());
					fos.write(responseBody);
					fos.close();
				}
				catch (java.io.IOException e) {
					listener.onFail(-999);
					return;
				}


				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				listener.onFail(statusCode);
			}
		});

	}

	final String CHECK_MEMBER = "/checkMember.do";
	public void checkMemberInServer(MemberInfo kakaoMemberInfo, final OnNetResultListener<CheckMemberResult> listener){

		Log.d(TAG, "checkMemberInServer start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		Gson gson = new GsonBuilder().create();
		params.put("memberJSON", gson.toJson(kakaoMemberInfo));

		String url = SERVER_URL + CHECK_MEMBER;
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				InputStreamReader is = new InputStreamReader(new ByteArrayInputStream(responseBody));
				Gson gson = new GsonBuilder().create();
				CheckMemberResult result = gson.fromJson(is, CheckMemberResult.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				listener.onFail(statusCode);
			}
		});

	}

	final String SAVE_MEMBER = "/saveMember.do";
	public void saveMemberServer(MemberInfo kakaoMemberInfo, final OnNetResultListener<CheckMemberResult> listener){

		Log.d(TAG, "saveMemberServer start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		Gson gson = new GsonBuilder().create();
		params.put("memberJSON", gson.toJson(kakaoMemberInfo));

		String url = SERVER_URL + SAVE_MEMBER;
		client.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				InputStreamReader is = new InputStreamReader(new ByteArrayInputStream(responseBody));
				Gson gson = new GsonBuilder().create();
				CheckMemberResult result = gson.fromJson(is, CheckMemberResult.class);
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				listener.onFail(statusCode);
			}
		});

	}

	final String REQUEST_ASSEMBLYMAN = "/requestAssemblyman.do";
	public void requestAssemblyman(int tag, final OnNetResultListener<List<Assemblyman>> listener) {
		Log.d(TAG, "requestAssemblyman start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("tag", tag);

		String url = SERVER_URL + REQUEST_ASSEMBLYMAN;
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				InputStreamReader is = new InputStreamReader(new ByteArrayInputStream(responseBody));
				Gson gson = new GsonBuilder().create();
				List<Assemblyman> result = gson.fromJson(is, new TypeToken<List<Assemblyman>>() {}.getType());
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				listener.onFail(statusCode);
			}
		});
	}

	final String REQUEST_BILL = "/requestBill.do";
	public void requestBill(int tag, final OnNetResultListener<List<Bill>> listener) {
		Log.d(TAG, "requestBill start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("tag", tag);

		String url = SERVER_URL + REQUEST_BILL;
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				InputStreamReader is = new InputStreamReader(new ByteArrayInputStream(responseBody));
				Gson gson = new GsonBuilder().create();
				List<Bill> result = gson.fromJson(is, new TypeToken<List<Bill>>() {}.getType());
				for(int i=0;i<result.size();i++){
					result.get(i).setBill_title(result.get(i).getBill_title().replace("'", ""));
				}
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				listener.onFail(statusCode);
			}
		});
	}

	final String REQUEST_COMMITTEE = "/requestCommitteeMeeting.do";
	public void requestCommitteeMeeting(int tag, final OnNetResultListener<List<CommitteeMeeting>> listener) {
		Log.d(TAG, "requestCommitteeMeeting start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("tag", tag);

		String url = SERVER_URL + REQUEST_COMMITTEE;
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				InputStreamReader is = new InputStreamReader(new ByteArrayInputStream(responseBody));
				Gson gson = new GsonBuilder().create();
				List<CommitteeMeeting> result = gson.fromJson(is, new TypeToken<List<CommitteeMeeting>>() {}.getType());
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				listener.onFail(statusCode);
			}
		});
	}

	final String REQUEST_GENERAL = "/requestGeneralMeeting.do";
	public void requestGeneralMeeting(int tag, final OnNetResultListener<List<GeneralMeeting>> listener) {
		Log.d(TAG, "requestGeneralMeeting start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("tag", tag);

		String url = SERVER_URL + REQUEST_GENERAL;
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				InputStreamReader is = new InputStreamReader(new ByteArrayInputStream(responseBody));
				Gson gson = new GsonBuilder().create();
				List<GeneralMeeting> result = gson.fromJson(is, new TypeToken<List<GeneralMeeting>>() {}.getType());
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				listener.onFail(statusCode);
			}
		});
	}

	final String REQUEST_PARTY = "/requestParty.do";
	public void requestPartyHistory(int tag, final OnNetResultListener<List<PartyHistory>> listener) {
		Log.d(TAG, "requestPartyHistory start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("tag", tag);

		String url = SERVER_URL + REQUEST_PARTY;
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				InputStreamReader is = new InputStreamReader(new ByteArrayInputStream(responseBody));
				Gson gson = new GsonBuilder().create();
				List<PartyHistory> result = gson.fromJson(is, new TypeToken<List<PartyHistory>>() {}.getType());
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				listener.onFail(statusCode);
			}
		});
	}

	final String REQUEST_VOTE = "/requestVote.do";
	public void requestVote(int tag, final OnNetResultListener<List<Vote>> listener) {
		Log.d(TAG, "requestVote start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("tag", tag);

		String url = SERVER_URL + REQUEST_VOTE;
		client.post(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				InputStreamReader is = new InputStreamReader(new ByteArrayInputStream(responseBody));
				Gson gson = new GsonBuilder().create();
				List<Vote> result = gson.fromJson(is, new TypeToken<List<Vote>>() {}.getType());
				listener.onSuccess(result);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				listener.onFail(statusCode);
			}
		});
	}

	public HttpClient getHttpClient() {
		return client.getHttpClient();
	}
}
