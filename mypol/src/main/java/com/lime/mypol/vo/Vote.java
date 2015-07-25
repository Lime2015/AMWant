package com.lime.mypol.vo;

/**
 * Created by AMD on 2015-07-25.
 */
public class Vote {
    private String assemblyman_id;
    private Integer update_tag;

    private String bill_name;
    private Integer bill_no;
    private String vote_dttm;
    private String mod_dttm;
    private String bill_target_url;
    private String result;
    private String assemblyman_vote;

    public Vote() {
    }

    public Vote(String assemblyman_id, Integer update_tag, String bill_name, Integer bill_no, String vote_dttm, String mod_dttm, String bill_target_url, String result, String assemblyman_vote) {
        this.assemblyman_id = assemblyman_id;
        this.update_tag = update_tag;
        this.bill_name = bill_name;
        this.bill_no = bill_no;
        this.vote_dttm = vote_dttm;
        this.mod_dttm = mod_dttm;
        this.bill_target_url = bill_target_url;
        this.result = result;
        this.assemblyman_vote = assemblyman_vote;
    }

    public String getAssemblyman_id() {
        return assemblyman_id;
    }

    public void setAssemblyman_id(String assemblyman_id) {
        this.assemblyman_id = assemblyman_id;
    }

    public Integer getUpdate_tag() {
        return update_tag;
    }

    public void setUpdate_tag(Integer update_tag) {
        this.update_tag = update_tag;
    }

    public String getBill_name() {
        return bill_name;
    }

    public void setBill_name(String bill_name) {
        this.bill_name = bill_name;
    }

    public Integer getBill_no() {
        return bill_no;
    }

    public void setBill_no(Integer bill_no) {
        this.bill_no = bill_no;
    }

    public String getVote_dttm() {
        return vote_dttm;
    }

    public void setVote_dttm(String vote_dttm) {
        this.vote_dttm = vote_dttm;
    }

    public String getMod_dttm() {
        return mod_dttm;
    }

    public void setMod_dttm(String mod_dttm) {
        this.mod_dttm = mod_dttm;
    }

    public String getBill_target_url() {
        return bill_target_url;
    }

    public void setBill_target_url(String bill_target_url) {
        this.bill_target_url = bill_target_url;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAssemblyman_vote() {
        return assemblyman_vote;
    }

    public void setAssemblyman_vote(String assemblyman_vote) {
        this.assemblyman_vote = assemblyman_vote;
    }
}
