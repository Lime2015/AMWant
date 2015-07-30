package com.lime.mypol.frame;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lime.mypol.R;
import com.lime.mypol.listitem.AssemblymanItem;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2015-07-30.
 */
public class AssemblymanItemView extends FrameLayout {
    public AssemblymanItemView(Context context) {
        super(context);
        init();
    }

    CardView cv;
    TextView assName;
    TextView assPartyName;
    TextView assLocation;
    TextView countLike;
    TextView countDislike;
    ImageView assPhoto;
    AssemblymanItem mData;
    private void init() {
        inflate(getContext(), R.layout.card_assemblymanlist, this);
        cv = (CardView) findViewById(R.id.cv_assemblyman);
        assName = (TextView) findViewById(R.id.ass_name);
        assPartyName = (TextView) findViewById(R.id.ass_party_name);
        assLocation = (TextView) findViewById(R.id.ass_location);
        countLike = (TextView) findViewById(R.id.count_like);
        countDislike = (TextView) findViewById(R.id.count_dislike);
        assPhoto = (ImageView) findViewById(R.id.ass_photo);
    }

    public void setItem(final AssemblymanItem item) {
        mData = item;
        assName.setText(item.getAssemblymanName());
        assPartyName.setText(item.getPartyName());
        assLocation.setText(item.getLocalConstituency());
        countLike.setText("" + item.getCountLike());
        countDislike.setText("" + item.getCountDislike());
        ImageLoader.getInstance().displayImage(item.getUrlPhoto(), assPhoto);
    }
}
