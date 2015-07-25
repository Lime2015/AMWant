package com.lime.mypol.vo;

/**
 * Created by AMD on 2015-07-25.
 */
public class GeneralMeeting {

    private String assemblyman_id;
    private Integer update_tag;

    private Integer meeting_id;
    private String meeting_order;
    private String meeting_dttm;
    private String mod_dttm;
    private String attend_status;

    public GeneralMeeting() { }

    public GeneralMeeting(String assemblyman_id, Integer update_tag, Integer meeting_id, String meeting_order, String meeting_dttm, String mod_dttm, String attend_status) {
        this.assemblyman_id = assemblyman_id;
        this.update_tag = update_tag;
        this.meeting_id = meeting_id;
        this.meeting_order = meeting_order;
        this.meeting_dttm = meeting_dttm;
        this.mod_dttm = mod_dttm;
        this.attend_status = attend_status;
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

    public Integer getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(Integer meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getMeeting_order() {
        return meeting_order;
    }

    public void setMeeting_order(String meeting_order) {
        this.meeting_order = meeting_order;
    }

    public String getMeeting_dttm() {
        return meeting_dttm;
    }

    public void setMeeting_dttm(String meeting_dttm) {
        this.meeting_dttm = meeting_dttm;
    }

    public String getMod_dttm() {
        return mod_dttm;
    }

    public void setMod_dttm(String mod_dttm) {
        this.mod_dttm = mod_dttm;
    }

    public String getAttend_status() {
        return attend_status;
    }

    public void setAttend_status(String attend_status) {
        this.attend_status = attend_status;
    }
}
