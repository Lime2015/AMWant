package com.lime.mypol.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lime.mypol.R;
import com.lime.mypol.adapter.BillListAdapter;
import com.lime.mypol.listitem.AssemblymanItem;
import com.lime.mypol.listitem.BillItem;
import com.lime.mypol.manager.DatabaseManager;
import com.lime.mypol.statics.AMWStatic;
import com.lime.mypol.vo.MemberInfo;

/**
 * Created by SeongSan on 2015-07-13.
 */
public class BillListActivity extends AppCompatActivity {

    private final String TAG = "BillListActivity";

    private String[] navItems = {"국회의원", "의안", "명예전당", "국민참여", "마이페이지"};
    private final int DATA_COUNT = 30;

    private Toolbar toolbar;
    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;
    private ListView drawerListView;

    private MemberInfo memberInfo;

    private RadioGroup mRgroup;

    private PullToRefreshListView rv;
    private BillListAdapter mAdapter;
    private ListView rvListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        Intent intent = getIntent();
        memberInfo = (MemberInfo) intent.getSerializableExtra("memberInfo");

        if (memberInfo.getMemberId().equals("")) {
            navItems = new String[]{"국회의원", "의안", "명예전당", "국민참여"};
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("의안");
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_green_dark)));

        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, 0, 0);
        dlDrawer.setDrawerListener(dtToggle);

        drawerListView = (ListView) findViewById(R.id.drawer);
        drawerListView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));

        drawerListView.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dlDrawer.closeDrawer(drawerListView);
                AMWStatic.viewSubActivity(view.getContext(), position, memberInfo);
            }
        });

        rv = (PullToRefreshListView) findViewById(R.id.rv_cardList);
        rv.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                int sortIndex = mAdapter.getSortIndex();
                int start = mAdapter.getStart();
                if (start != -1) {
                    mAdapter.addAll(DatabaseManager.getInstance(BillListActivity.this).selectBillList(sortIndex, start, DATA_COUNT));
                }
            }
        });

        rvListView = rv.getRefreshableView();
        mAdapter = new BillListAdapter();
        rvListView.setAdapter(mAdapter);

        rvListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showBillActivity(position - 1);
            }
        });
        rvListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ((BillItem) mAdapter.getItem(position - 1)).getBillTitle() + " 관심의안 등록~~", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mRgroup = (RadioGroup) findViewById(R.id.radio_group);
        mRgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_up:
                        changeSorting(0);
                        break;
                    case R.id.radio_fav:
//                        changeSorting(1);
                        Toast.makeText(getApplicationContext(), "준비중입니다...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio_name:
                        changeSorting(2);
                        break;
                }
            }
        });


        changeSorting(0);

    }

    private void showBillActivity(int position) {
        Intent intent = new Intent(this, BillActivity.class);
        intent.putExtra("title", ((BillItem) mAdapter.getItem(position)).getBillTitle());
        startActivity(intent);
    }

    private void changeSorting(int type) {
        int totalCount = DatabaseManager.getInstance(BillListActivity.this).getTotalBill();
        if (totalCount == 0){
            Toast.makeText(getApplicationContext(), "의안 데이터를 다운받으세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        mAdapter.clear();
        mAdapter.addAll(DatabaseManager.getInstance(BillListActivity.this).selectBillList(type, 1, DATA_COUNT));
        mAdapter.setSortIndex(type);
        mAdapter.setTotalCount(totalCount);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_category, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
//        MenuItem logoutItem = menu.findItem(R.id.action_logout);

        SearchManager searchManager = (SearchManager) BillListActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(BillListActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dtToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
