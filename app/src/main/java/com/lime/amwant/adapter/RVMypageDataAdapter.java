package com.lime.amwant.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lime.amwant.R;
import com.lime.amwant.vo.TableInfo;

import java.util.List;

public class RVMypageDataAdapter extends RecyclerView.Adapter<RVMypageDataAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView tableName;
        TextView lastTag;
        ImageView icTable;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_mypage_data);
            tableName = (TextView) itemView.findViewById(R.id.table_name);
            lastTag = (TextView) itemView.findViewById(R.id.last_tag);
            icTable = (ImageView) itemView.findViewById(R.id.ic_table);
        }
    }

    List<TableInfo> tables;

    public RVMypageDataAdapter(List<TableInfo> tables) {
        this.tables = tables;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_mypage_data, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.tableName.setText(tables.get(i).getTableName());
        personViewHolder.lastTag.setText("last:" + tables.get(i).getLastTag());
        personViewHolder.icTable.setImageResource(tables.get(i).getIcTable());
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }
}
