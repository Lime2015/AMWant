package com.lime.amwant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lime.amwant.R;
import com.lime.amwant.adapter.RVMypageDataAdapter;
import com.lime.amwant.vo.TableInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SeongSan on 2015-07-14.
 */
public class MypageDataFragment extends Fragment {

    private RecyclerView rv;
    private List<TableInfo> tables;

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

        initializeData();
        initializeAdapter();

        return rootView;
    }

    private void initializeData() {
        tables = new ArrayList<>();
        tables.add(new TableInfo("국회의원", 0, R.drawable.assemblyman));
    }

    private void initializeAdapter() {
        RVMypageDataAdapter adapter = new RVMypageDataAdapter(tables);
        rv.setAdapter(adapter);
    }
}
