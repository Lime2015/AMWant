package com.lime.amwant.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lime.amwant.R;
import com.lime.amwant.frame.ExtraUserPropertyFrame;
import com.lime.amwant.vo.MemberInfo;
import com.lime.amwant.result.CheckMemberResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by SeongSan on 2015-06-18.
 */
public class WASignupActivity extends Activity {

    private final String TAG = "WASignupActivity";

    // property keyQu
    private  static final String ADDRESS_KEY = "address";
    private  static final String BIRTHDAY_KEY = "birthday";
    private  static final String GENDER_KEY = "gender";

    Button btnWASignup;
    ExtraUserPropertyFrame waExtraUserPropertyFrame;
    MemberInfo kakaoMemberInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showSignup();
    }

    private void showSignup() {

        Intent intent = getIntent();
        kakaoMemberInfo = (MemberInfo)intent.getSerializableExtra("kakaoMemberInfo");

        setContentView(R.layout.wa_signup);
        waExtraUserPropertyFrame = (ExtraUserPropertyFrame) findViewById(R.id.wa_extra_user_property);
        btnWASignup = (Button) findViewById(R.id.btnWASignup);
        btnWASignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickSignup(waExtraUserPropertyFrame.getProperties());
            }
        });
    }

    /**
     * 서버에 없는 데이터가 없는 신규 회원의 경우 추가 정보 받기
     */
    private void onClickSignup(final HashMap<String, String> properties) {
        Log.d(TAG, "saveMember.do start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        // save extra info
        final String strAddress = properties.get(ADDRESS_KEY);
        if (strAddress != null)
            kakaoMemberInfo.setAddress(strAddress);

        final String strBirthday = properties.get(BIRTHDAY_KEY);
        if (strBirthday != null) {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = null;
            try {
                date = transFormat.parse(strBirthday);
                kakaoMemberInfo.setBirthDate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        final String strGenger = properties.get(GENDER_KEY);
        if (strGenger != null)
            kakaoMemberInfo.setGender(strGenger);

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Gson gson = new GsonBuilder().create();

        Log.d(TAG, "memberJSON:" + gson.toJson(kakaoMemberInfo));
        params.put("memberJSON", gson.toJson(kakaoMemberInfo));

        client.post(getResources().getString(R.string.server_url) + getResources().getString(R.string.server_save_member), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String content) {

                Log.d(TAG, "AsyncHttpClient response result:" + content);

                Gson gson = new GsonBuilder().create();
                CheckMemberResult serverResult = gson.fromJson(content, CheckMemberResult.class);

                if (serverResult.getResult() == 1) {
                    // 정상 신규 등록
                    Log.d(TAG, "complete to signup in server");
                    Toast.makeText(getApplicationContext(), "Success Signup !!", Toast.LENGTH_LONG).show();

                    redirectKakaoActivity();
                } else {
                    // 신규 등록 실패
                    Log.d(TAG, "fail to request new member info in server");
                    Toast.makeText(getApplicationContext(), "fail to request new member info in server", Toast.LENGTH_LONG).show();

                    redirectKakaoActivity();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d(TAG, "AsyncHttpClient response fail:" + statusCode);
                Toast.makeText(getApplicationContext(), "AsyncHttpClient response fail:" + statusCode, Toast.LENGTH_LONG).show();

                redirectKakaoActivity();
            }
        });
    }

    private void redirectKakaoActivity() {
        Intent intent = new Intent(this, MainLoginTypeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

}
