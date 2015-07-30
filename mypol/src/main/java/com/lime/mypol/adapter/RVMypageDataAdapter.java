package com.lime.mypol.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lime.mypol.R;
import com.lime.mypol.listitem.DataInfoItem;

import java.util.List;

public class RVMypageDataAdapter extends RecyclerView.Adapter<RVMypageDataAdapter.DataViewHolder> {

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView tableName;
        TextView appTag;
        TextView serverTag;
        ImageView icTable;
        ImageButton btnRefresh;

        DataViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_mypage_data);
            tableName = (TextView) itemView.findViewById(R.id.table_name);
            appTag = (TextView) itemView.findViewById(R.id.app_tag);
            serverTag = (TextView) itemView.findViewById(R.id.server_tag);
            icTable = (ImageView) itemView.findViewById(R.id.ic_table);
            btnRefresh = (ImageButton) itemView.findViewById(R.id.btn_refresh);
        }
    }

    List<DataInfoItem> tables;

    public RVMypageDataAdapter(List<DataInfoItem> tables) {
        this.tables = tables;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_mypage_data, viewGroup, false);
        DataViewHolder pvh = new DataViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(DataViewHolder dataViewHolder, int i) {
        dataViewHolder.tableName.setText(tables.get(i).getDataName());
        dataViewHolder.appTag.setText("app tag : " + tables.get(i).getAppTag());
        dataViewHolder.serverTag.setText("server tag : " + tables.get(i).getServerTag());
        dataViewHolder.icTable.setImageResource(tables.get(i).getIcTable());
        dataViewHolder.btnRefresh.setImageResource(tables.get(i).getIcRefresh());
    }



    @Override
    public int getItemCount() {
        return tables.size();
    }
}
