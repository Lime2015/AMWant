package com.lime.amwant.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lime.amwant.R;
import com.lime.amwant.listitem.AssemblymanListItem;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public RVAssemblymenListAdapter(List<AssemblymanListItem> tables) {
        this.tables = tables;
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
        new ImageLoadTask(url, dataViewHolder.assPhoto).execute();
    }



    @Override
    public int getItemCount() {
        return tables.size();
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }
}