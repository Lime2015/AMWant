package com.lime.mypol.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lime.mypol.R;
import com.lime.mypol.vo.Bill;

import java.util.List;

/**
 * Created by Administrator on 2015-07-21.
 */
public class RVBillListAdapter  extends RecyclerView.Adapter<RVBillListAdapter.BillViewHolder> {

    public static class BillViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
//        TextView assName;
//        TextView assPartyName;
//        TextView assLocation;
//        TextView countLike;
//        TextView countDislike;
//        ImageView assPhoto;

        BillViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_bill);
//            assName = (TextView) itemView.findViewById(R.id.ass_name);
//            assPartyName = (TextView) itemView.findViewById(R.id.ass_party_name);
//            assLocation = (TextView) itemView.findViewById(R.id.ass_location);
//            countLike = (TextView) itemView.findViewById(R.id.count_like);
//            countDislike = (TextView) itemView.findViewById(R.id.count_dislike);
//            assPhoto = (ImageView) itemView.findViewById(R.id.ass_photo);
        }
    }

    List<Bill> tables;

    public RVBillListAdapter(List<Bill> tables) {
        this.tables = tables;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public BillViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_assemblymanlist, viewGroup, false);
        BillViewHolder pvh = new BillViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(BillViewHolder dataViewHolder, int i) {
//        dataViewHolder.assName.setText(tables.get(i).getAssemblymanName());
//        dataViewHolder.assPartyName.setText(tables.get(i).getPartyName());
//        dataViewHolder.assLocation.setText(tables.get(i).getLocalConstituency());
//        dataViewHolder.countLike.setText("" + tables.get(i).getCountLike());
//        dataViewHolder.countDislike.setText("" + tables.get(i).getCountDislike());

    }



    @Override
    public int getItemCount() {
        return tables.size();
    }

}