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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lime.amwant.R;
import com.lime.amwant.vo.MemberInfo;

/**
 * Created by Administrator on 2015-07-13.
 */
public class HallOfFameActivity extends ActionBarActivity {

    private final String TAG = "HallOfFameActivity";

    private String[] navItems = {"국회의원", "의안", "명예전당", "국민참여", "마이페이지"};

    private ListView lvNavList;

    private Toolbar toolbar;
    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle dtToggle;

    private MemberInfo memberInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_tab);

        Intent intent = getIntent();
        memberInfo = (MemberInfo) intent.getSerializableExtra("memberInfo");

        if (memberInfo.getMemberId().equals("")) {
            navItems = new String[]{"국회의원", "의안", "명예전당", "국민참여"};
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("명예전당");
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);
        bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.holo_purple_dark)));

        lvNavList = (ListView) findViewById(R.id.drawer);
        lvNavList.setBackgroundColor(getResources().getColor(android.R.color.holo_purple));

        lvNavList.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        lvNavList.setOnItemClickListener(new DrawerItemClickListener());

        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, 0, 0);
        dlDrawer.setDrawerListener(dtToggle);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_category, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
//        MenuItem logoutItem = menu.findItem(R.id.action_logout);

        SearchManager searchManager = (SearchManager) HallOfFameActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(HallOfFameActivity.this.getComponentName()));
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            dlDrawer.closeDrawer(lvNavList);
            if (position != 2) {
                viewSubmain(position);
            }
        }
    }

    private void viewSubmain(int index) {
        Intent intent = null;
        switch (index){
            case 0:
                intent = new Intent(this, AssemblymenListActivity.class);
                break;
            case 1:
                intent = new Intent(this, BillListActivity.class);
                break;
            case 2:
                intent = new Intent(this, HallOfFameActivity.class);
                break;
            case 3:
                intent = new Intent(this, PublicOpinionsActivity.class);
                break;
            case 4:
                intent = new Intent(this, MypageActivity.class);
                break;
        }
        intent.putExtra("memberInfo", memberInfo);
        startActivity(intent);
        finish();
    }

}
