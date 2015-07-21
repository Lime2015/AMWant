package com.lime.amwant.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lime.amwant.R;
import com.lime.amwant.adapter.RVAssemblymenListAdapter;
import com.lime.amwant.db.AMWDatabase;
import com.lime.amwant.listener.RecyclerItemClickListener;
import com.lime.amwant.listitem.AssemblymanListItem;
import com.lime.amwant.statics.AMWStatic;
import com.lime.amwant.vo.MemberInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-07-13.
 */
public class BillListActivity extends ActionBarActivity {

    private final String TAG = "BillListActivity";

    private String[] navItems = {"국회의원", "의안", "명예전당", "국민참여", "마이페이지"};

    private ListView lvNavList;

    private Toolbar toolbar;
    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;

    private MemberInfo memberInfo;

    private RadioGroup mRgroup;
    private RecyclerView rv;
    private RVAssemblymenListAdapter adapter;
    private List<AssemblymanListItem> tables;
    private AMWDatabase database;

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

        lvNavList = (ListView) findViewById(R.id.drawer);
        lvNavList.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));

        lvNavList.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        lvNavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dlDrawer.closeDrawer(lvNavList);
                AMWStatic.viewSubActivity(view.getContext(), position, memberInfo);
            }
        });

        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, 0, 0);
        dlDrawer.setDrawerListener(dtToggle);

        rv = (RecyclerView) findViewById(R.id.rv_cardList);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeDatabase(this);
        initializeData();
        initializeAdapter();


        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        // do whatever
                        showBillActivity(position);
                    }

                    @Override
                    public void onItemLongClick(MotionEvent e) {
                        View childView = rv.findChildViewUnder(e.getX(), e.getY());
                        int position = rv.getChildPosition(childView);
                        Toast.makeText(getApplicationContext(), tables.get(position).getAssemblymanName() + " 관심의안 등록~~", Toast.LENGTH_SHORT).show();
                    }
                })
        );

        mRgroup = (RadioGroup) findViewById(R.id.radio_group);
        mRgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
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
    }

    private void showBillActivity(int position) {
        Intent intent = new Intent(this, BillActivity.class);
//        intent.putExtra("id", billId);
        startActivity(intent);
    }

    private void initializeAdapter() {
        if(tables==null){
            Toast.makeText(getApplicationContext(), "마이페이지>데이터>국회의원 데이터를 다운받으세요.", Toast.LENGTH_SHORT).show();
            return;
        }
//        adapter = new RVAssemblymenListAdapter(this, tables);
        rv.setAdapter(adapter);
    }

    private void initializeData() {
        tables = new ArrayList<>();
//        tables = database.selectAssemblymenList(0);      // modDttm:0, 인기순:1, 이름순:2
    }

    private void changeSorting(int type){
//        tables = database.selectAssemblymenList(type);
//        initializeAdapter();
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

    private void initializeDatabase(Context context) {
        if (database != null) {
            database.close();
            database = null;
        }

        database = AMWDatabase.getInstance(context);
        boolean isOpen = database.open();
        if (isOpen) {
            Log.d(TAG, "WatchAssembly database is open.");
        } else {
            Log.d(TAG, "WatchAssembly database is not open.");
        }

        Log.d(TAG, "initializeDatabase success!!");
    }
}
