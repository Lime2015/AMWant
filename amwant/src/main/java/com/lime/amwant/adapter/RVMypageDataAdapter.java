package com.lime.amwant.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lime.amwant.R;
import com.lime.amwant.listitem.TableInfoListItem;

import java.util.List;

public class RVMypageDataAdapter extends RecyclerView.Adapter<RVMypageDataAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView tableName;
        TextView appTag;
        TextView serverTag;
        ImageView icTable;
        ImageButton btnRefresh;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_mypage_data);
            tableName = (TextView) itemView.findViewById(R.id.table_name);
            appTag = (TextView) itemView.findViewById(R.id.app_tag);
            serverTag = (TextView) itemView.findViewById(R.id.server_tag);
            icTable = (ImageView) itemView.findViewById(R.id.ic_table);
            btnRefresh = (ImageButton) itemView.findViewById(R.id.btn_refresh);
        }
    }

    List<TableInfoListItem> tables;

    public RVMypageDataAdapter(List<TableInfoListItem> tables) {
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
        personViewHolder.appTag.setText("app tag : " + tables.get(i).getAppTag());
        personViewHolder.serverTag.setText("server tag : " + tables.get(i).getServerTag());
        personViewHolder.icTable.setImageResource(tables.get(i).getIcTable());
        personViewHolder.btnRefresh.setImageResource(tables.get(i).getIcRefresh());
    }



    @Override
    public int getItemCount() {
        return tables.size();
    }
}
