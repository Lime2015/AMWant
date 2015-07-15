package com.lime.amwant.vo;

/**
 * Created by SeongSan on 2015-07-15.
 */
public class Assemblyman {
    private String assemblymanId;
    private String assemblymanName;
    private String imageUrl;
    private String orgImageUrl;
    private String mod_dttm;
    private int partyId;
    private String partyName;
    private String localConstituency;
    private int updateTag;

    public Assemblyman(){}

    public Assemblyman(String assemblymanId, String assemblymanName, String imageUrl, String orgImageUrl, String mod_dttm, int partyId, String partyName, String localConstituency, int updateTag) {
        this.assemblymanId = assemblymanId;
        this.assemblymanName = assemblymanName;
        this.imageUrl = imageUrl;
        this.orgImageUrl = orgImageUrl;
        this.mod_dttm = mod_dttm;
        this.partyId = partyId;
        this.partyName = partyName;
        this.localConstituency = localConstituency;
        this.updateTag = updateTag;
    }

    public String getAssemblymanId() {
        return assemblymanId;
    }

    public void setAssemblymanId(String assemblymanId) {
        this.assemblymanId = assemblymanId;
    }

    public String getAssemblymanName() {
        return assemblymanName;
    }

    public void setAssemblymanName(String assemblymanName) {
        this.assemblymanName = assemblymanName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOrgImageUrl() {
        return orgImageUrl;
    }

    public void setOrgImageUrl(String orgImageUrl) {
        this.orgImageUrl = orgImageUrl;
    }

    public String getMod_dttm() {
        return mod_dttm;
    }

    public void setMod_dttm(String mod_dttm) {
        this.mod_dttm = mod_dttm;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getLocalConstituency() {
        return localConstituency;
    }

    public void setLocalConstituency(String localConstituency) {
        this.localConstituency = localConstituency;
    }

    public int getUpdateTag() {
        return updateTag;
    }

    public void setUpdateTag(int updateTag) {
        this.updateTag = updateTag;
    }
}
