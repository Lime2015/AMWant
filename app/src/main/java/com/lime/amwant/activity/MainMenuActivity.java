package com.lime.amwant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.kakao.APIErrorResult;
import com.kakao.LogoutResponseCallback;
import com.kakao.UserManagement;
import com.lime.amwant.R;
import com.lime.amwant.vo.MemberInfo;

/**
 * Created by SeongSan on 2015-06-24.
 */
public class MainMenuActivity extends ActionBarActivity {

    private static final String TAG = "MainMenuActivity";

    MemberInfo memberInfo;

    Button btnViewMypage;
    Button btnViewAssList;
    Button btnViewBillList;
    Button btnViewHall;
    Button btnViewPublic;
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();
        memberInfo = (MemberInfo) intent.getSerializableExtra("memberInfo");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (memberInfo.getMemberId().equals("")) {
            getSupportActionBar().setTitle("둘러보기");
        } else {
            getSupportActionBar().setTitle(memberInfo.getMemberNickname());
        }

        btnViewMypage = (Button) findViewById(R.id.btnViewMypage);
        btnViewMypage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSubmain(4);
            }
        });
        btnViewAssList = (Button) findViewById(R.id.btnViewAssemblymanList);
        btnViewAssList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSubmain(0);
            }
        });
        btnViewBillList = (Button) findViewById(R.id.btnViewBillList);
        btnViewBillList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSubmain(1);
            }
        });
        btnViewHall = (Button) findViewById(R.id.btnViewHallOfFame);
        btnViewHall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSubmain(2);
            }
        });
        btnViewPublic = (Button) findViewById(R.id.btnViewPublicOpinion);
        btnViewPublic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSubmain(3);
            }
        });

        if (memberInfo.getMemberId().equals("")) {
            btnViewMypage.setVisibility(View.GONE);
//            btnViewMypage.setEnabled(false);
//            btnViewMypage.setAlpha(0.3f);
        }
    }


    private void redirectLogoutActivity() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            protected void onSuccess(long l) {
                Log.d(TAG, "로그아웃");

                memberInfo = null;
                redirectMainActivity();
            }

            @Override
            protected void onFailure(APIErrorResult apiErrorResult) {
            }
        });
    }

    public void redirectMainActivity() {
        Intent intent = new Intent(this, MainLoginTypeActivity.class);
        startActivity(intent);
        finish();
    }

    private void viewSubmain(int index) {
        Intent intent = null;
        switch (index){
            case 0:
                intent = new Intent(this, AssemblymanListActivity.class);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
        }
        intent.putExtra("memberInfo", memberInfo);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                if (memberInfo.getMemberId().equals("")) {
                    redirectMainActivity();
                } else {
                    redirectLogoutActivity();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
