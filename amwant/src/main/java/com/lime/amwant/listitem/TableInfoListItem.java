package com.lime.amwant.listitem;

/**
 * Created by SeongSan on 2015-07-14.
 */
public class TableInfoListItem {
    private String tableName;
    private int lastTag;
    private int icTable;
    private int icRefresh;

    public TableInfoListItem() {
    }

    public TableInfoListItem(String tableName, int lastTag, int icTable, int icRefresh) {
        this.tableName = tableName;
        this.lastTag = lastTag;
        this.icTable = icTable;
        this.icRefresh = icRefresh;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getLastTag() {
        return lastTag;
    }

    public void setLastTag(int lastTag) {
        this.lastTag = lastTag;
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
}
