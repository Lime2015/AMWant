package com.lime.mypol.listitem;

/**
 * Created by SeongSan on 2015-07-14.
 */
public class DataInfoItem {
    private String dataName;
    private int appTag;
    private int serverTag;
    private int icTable;
    private boolean enabled;

    public DataInfoItem() {
    }

    public DataInfoItem(String dataName, int appTag, int serverTag, int icTable, boolean enabled) {
        this.dataName = dataName;
        this.appTag = appTag;
        this.serverTag = serverTag;
        this.icTable = icTable;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public int getAppTag() {
        return appTag;
    }

    public void setAppTag(int appTag) {
        this.appTag = appTag;
    }

    public int getIcTable() {
        return icTable;
    }

    public void setIcTable(int icTable) {
        this.icTable = icTable;
    }

    public int getServerTag() {
        return serverTag;
    }

    public void setServerTag(int serverTag) {
        this.serverTag = serverTag;
    }

}
