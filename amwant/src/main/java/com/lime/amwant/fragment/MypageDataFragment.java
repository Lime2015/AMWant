package com.lime.amwant.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lime.amwant.R;
import com.lime.amwant.adapter.RVMypageDataAdapter;
import com.lime.amwant.db.AMWDatabase;
import com.lime.amwant.listitem.TableInfoListItem;
import com.lime.amwant.result.CheckTagResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SeongSan on 2015-07-14.
 */
public class MypageDataFragment extends Fragment {

    private static String TAG = "MypageDataFragment";

    private RecyclerView rv;
    private RVMypageDataAdapter adapter;
    private List<TableInfoListItem> tables;

    private AMWDatabase database;


    public static MypageDataFragment newInstance() {
        MypageDataFragment f = new MypageDataFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mypage_data, container, false);
//        ViewCompat.setElevation(rootView, 50);

        rv = (RecyclerView) rootView.findViewById(R.id.rv_mypage_data);

        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeDatabase(rootView.getContext());
        initializeData();
        initializeAdapter();

        return rootView;
    }

    private void initializeData() {
        tables = new ArrayList<>();
        tables.add(new TableInfoListItem("국회의원", 0, 0, R.drawable.assemblyman, R.drawable.check_orange));
    }

    private void initializeAdapter() {
        adapter = new RVMypageDataAdapter(tables);
        rv.setAdapter(adapter);
        checkServerTag();
    }

    private void checkServerTag() {
        Log.d(TAG, "checkServerTag start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(getResources().getString(R.string.server_url) + getResources().getString(R.string.server_check_tag), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.d(TAG, "checkServerTag result:" + content);

                Gson gson = new GsonBuilder().create();
                CheckTagResult result = gson.fromJson(content, CheckTagResult.class);
                CheckTagResult resultDB = database.checkTag();

                TableInfoListItem item0 = tables.get(0);
                if (result.getAssemblymanTag() > resultDB.getAssemblymanTag()) {
                    item0.setAppTag(resultDB.getAssemblymanTag());
                    item0.setServerTag(result.getAssemblymanTag());
                    item0.setIcRefresh(R.drawable.refresh);
                } else {
                    item0.setAppTag(resultDB.getAssemblymanTag());
                    item0.setServerTag(result.getAssemblymanTag());
                    item0.setIcRefresh(R.drawable.check_orange);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d(TAG, "checkServerTag response fail:" + statusCode);
            }
        });

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
