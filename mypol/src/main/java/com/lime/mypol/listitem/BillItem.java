package com.lime.mypol.listitem;

/**
 * Created by AMD on 2015-07-25.
 */
public class BillItem {
    private String proposerInfo;
    private String billStatus;
    private String billTitle;
    private String committeeName;
    private String referDate;
    private int countLike;
    private int countDislike;

    public BillItem() {
    }

    public BillItem(String proposerInfo, String billStatus, String billTitle, String committeeName, String referDate, int countLike, int countDislike) {
        this.proposerInfo = proposerInfo;
        this.billStatus = billStatus;
        this.billTitle = billTitle;
        this.committeeName = committeeName;
        this.referDate = referDate;
        this.countLike = countLike;
        this.countDislike = countDislike;
    }

    public String getProposerInfo() {
        return proposerInfo;
    }

    public void setProposerInfo(String proposerInfo) {
        this.proposerInfo = proposerInfo;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getBillTitle() {
        return billTitle;
    }

    public void setBillTitle(String billTitle) {
        this.billTitle = billTitle;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public String getReferDate() {
        return referDate;
    }

    public void setReferDate(String referDate) {
        this.referDate = referDate;
    }

    public int getCountLike() {
        return countLike;
    }

    public void setCountLike(int countLike) {
        this.countLike = countLike;
    }

    public int getCountDislike() {
        return countDislike;
    }

    public void setCountDislike(int countDislike) {
        this.countDislike = countDislike;
    }
}
