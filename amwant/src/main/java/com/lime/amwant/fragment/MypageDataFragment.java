package com.lime.amwant.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lime.amwant.R;
import com.lime.amwant.adapter.RVMypageDataAdapter;
import com.lime.amwant.db.AMWDatabase;
import com.lime.amwant.listener.RecyclerItemClickListener;
import com.lime.amwant.listitem.TableInfoListItem;
import com.lime.amwant.result.CheckTagResult;
import com.lime.amwant.vo.Assemblyman;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
    private List<Boolean> download;

    private AMWDatabase database;
    private CheckTagResult serverTag;
    private CheckTagResult dbTag;

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

        rv.addOnItemTouchListener(new RecyclerItemClickListener(rootView.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
//                        Log.d(TAG, "RecycerItem click event view:" + view);
//                        Log.d(TAG, "RecycerItem click event position:" + position);
                        if(download.get(position)){
                            switch (position){
                                case 0:
                                    requestAssemblyman();
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    break;
                                case 5:
                                    break;
                            }
                        }
                    }
                })
        );

        return rootView;
    }

    private void initializeData() {
        tables = new ArrayList<>();
        tables.add(new TableInfoListItem("국회의원", 0, 0, R.drawable.assemblyman, R.drawable.check_orange));
        tables.add(new TableInfoListItem("의안", 0, 0, R.drawable.bill, R.drawable.check_orange));
        tables.add(new TableInfoListItem("상임위원회", 0, 0, R.drawable.committee, R.drawable.check_orange));
        tables.add(new TableInfoListItem("본회의", 0, 0, R.drawable.assembly, R.drawable.check_orange));
        tables.add(new TableInfoListItem("정당", 0, 0, R.drawable.committee, R.drawable.check_orange));
        tables.add(new TableInfoListItem("표결", 0, 0, R.drawable.assembly, R.drawable.check_orange));

        download = new ArrayList<>();
        download.add(false);
        download.add(false);
        download.add(false);
        download.add(false);
        download.add(false);
        download.add(false);
    }

    private void initializeAdapter() {
        adapter = new RVMypageDataAdapter(tables);
        rv.setAdapter(adapter);
        checkServerTag();
    }

    private void checkServerTag() {
        Log.d(TAG, "checkServerTag start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        AsyncHttpClient client = new AsyncHttpClient();
        String url = getResources().getString(R.string.server_url) + getResources().getString(R.string.server_check_tag);
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.d(TAG, "checkServerTag result:" + content);

                Gson gson = new GsonBuilder().create();
                serverTag = gson.fromJson(content, CheckTagResult.class);
                dbTag = database.checkTag();

                for (int i = 0; i < serverTag.getTagList().size(); i++) {
                    TableInfoListItem item = tables.get(i);
                    item.setAppTag(dbTag.getTagList().get(i));
                    item.setServerTag(serverTag.getTagList().get(i));
                    if (serverTag.getTagList().get(i) > dbTag.getTagList().get(i)) {
                        item.setIcRefresh(R.drawable.refresh);
                        download.set(i, true);
                    } else {
                        item.setIcRefresh(R.drawable.check_orange);
                        download.set(i, false);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d(TAG, "checkServerTag response fail:" + statusCode);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void requestAssemblyman() {
        Log.d(TAG, "requestAssemblyman start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tag", dbTag.getTagList().get(0));

        String url = getResources().getString(R.string.server_url) + getResources().getString(R.string.server_request_assemblyman);
        client.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.d(TAG, "requestAssemblyman result:" + content);

                Gson gson = new GsonBuilder().create();
                List<Assemblyman> result = gson.fromJson(content, new TypeToken<List<Assemblyman>>() {
                }.getType());

            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d(TAG, "requestAssemblyman response fail:" + statusCode);
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
