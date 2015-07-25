package com.lime.mypol.vo;

/**
 * Created by AMD on 2015-07-25.
 */
public class PartyHistory {

    private Integer update_tag;

    private Integer member_seq;
    private String party_name;
    private String in_date;
    private String out_date;
    private String note;

    public PartyHistory() {
    }

    public PartyHistory(Integer update_tag, Integer member_seq, String party_name, String in_date, String out_date, String note) {
        this.update_tag = update_tag;
        this.member_seq = member_seq;
        this.party_name = party_name;
        this.in_date = in_date;
        this.out_date = out_date;
        this.note = note;
    }

    public Integer getUpdate_tag() {
        return update_tag;
    }

    public void setUpdate_tag(Integer update_tag) {
        this.update_tag = update_tag;
    }

    public Integer getMember_seq() {
        return member_seq;
    }

    public void setMember_seq(Integer member_seq) {
        this.member_seq = member_seq;
    }

    public String getParty_name() {
        return party_name;
    }

    public void setParty_name(String party_name) {
        this.party_name = party_name;
    }

    public String getIn_date() {
        return in_date;
    }

    public void setIn_date(String in_date) {
        this.in_date = in_date;
    }

    public String getOut_date() {
        return out_date;
    }

    public void setOut_date(String out_date) {
        this.out_date = out_date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
