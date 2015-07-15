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
import com.lime.amwant.result.CheckMemberResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;


public class MainLoginTypeActivity extends ActionBarActivity {

    private static String TAG = "MainLoginTypeActivity";

    private Button btnDemo;

    private final int WA_SIGNUP_CODE = 1100;

    private MemberInfo kakaoMemberInfo;
    private UserProfile userProfile;


    private LoginButton loginButton;
    private final SessionCallback mySessionCallback = new MySessionStatusCallback();
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        super.onCreate(savedInstanceState);

        kakaoMemberInfo = null;
        checkLoginInfo();
    }

    private void checkLoginInfo() {
        Log.d(TAG, "checkLoginInfo start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        userProfile = UserProfile.loadFromCache();
//        Log.d(TAG, "kakaoMemberInfo:" + kakaoMemberInfo);
//        Log.d(TAG, "userProfile:" + userProfile);

        if (kakaoMemberInfo == null && userProfile != null) {
            long id = userProfile.getId();
            String nickname = userProfile.getNickname();

            if (id > 0) {
                Log.d(TAG, "로그인정보:" + id + "/" + nickname);
                kakaoMemberInfo = new MemberInfo("" + id, 1, nickname);
                // web server 회원인지 체크
                checkMemberInServer();
            } else {
                initView();
            }
        } else {
            initView();
        }
    }

    private void initView() {
        setContentView(R.layout.activity_main_login_type);
        btnDemo = (Button) findViewById(R.id.demo_btn);
        btnDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDemo();
            }
        });

        loginButton = (LoginButton) findViewById(R.id.com_kakao_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainLoginTypeActivity.this.finish();
            }
        });

        session = Session.getCurrentSession();
        session.addCallback(mySessionCallback);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        super.onDestroy();
        if (session != null) session.removeCallback(mySessionCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (session != null) {
            if (session.isClosed()) {
                loginButton.setVisibility(View.VISIBLE);
                btnDemo.setVisibility(View.VISIBLE);
            } else {
                loginButton.setVisibility(View.GONE);
                btnDemo.setVisibility(View.GONE);
                session.implicitOpen();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

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
            btnDemo.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSessionOpening() {
            //뺑글이 시작
        }

    }

    protected void onSessionOpened() {
        // 세션이 오픈되어 있음. 즉, Access Token을 얻어 낼 수 있는 위치임.
        final Intent intent = new Intent(MainLoginTypeActivity.this, SampleSignupActivity.class);
        startActivity(intent);
        finish();
    }

    private void checkMemberInServer() {
        Log.d(TAG, "checkMemberInServer start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Gson gson = new GsonBuilder().create();

        Log.d(TAG, "memberJSON:" + gson.toJson(kakaoMemberInfo));
        params.put("memberJSON", gson.toJson(kakaoMemberInfo));

        client.post(getResources().getString(R.string.server_url) + getResources().getString(R.string.server_check_member), params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);

                Log.d(TAG, "AsyncHttpClient response result:" + content);

                Gson gson = new GsonBuilder().create();
                CheckMemberResult result = gson.fromJson(content, CheckMemberResult.class);

                if (result.getResult() == 0) {
                    // 신규회원
                    redirectWASignupActivity();
                } else {
                    // 기존회원
                    showMainMenuActivity();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d(TAG, "checkMemberInServer response fail:" + statusCode);
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
        Log.d(TAG, "MainMenuActivity start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("memberInfo", kakaoMemberInfo);
        startActivity(intent);
        finish();
    }

    private void showDemo() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("memberInfo", new MemberInfo());
        startActivity(intent);
        finish();
    }
}
