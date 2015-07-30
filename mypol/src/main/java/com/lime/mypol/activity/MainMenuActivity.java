package com.lime.mypol.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
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
import com.lime.mypol.R;
import com.lime.mypol.statics.AMWStatic;
import com.lime.mypol.vo.MemberInfo;

/**
 * Created by SeongSan on 2015-06-24.
 */
public class MainMenuActivity extends AppCompatActivity {

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

        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_dark_material_light)));

//        ActionBar bar = getSupportActionBar();
//        bar.setDisplayShowHomeEnabled(false);
//        bar.setDisplayShowTitleEnabled(false);
//        LayoutInflater mInflater = LayoutInflater.from(this);
//
//        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
//        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);

        if (memberInfo == null) {
            bar.setTitle("둘러보기");
//            mTitleTextView.setText("둘러보기");
        } else if (memberInfo.getMemberId().equals("")) {
            bar.setTitle("둘러보기");
//            mTitleTextView.setText("둘러보기");
        } else {
            bar.setTitle(memberInfo.getMemberNickname());
//            mTitleTextView.setText(memberInfo.getMemberNickname());
//            ImageView imageView = (ImageView) mCustomView.findViewById(R.id.icThumbnail);
//            Picasso.with(this).load(memberInfo.getUrlThumbnail()).into(imageView);
//            bar.setIcon(new BitmapDrawable(getResources(),new Bitmap()));
        }

//        bar.setCustomView(mCustomView);
//        bar.setDisplayShowCustomEnabled(true);

        btnViewMypage = (Button) findViewById(R.id.btnViewMypage);
        btnViewMypage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AMWStatic.viewSubActivity(view.getContext(), 4, memberInfo);
            }
        });
        btnViewAssList = (Button) findViewById(R.id.btnViewAssemblymanList);
        btnViewAssList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AMWStatic.viewSubActivity(view.getContext(), 0, memberInfo);
            }
        });
        btnViewBillList = (Button) findViewById(R.id.btnViewBillList);
        btnViewBillList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AMWStatic.viewSubActivity(view.getContext(), 1, memberInfo);
            }
        });
        btnViewHall = (Button) findViewById(R.id.btnViewHallOfFame);
        btnViewHall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AMWStatic.viewSubActivity(view.getContext(), 2, memberInfo);
            }
        });
        btnViewPublic = (Button) findViewById(R.id.btnViewPublicOpinion);
        btnViewPublic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AMWStatic.viewSubActivity(view.getContext(), 3, memberInfo);
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
