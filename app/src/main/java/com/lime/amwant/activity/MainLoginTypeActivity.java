package com.lime.amwant.activity;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kakao.Session;
import com.kakao.SessionCallback;
import com.kakao.UserProfile;
import com.kakao.exception.KakaoException;
import com.kakao.widget.LoginButton;
import com.lime.amwant.R;
import com.lime.amwant.vo.MemberInfo;
import com.lime.amwant.vo.ServerResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;


public class MainLoginTypeActivity extends ActionBarActivity {

    private static String TAG = "MainLoginTypeActivity";
    private int mDestroyFlag;

    private LoginButton loginButton;
    private Button btnDemo;
    private final SessionCallback mySessionCallback = new MySessionStatusCallback();
    private Session session;

    private final String SERVER_URL = "http://52.69.102.82";
    //    private final String SERVER_URL = "http://192.168.0.3:9080";
    private final String SERVER_CHECK_MEMBER = "/WatchAssemblyWebServer/checkMember.do";
    private final int WA_SIGNUP_CODE = 1100;

    private MemberInfo kakaoMemberInfo;
    private UserProfile userProfile;

//    private WatchAssemblyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_type);

//        initializeDatabase();
        kakaoMemberInfo = null;
        mDestroyFlag = 0;

        btnDemo = (Button) findViewById(R.id.demo_btn);
        btnDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDemo();
            }
        });

        // SimpleLoginActivity
//        Session.initialize(this);
        userProfile = UserProfile.loadFromCache();
        loginButton = (LoginButton) findViewById(R.id.com_kakao_login);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        session = Session.getCurrentSession();
        session.addCallback(mySessionCallback);
    }

    private void checkLoginInfo() {
        Log.d(TAG, "checkLoginInfo start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        if (kakaoMemberInfo == null && userProfile != null) {
            long id = userProfile.getId();
            String nickname = userProfile.getNickname();

            if (id > 0) {
                Log.d(TAG, "로그인정보:" + id + "/" + nickname);

                kakaoMemberInfo = new MemberInfo("" + id, 1, nickname);
//                txtNickname.setText(kakaoMemberInfo.getMemberNickname());

                // web server 회원인지 체크
                checkMemberInServer();
            }
        }
    }

    @Override
    protected void onDestroy() {

        if (mDestroyFlag == 0) return;

        Log.d(TAG, "onDestroy start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        super.onDestroy();
        session.removeCallback(mySessionCallback);
    }

    @Override
    public void onBackPressed() {
        mDestroyFlag = 1;
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        super.onResume();
        mDestroyFlag = 0;

        if (session.isClosed()) {
            loginButton.setVisibility(View.VISIBLE);
//            loginButton.setVisibility(View.GONE);
//            loginButton.performClick();
        } else {
            loginButton.setVisibility(View.GONE);
            session.implicitOpen();
        }

        checkLoginInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "onActivityResult start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
//            checkLoginInfo();
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void checkMemberInServer() {
        Log.d(TAG, "checkMemberInServer start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Gson gson = new GsonBuilder().create();

        Log.d(TAG, "memberJSON:" + gson.toJson(kakaoMemberInfo));
        params.put("memberJSON", gson.toJson(kakaoMemberInfo));

//        prgDialog.setMessage("check member...");
//        prgDialog.show();

//        client.addHeader("Accept-Encoding", "gzip");
        client.post(SERVER_URL + SERVER_CHECK_MEMBER, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                super.onSuccess(statusCode, headers, responseBody);
                String content = new String(responseBody);
//                prgDialog.hide();

                Log.d(TAG, "AsyncHttpClient response result:" + content);

                Gson gson = new GsonBuilder().create();
                ServerResult serverResult = gson.fromJson(content, ServerResult.class);

                if (serverResult.getResult() == 0) {
                    // 신규회원
                    redirectWASignupActivity();
                } else {
                    // 기존회원
                    showMainMenuActivity();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
//                prgDialog.hide();
                Log.d(TAG, "AsyncHttpClient response fail:" + statusCode);
//                Toast.makeText(getApplicationContext(), "서버연결실패!!" + statusCode, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void redirectWASignupActivity() {
        Log.d(TAG, "WASignupActivity start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Intent intent = new Intent(this, WASignupActivity.class);
        intent.putExtra("kakaoMemberInfo", kakaoMemberInfo);
        startActivityForResult(intent, WA_SIGNUP_CODE);
        finish();
    }

    private void showMainMenuActivity() {
//        Toast.makeText(getApplicationContext(), "Show MyPage !!", Toast.LENGTH_LONG).show();

        // MainMenuActivity
        Log.d(TAG, "MainMenuActivity start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        mDestroyFlag = 1;

        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("memberInfo", kakaoMemberInfo);
        startActivity(intent);
        finish();
    }

    private void showDemo() {
        // MainMenuActivity
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("memberInfo", new MemberInfo());
        startActivity(intent);
        finish();
    }


    // SimpleLoginActivity
    private class MySessionStatusCallback implements SessionCallback {
        /**
         * 세션이 오픈되었으면 가입페이지로 이동 한다.
         */
        @Override
        public void onSessionOpened() {
            //뺑글이 종료
            // 프로그레스바를 보이고 있었다면 중지하고 세션 오픈후 보일 페이지로 이동
            MainLoginTypeActivity.this.onSessionOpened();
        }

        /**
         * 세션이 삭제되었으니 로그인 화면이 보여야 한다.
         *
         * @param exception 에러가 발생하여 close가 된 경우 해당 exception
         */
        @Override
        public void onSessionClosed(final KakaoException exception) {
            //뺑글이 종료
            // 프로그레스바를 보이고 있었다면 중지하고 세션 오픈을 못했으니 다시 로그인 버튼 노출.
            loginButton.setVisibility(View.VISIBLE);
//            onBackPressed();
        }

        @Override
        public void onSessionOpening() {
            //뺑글이 시작
        }

    }

    protected void onSessionOpened() {
        final Intent intent = new Intent(MainLoginTypeActivity.this, SampleSignupActivity.class);
        startActivity(intent);
        finish();
    }

//    private void initializeDatabase() {
//        if (database != null) {
//            database.close();
//            database = null;
//        }
//
//        database = WatchAssemblyDatabase.getInstance(this);
//        boolean isOpen = database.open();
//        if (isOpen) {
//            Log.d(TAG, "WatchAssembly database is open.");
//        } else {
//            Log.d(TAG, "WatchAssembly database is not open.");
//        }
//
//        Log.d(TAG, "initializeDatabase success!!");
//    }
}
