package com.lime.amwant.listitem;

/**
 * Created by SeongSan on 2015-07-14.
 */
public class TableInfoListItem {
    private String tableName;
    private int appTag;
    private int serverTag;
    private int icTable;
    private int icRefresh;

    public TableInfoListItem() {
    }

    public TableInfoListItem(String tableName, int appTag, int serverTag, int icTable, int icRefresh) {
        this.tableName = tableName;
        this.appTag = appTag;
        this.serverTag = serverTag;
        this.icTable = icTable;
        this.icRefresh = icRefresh;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

    public int getIcRefresh() {
        return icRefresh;
    }

    public void setIcRefresh(int icRefresh) {
        this.icRefresh = icRefresh;
    }

    public int getServerTag() {
        return serverTag;
    }

    public void setServerTag(int serverTag) {
        this.serverTag = serverTag;
    }

}
