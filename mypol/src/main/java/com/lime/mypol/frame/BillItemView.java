package com.lime.mypol.frame;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lime.mypol.R;
import com.lime.mypol.listitem.AssemblymanItem;
import com.lime.mypol.listitem.BillItem;
import com.lime.mypol.listitem.DataInfoItem;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2015-07-30.
 */
public class BillItemView extends FrameLayout {
    public BillItemView(Context context) {
        super(context);
        init();
    }

    CardView cv;
    TextView billTitle;
    TextView billCommitteeName;
    TextView billDate;
    TextView billStatue;
    TextView countLike;
    TextView countDislike;
    BillItem mData;
    private void init() {
        inflate(getContext(), R.layout.card_billlist, this);
        cv = (CardView) findViewById(R.id.cv_bill);
        billTitle = (TextView) findViewById(R.id.bill_title);
        billCommitteeName = (TextView) findViewById(R.id.bill_committee_name);
        billDate = (TextView) findViewById(R.id.bill_date);
        billStatue = (TextView) findViewById(R.id.bill_statue);
        countLike = (TextView) findViewById(R.id.count_like);
        countDislike = (TextView) findViewById(R.id.count_dislike);
    }

    public void setItem(final BillItem item) {
        mData = item;
        billTitle.setText(item.getBillTitle());
        billCommitteeName.setText(item.getCommitteeName());
        billDate.setText(item.getReferDate());
        billStatue.setText(item.getBillStatus());
        countLike.setText("" + item.getCountLike());
        countDislike.setText("" + item.getCountDislike());
    }
}
