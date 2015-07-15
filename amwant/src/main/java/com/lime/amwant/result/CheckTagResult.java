package com.lime.amwant.result;

/**
 * Created by SeongSan on 2015-07-15.
 */
public class CheckTagResult {
    private int assemblymanTag;
    private int billTag;
    private int committeeMeetingTag;
    private int generalMeetingTag;
    private int partyHistoryTag;
    private int voteTag;

    public CheckTagResult(){}

    public CheckTagResult(int assemblymanTag, int billTag, int committeeMeetingTag, int generalMeetingTag, int partyHistoryTag, int voteTag) {
        this.assemblymanTag = assemblymanTag;
        this.billTag = billTag;
        this.committeeMeetingTag = committeeMeetingTag;
        this.generalMeetingTag = generalMeetingTag;
        this.partyHistoryTag = partyHistoryTag;
        this.voteTag = voteTag;
    }

    public int getAssemblymanTag() {
        return assemblymanTag;
    }

    public void setAssemblymanTag(int assemblymanTag) {
        this.assemblymanTag = assemblymanTag;
    }

    public int getBillTag() {
        return billTag;
    }

    public void setBillTag(int billTag) {
        this.billTag = billTag;
    }

    public int getCommitteeMeetingTag() {
        return committeeMeetingTag;
    }

    public void setCommitteeMeetingTag(int committeeMeetingTag) {
        this.committeeMeetingTag = committeeMeetingTag;
    }

    public int getGeneralMeetingTag() {
        return generalMeetingTag;
    }

    public void setGeneralMeetingTag(int generalMeetingTag) {
        this.generalMeetingTag = generalMeetingTag;
    }

    public int getPartyHistoryTag() {
        return partyHistoryTag;
    }

    public void setPartyHistoryTag(int partyHistoryTag) {
        this.partyHistoryTag = partyHistoryTag;
    }

    public int getVoteTag() {
        return voteTag;
    }

    public void setVoteTag(int voteTag) {
        this.voteTag = voteTag;
    }
}
