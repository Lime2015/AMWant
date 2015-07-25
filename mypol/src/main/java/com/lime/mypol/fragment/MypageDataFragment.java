package com.lime.mypol.fragment;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lime.mypol.R;
import com.lime.mypol.adapter.RVMypageDataAdapter;
import com.lime.mypol.db.AMWDatabase;
import com.lime.mypol.listener.RecyclerItemClickListener;
import com.lime.mypol.listitem.TableInfoListItem;
import com.lime.mypol.result.CheckTagResult;
import com.lime.mypol.vo.Assemblyman;
import com.lime.mypol.vo.Bill;
import com.lime.mypol.R;
import com.lime.mypol.adapter.RVMypageDataAdapter;
import com.lime.mypol.db.AMWDatabase;
import com.lime.mypol.listener.RecyclerItemClickListener;
import com.lime.mypol.listitem.TableInfoListItem;
import com.lime.mypol.result.CheckTagResult;
import com.lime.mypol.vo.Assemblyman;
import com.lime.mypol.vo.Bill;
import com.lime.mypol.vo.CommitteeMeeting;
import com.lime.mypol.vo.GeneralMeeting;
import com.lime.mypol.vo.PartyHistory;
import com.lime.mypol.vo.Vote;
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

    private RecyclerView mRv;
    private RVMypageDataAdapter mAdapter;
    private List<TableInfoListItem> mTables;
    private List<Boolean> mDownloadList;

    private AMWDatabase mDatabase;
    private CheckTagResult mServerTag;
    private CheckTagResult mDbTag;

    private ProgressBar mProgressBar;

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

        mRv = (RecyclerView) rootView.findViewById(R.id.rv_mypage_data);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.pb_data);

        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        mRv.setLayoutManager(llm);
        mRv.setHasFixedSize(true);

        initializeDatabase(rootView.getContext());
        initializeData();
        initializeAdapter();

        mRv.addOnItemTouchListener(new RecyclerItemClickListener(rootView.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
//                        Log.d(TAG, "RecycerItem click event view:" + view);
//                        Log.d(TAG, "RecycerItem click event position:" + position);
                        if (mDownloadList.get(position)) {
                            switch (position) {
                                case 0:
                                    requestAssemblyman();
                                    break;
                                case 1:
                                    requestBill();
                                    break;
                                case 2:
                                    requestCommitteeMeeting();
                                    break;
                                case 3:
                                    requestGeneralMeeting();
                                    break;
                                case 4:
                                    requestPartyHistory();
                                    break;
                                case 5:
                                    requestVote();
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onItemLongClick(MotionEvent e) {

                    }
                })
        );

        return rootView;
    }

    private void requestBill() {

        Log.d(TAG, "requestBill start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tag", mDbTag.getTagList().get(1));

        String url = getResources().getString(R.string.server_url) + getResources().getString(R.string.server_name) + getResources().getString(R.string.server_request_bill);
        client.post(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(ProgressBar.VISIBLE);
            }

            @Override
            public void onProgress(int position, int length) {
                super.onProgress(position, length);


                Log.d(TAG, "ProgressBar position:" + position + " length:" + length + " >>>>> " + position * 100 / length);
                mProgressBar.setProgress(position * 100 / length);
//                mProgressBar.setMax(length);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.d(TAG, "requestBill result:" + content);

                Gson gson = new GsonBuilder().create();
                List<Bill> billList = gson.fromJson(content, new TypeToken<List<Bill>>() {
                }.getType());

                if (mDatabase.insertBillList(billList)) {
                    Log.d(TAG, "bill db insert success!!");
                    checkServerTag();
                }
            }
            
            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d(TAG, "requestBill response fail:" + statusCode);
                Toast.makeText(getActivity().getApplicationContext(), "서버접속 실패!! 잠시후에 다시 시도하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    
    private void initializeDatabase(Context context) {
        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }
        
        mDatabase = AMWDatabase.getInstance(context);
        boolean isOpen = mDatabase.open();
        if (isOpen) {
            Log.d(TAG, "WatchAssembly database is open.");
        } else {
            Log.d(TAG, "WatchAssembly database is not open.");
        }
        
        Log.d(TAG, "initializeDatabase success!!");
    }

    private void initializeData() {
        mTables = new ArrayList<>();
        mTables.add(new TableInfoListItem("국회의원", 0, 0, R.drawable.assemblyman, R.drawable.check_orange));
        mTables.add(new TableInfoListItem("의안", 0, 0, R.drawable.bill, R.drawable.check_orange));
        mTables.add(new TableInfoListItem("상임위원회", 0, 0, R.drawable.committee, R.drawable.check_orange));
        mTables.add(new TableInfoListItem("본회의", 0, 0, R.drawable.assembly, R.drawable.check_orange));
        mTables.add(new TableInfoListItem("정당", 0, 0, R.drawable.committee, R.drawable.check_orange));
        mTables.add(new TableInfoListItem("표결", 0, 0, R.drawable.assembly, R.drawable.check_orange));

        mDownloadList = new ArrayList<>();
        mDownloadList.add(false);
        mDownloadList.add(false);
        mDownloadList.add(false);
        mDownloadList.add(false);
        mDownloadList.add(false);
        mDownloadList.add(false);
    }

    private void initializeAdapter() {
        mAdapter = new RVMypageDataAdapter(mTables);
        mRv.setAdapter(mAdapter);
        checkServerTag();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    private void checkServerTag() {
        Log.d(TAG, "checkServerTag start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        AsyncHttpClient client = new AsyncHttpClient();
        String url = getResources().getString(R.string.server_url) + getResources().getString(R.string.server_name) + getResources().getString(R.string.server_check_tag);
        client.post(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(ProgressBar.VISIBLE);
            }

            @Override
            public void onProgress(int position, int length) {
                super.onProgress(position, length);

                mProgressBar.setProgress(position);
                mProgressBar.setMax(length);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.d(TAG, "checkServerTag result:" + content);

                Gson gson = new GsonBuilder().create();
                mServerTag = gson.fromJson(content, CheckTagResult.class);
                mDbTag = mDatabase.checkTag();

                for (int i = 0; i < mServerTag.getTagList().size(); i++) {
                    TableInfoListItem item = mTables.get(i);
                    item.setAppTag(mDbTag.getTagList().get(i));
                    item.setServerTag(mServerTag.getTagList().get(i));
                    if (mServerTag.getTagList().get(i) > mDbTag.getTagList().get(i)) {
                        item.setIcRefresh(R.drawable.download);
                        mDownloadList.set(i, true);
                    } else {
                        item.setIcRefresh(R.drawable.check_orange);
                        mDownloadList.set(i, false);
                    }
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProgressBar.setVisibility(ProgressBar.GONE);
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d(TAG, "checkServerTag response fail:" + statusCode);
                Toast.makeText(getActivity().getApplicationContext(), "서버접속 실패!! 잠시후에 다시 시도하세요.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestAssemblyman() {
        Log.d(TAG, "requestAssemblyman start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tag", mDbTag.getTagList().get(0));

        String url = getResources().getString(R.string.server_url) + getResources().getString(R.string.server_name) + getResources().getString(R.string.server_request_assemblyman);
        client.post(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(ProgressBar.VISIBLE);
            }

            @Override
            public void onProgress(int position, int length) {
                super.onProgress(position, length);


                Log.d(TAG, "ProgressBar position:" + position + " length:" + length + " >>>>> " + position * 100 / length);
                mProgressBar.setProgress(position * 100 / length);
//                mProgressBar.setMax(length);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.d(TAG, "requestAssemblyman result:" + content);

                Gson gson = new GsonBuilder().create();
                List<Assemblyman> assList = gson.fromJson(content, new TypeToken<List<Assemblyman>>() {
                }.getType());

                if (mDatabase.insertAssemblymenList(assList)) {
                    Log.d(TAG, "assemblyman db insert success!!");
                    checkServerTag();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d(TAG, "requestAssemblyman response fail:" + statusCode);
                Toast.makeText(getActivity().getApplicationContext(), "서버접속 실패!! 잠시후에 다시 시도하세요.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestBill() {

        Log.d(TAG, "requestBill start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tag", mDbTag.getTagList().get(1));

        String url = getResources().getString(R.string.server_url) + getResources().getString(R.string.server_name) + getResources().getString(R.string.server_request_bill);
        client.post(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(ProgressBar.VISIBLE);
            }

            @Override
            public void onProgress(int position, int length) {
                super.onProgress(position, length);


                Log.d(TAG, "ProgressBar position:" + position / 1000 + " length:" + length);
                mProgressBar.setProgress(position / 1000);
//                mProgressBar.setMax(length);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.d(TAG, "requestAssemblyman result:" + content);

                Gson gson = new GsonBuilder().create();
                List<Bill> billList = gson.fromJson(content, new TypeToken<List<Bill>>() {
                }.getType());

                if (mDatabase.insertBillList(billList)) {
                    Log.d(TAG, "bill db insert success!!");
                    checkServerTag();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d(TAG, "requestAssemblyman response fail:" + statusCode);
                Toast.makeText(getActivity().getApplicationContext(), "서버접속 실패!! 잠시후에 다시 시도하세요.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestCommitteeMeeting() {

        Log.d(TAG, "requestCommitteeMeeting start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tag", mDbTag.getTagList().get(2));

        String url = getResources().getString(R.string.server_url) + getResources().getString(R.string.server_name) + getResources().getString(R.string.server_request_committee);
        client.post(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(ProgressBar.VISIBLE);
            }

            @Override
            public void onProgress(int position, int length) {
                super.onProgress(position, length);


                Log.d(TAG, "ProgressBar position:" + position / 1000 + " length:" + length);
                mProgressBar.setProgress(position / 1000);
//                mProgressBar.setMax(length);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.d(TAG, "requestAssemblyman result:" + content);

                Gson gson = new GsonBuilder().create();
                List<CommitteeMeeting> List = gson.fromJson(content, new TypeToken<List<CommitteeMeeting>>() {
                }.getType());

                if (mDatabase.insertCommitteeMeetingList(List)) {
                    Log.d(TAG, "committee db insert success!!");
                    checkServerTag();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d(TAG, "requestAssemblyman response fail:" + statusCode);
                Toast.makeText(getActivity().getApplicationContext(), "서버접속 실패!! 잠시후에 다시 시도하세요.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestGeneralMeeting() {

        Log.d(TAG, "requestCommitteeMeeting start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tag", mDbTag.getTagList().get(3));

        String url = getResources().getString(R.string.server_url) + getResources().getString(R.string.server_name) + getResources().getString(R.string.server_request_general);
        client.post(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(ProgressBar.VISIBLE);
            }

            @Override
            public void onProgress(int position, int length) {
                super.onProgress(position, length);


                Log.d(TAG, "ProgressBar position:" + position / 1000 + " length:" + length);
                mProgressBar.setProgress(position / 1000);
//                mProgressBar.setMax(length);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.d(TAG, "requestAssemblyman result:" + content);

                Gson gson = new GsonBuilder().create();
                List<GeneralMeeting> List = gson.fromJson(content, new TypeToken<List<GeneralMeeting>>() {
                }.getType());

                if (mDatabase.insertGneralMeetingList(List)) {
                    Log.d(TAG, "general db insert success!!");
                    checkServerTag();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d(TAG, "requestAssemblyman response fail:" + statusCode);
                Toast.makeText(getActivity().getApplicationContext(), "서버접속 실패!! 잠시후에 다시 시도하세요.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestPartyHistory() {

        Log.d(TAG, "requestCommitteeMeeting start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tag", mDbTag.getTagList().get(4));

        String url = getResources().getString(R.string.server_url) + getResources().getString(R.string.server_name) + getResources().getString(R.string.server_request_party);
        client.post(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(ProgressBar.VISIBLE);
            }

            @Override
            public void onProgress(int position, int length) {
                super.onProgress(position, length);


                Log.d(TAG, "ProgressBar position:" + position / 1000 + " length:" + length);
                mProgressBar.setProgress(position / 1000);
//                mProgressBar.setMax(length);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.d(TAG, "requestAssemblyman result:" + content);

                Gson gson = new GsonBuilder().create();
                List<PartyHistory> List = gson.fromJson(content, new TypeToken<List<PartyHistory>>() {
                }.getType());

                if (mDatabase.insertPartyHistoryList(List)) {
                    Log.d(TAG, "party db insert success!!");
                    checkServerTag();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d(TAG, "requestAssemblyman response fail:" + statusCode);
                Toast.makeText(getActivity().getApplicationContext(), "서버접속 실패!! 잠시후에 다시 시도하세요.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestVote() {

        Log.d(TAG, "requestCommitteeMeeting start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tag", mDbTag.getTagList().get(5));

        String url = getResources().getString(R.string.server_url) + getResources().getString(R.string.server_name) + getResources().getString(R.string.server_request_vote);
        client.post(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(ProgressBar.VISIBLE);
            }

            @Override
            public void onProgress(int position, int length) {
                super.onProgress(position, length);


                Log.d(TAG, "ProgressBar position:" + position / 1000 + " length:" + length);
                mProgressBar.setProgress(position / 1000);
//                mProgressBar.setMax(length);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.d(TAG, "requestAssemblyman result:" + content);

                Gson gson = new GsonBuilder().create();
                List<Vote> List = gson.fromJson(content, new TypeToken<List<Vote>>() {
                }.getType());

                if (mDatabase.insertVoteList(List)) {
                    Log.d(TAG, "party db insert success!!");
                    checkServerTag();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                Log.d(TAG, "requestAssemblyman response fail:" + statusCode);
                Toast.makeText(getActivity().getApplicationContext(), "서버접속 실패!! 잠시후에 다시 시도하세요.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
