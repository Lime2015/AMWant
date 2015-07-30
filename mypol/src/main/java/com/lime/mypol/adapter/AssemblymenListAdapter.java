package com.lime.mypol.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lime.mypol.frame.AssemblymanItemView;
import com.lime.mypol.listitem.AssemblymanItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-07-30.
 */
public class AssemblymenListAdapter extends BaseAdapter {
    private List<AssemblymanItem> items = new ArrayList<>();
    private int sortIndex;
    private int totalCount;

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AssemblymanItemView view;
        if (convertView == null) {
            view = new AssemblymanItemView(parent.getContext());
        } else {
            view = (AssemblymanItemView)convertView;
        }
        view.setItem(items.get(position));
        return view;
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public int getStart() {
        if (items.size() < totalCount) {
            return items.size() + 1;
        }
        return -1;
    }
    public void addAll(List<AssemblymanItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }
}
