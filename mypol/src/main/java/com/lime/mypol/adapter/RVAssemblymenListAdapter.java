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
import com.lime.mypol.listitem.AssemblymanListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SeongSan on 2015-07-17.
 */
public class RVAssemblymenListAdapter extends RecyclerView.Adapter<RVAssemblymenListAdapter.AssemblymanViewHolder> {

    public static class AssemblymanViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView assName;
        TextView assPartyName;
        TextView assLocation;
        TextView countLike;
        TextView countDislike;
        ImageView assPhoto;

        AssemblymanViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_assemblyman);
            assName = (TextView) itemView.findViewById(R.id.ass_name);
            assPartyName = (TextView) itemView.findViewById(R.id.ass_party_name);
            assLocation = (TextView) itemView.findViewById(R.id.ass_location);
            countLike = (TextView) itemView.findViewById(R.id.count_like);
            countDislike = (TextView) itemView.findViewById(R.id.count_dislike);
            assPhoto = (ImageView) itemView.findViewById(R.id.ass_photo);
        }
    }

    List<AssemblymanListItem> tables;
    Context context;

    public RVAssemblymenListAdapter(Context context, List<AssemblymanListItem> tables) {
        this.tables = tables;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AssemblymanViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_assemblymanlist, viewGroup, false);
        AssemblymanViewHolder pvh = new AssemblymanViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(AssemblymanViewHolder dataViewHolder, int i) {
        dataViewHolder.assName.setText(tables.get(i).getAssemblymanName());
        dataViewHolder.assPartyName.setText(tables.get(i).getPartyName());
        dataViewHolder.assLocation.setText(tables.get(i).getLocalConstituency());
        dataViewHolder.countLike.setText("" + tables.get(i).getCountLike());
        dataViewHolder.countDislike.setText("" + tables.get(i).getCountDislike());

        //사진 다운로드
        String url = tables.get(i).getUrlPhoto();
        //new ImageLoadTask(url, dataViewHolder.assPhoto).execute();
        Picasso.with(context).load(url).into(dataViewHolder.assPhoto);
    }



    @Override
    public int getItemCount() {
        return tables.size();
    }

}