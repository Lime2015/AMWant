package com.lime.mypol.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lime.mypol.R;
import com.lime.mypol.adapter.MypageDataAdapter;
import com.lime.mypol.listitem.DataInfoItem;
import com.lime.mypol.manager.DatabaseManager;
import com.lime.mypol.manager.NetworkManager;
import com.lime.mypol.result.CheckTagResult;
import com.lime.mypol.vo.Assemblyman;
import com.lime.mypol.vo.Bill;
import com.lime.mypol.vo.CommitteeMeeting;
import com.lime.mypol.vo.GeneralMeeting;
import com.lime.mypol.vo.PartyHistory;
import com.lime.mypol.vo.Vote;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SeongSan on 2015-07-14.
 */
public class MypageDataFragment extends Fragment {

    private static String TAG = "MypageDataFragment";
    private final String DOWNLOAD_ERROR = "다운로드 실패:";
    private final String DB_INSERT_ERROR = "DB insert 실패:";

    private Context mContext;
    private MypageDataAdapter mAdapter;
    private ListView listView;
    private List<Boolean> mDownloadList;

    private CheckTagResult mServerTag;
    private CheckTagResult mDbTag;

    public static MypageDataFragment newInstance() {
        MypageDataFragment f = new MypageDataFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_mypage_data, container, false);

        mContext = rootView.getContext();

        listView = (ListView) rootView.findViewById(R.id.list_mypage_data);
        mAdapter = new MypageDataAdapter();
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = position - 1;
                if (mDownloadList.get(index)) {
                    int tag = mDbTag.getTagList().get(index);
                    switch (position) {
                        case 0:
                            NetworkManager.getInstance().requestAssemblyman(tag, new NetworkManager.OnNetResultListener<List<Assemblyman>>() {
                                @Override
                                public void onSuccess(List<Assemblyman> result) {
                                    if (DatabaseManager.getInstance(mContext).insertAssemblymanList(result)) {
                                        checkServerTag();
                                    } else {
                                        Toast.makeText(mContext, DB_INSERT_ERROR + "국회의원", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFail(int code) {
                                    Toast.makeText(mContext, DOWNLOAD_ERROR + code, Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 1:
                            NetworkManager.getInstance().requestBill(tag, new NetworkManager.OnNetResultListener<List<Bill>>() {
                                @Override
                                public void onSuccess(List<Bill> result) {
                                    if (DatabaseManager.getInstance(mContext).insertBillList(result)) {
                                        checkServerTag();
                                    } else {
                                        Toast.makeText(mContext, DB_INSERT_ERROR + "의안", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFail(int code) {
                                    Toast.makeText(mContext, DOWNLOAD_ERROR + code, Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 2:
                            NetworkManager.getInstance().requestCommitteeMeeting(tag, new NetworkManager.OnNetResultListener<List<CommitteeMeeting>>() {
                                @Override
                                public void onSuccess(List<CommitteeMeeting> result) {
                                    if (DatabaseManager.getInstance(mContext).insertCommitteeMeetingList(result)) {
                                        checkServerTag();
                                    } else {
                                        Toast.makeText(mContext, DB_INSERT_ERROR + "상임위원회", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFail(int code) {
                                    Toast.makeText(mContext, DOWNLOAD_ERROR + code, Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 3:
                            NetworkManager.getInstance().requestGeneralMeeting(tag, new NetworkManager.OnNetResultListener<List<GeneralMeeting>>() {
                                @Override
                                public void onSuccess(List<GeneralMeeting> result) {
                                    if (DatabaseManager.getInstance(mContext).insertGneralMeetingList(result)) {
                                        checkServerTag();
                                    } else {
                                        Toast.makeText(mContext, DB_INSERT_ERROR + "본회의", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFail(int code) {
                                    Toast.makeText(mContext, DOWNLOAD_ERROR + code, Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 4:
                            NetworkManager.getInstance().requestPartyHistory(tag, new NetworkManager.OnNetResultListener<List<PartyHistory>>() {
                                @Override
                                public void onSuccess(List<PartyHistory> result) {
                                    if (DatabaseManager.getInstance(mContext).insertPartyHistoryList(result)) {
                                        checkServerTag();
                                    } else {
                                        Toast.makeText(mContext, DB_INSERT_ERROR + "정당", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFail(int code) {
                                    Toast.makeText(mContext, DOWNLOAD_ERROR + code, Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case 5:
                            NetworkManager.getInstance().requestVote(tag, new NetworkManager.OnNetResultListener<List<Vote>>() {
                                @Override
                                public void onSuccess(List<Vote> result) {
                                    if (DatabaseManager.getInstance(mContext).insertVoteList(result)) {
                                        checkServerTag();
                                    } else {
                                        Toast.makeText(mContext, DB_INSERT_ERROR + "표결", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFail(int code) {
                                    Toast.makeText(mContext, DOWNLOAD_ERROR + code, Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                    }
                }
            }
        });

        initializeData();
        checkServerTag();

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void initializeData() {

        List<DataInfoItem> items = new ArrayList<>();
        items.add(new DataInfoItem("국회의원", 0, 0, R.drawable.assemblyman, R.drawable.check_orange));
        items.add(new DataInfoItem("의안", 0, 0, R.drawable.bill, R.drawable.check_orange));
        items.add(new DataInfoItem("상임위원회", 0, 0, R.drawable.committee, R.drawable.check_orange));
        items.add(new DataInfoItem("본회의", 0, 0, R.drawable.assembly, R.drawable.check_orange));
        items.add(new DataInfoItem("정당", 0, 0, R.drawable.committee, R.drawable.check_orange));
        items.add(new DataInfoItem("표결", 0, 0, R.drawable.assembly, R.drawable.check_orange));
        mAdapter.addAll(items);

        mDownloadList = new ArrayList<>();
        mDownloadList.add(false);
        mDownloadList.add(false);
        mDownloadList.add(false);
        mDownloadList.add(false);
        mDownloadList.add(false);
        mDownloadList.add(false);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

    private void checkServerTag() {
        NetworkManager.getInstance().checkServerTag(new NetworkManager.OnNetResultListener<CheckTagResult>() {
            @Override
            public void onSuccess(CheckTagResult result) {
                mServerTag = result;
                mDbTag = DatabaseManager.getInstance(mContext).checkTag();

                for (int i = 0; i < mServerTag.getTagList().size(); i++) {
                    DataInfoItem item = (DataInfoItem) mAdapter.getItem(i);
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
            public void onFail(int code) {
                Toast.makeText(mContext, DOWNLOAD_ERROR + code, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
