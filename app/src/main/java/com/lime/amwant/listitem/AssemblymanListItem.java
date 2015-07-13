package com.lime.amwant.listitem;

/**
 * Created by SeongSan on 2015-06-30.
 */
public class AssemblymanListItem {
    private String imageUrl;
    private String assemblymanName;
    private String partyName;
    private String localConstituency;

    public AssemblymanListItem(){}

    public AssemblymanListItem(String imageUrl, String assemblymanName, String partyName, String localConstituency) {
        this.imageUrl = imageUrl;
        this.assemblymanName = assemblymanName;
        this.partyName = partyName;
        this.localConstituency = localConstituency;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAssemblymanName() {
        return assemblymanName;
    }

    public void setAssemblymanName(String assemblymanName) {
        this.assemblymanName = assemblymanName;
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
}
