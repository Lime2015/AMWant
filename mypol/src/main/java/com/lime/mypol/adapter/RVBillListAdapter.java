package com.lime.mypol.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lime.mypol.R;
import com.lime.mypol.listitem.BillItem;

import java.util.List;

/**
 * Created by Administrator on 2015-07-21.
 */
public class RVBillListAdapter  extends RecyclerView.Adapter<RVBillListAdapter.BillViewHolder> {

    public static class BillViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView billTitle;
        TextView billCommitteeName;
        TextView billDate;
        TextView billStatue;
        TextView countLike;
        TextView countDislike;
//        ImageView billPhoto;

        BillViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_bill);
            billTitle = (TextView) itemView.findViewById(R.id.bill_title);
            billCommitteeName = (TextView) itemView.findViewById(R.id.bill_committee_name);
            billDate = (TextView) itemView.findViewById(R.id.bill_date);
            billStatue = (TextView) itemView.findViewById(R.id.bill_statue);
            countLike = (TextView) itemView.findViewById(R.id.count_like);
            countDislike = (TextView) itemView.findViewById(R.id.count_dislike);
//            billPhoto = (ImageView) itemView.findViewById(R.id.bill_photo);
        }
    }

    List<BillItem> tables;
    Context context;

    public RVBillListAdapter(Context context, List<BillItem> tables) {
        this.tables = tables;
        this.context = context;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public BillViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_billlist, viewGroup, false);
        BillViewHolder pvh = new BillViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(BillViewHolder dataViewHolder, int i) {
        dataViewHolder.billTitle.setText(tables.get(i).getBillTitle());
        dataViewHolder.billCommitteeName.setText(tables.get(i).getCommitteeName());
        dataViewHolder.billDate.setText(tables.get(i).getReferDate());
        dataViewHolder.billStatue.setText(tables.get(i).getBillStatus());
        dataViewHolder.countLike.setText("" + tables.get(i).getCountLike());
        dataViewHolder.countDislike.setText("" + tables.get(i).getCountDislike());

    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

}