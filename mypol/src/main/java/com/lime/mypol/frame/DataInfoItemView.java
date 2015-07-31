package com.lime.mypol.frame;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lime.mypol.R;
import com.lime.mypol.listitem.BillItem;
import com.lime.mypol.listitem.DataInfoItem;

/**
 * Created by Administrator on 2015-07-30.
 */
public class DataInfoItemView extends FrameLayout {
    public DataInfoItemView(Context context) {
        super(context);
        init();
    }

    CardView cv;
    TextView tableName;
    TextView appTag;
    TextView serverTag;
    ImageView icTable;
    Button btnRefresh;
    DataInfoItem mData;
    private void init() {
        inflate(getContext(), R.layout.card_mypage_data, this);
        cv = (CardView) findViewById(R.id.cv_mypage_data);
        tableName = (TextView) findViewById(R.id.table_name);
        appTag = (TextView) findViewById(R.id.app_tag);
        serverTag = (TextView) findViewById(R.id.server_tag);
        icTable = (ImageView) findViewById(R.id.ic_table);
        btnRefresh = (Button) findViewById(R.id.btn_refresh);
    }

    public void setItem(final DataInfoItem item) {
        mData = item;
        tableName.setText(item.getDataName());
        appTag.setText("app tag : " + item.getAppTag());
        serverTag.setText("server tag : " + item.getServerTag());
        icTable.setImageResource(item.getIcTable());
        btnRefresh.setEnabled(item.isEnabled());
    }
}
