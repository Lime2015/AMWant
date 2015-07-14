package com.lime.amwant.vo;

/**
 * Created by SeongSan on 2015-07-14.
 */
public class TableInfo {
    private String tableName;
    private int lastTag;
    private int icTable;

    public TableInfo() {
    }

    public TableInfo(String tableName, int lastTag, int icTable) {
        this.tableName = tableName;
        this.lastTag = lastTag;
        this.icTable = icTable;
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
}
