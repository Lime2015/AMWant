package com.lime.mypol.listitem;

/**
 * Created by SeongSan on 2015-06-30.
 */
public class AssemblymanItem {
    private String urlPhoto;
    private String assemblymanName;
    private String partyName;
    private String localConstituency;
    private int countLike;
    private int countDislike;

    public AssemblymanItem(){}

    public AssemblymanItem(String urlPhoto, String assemblymanName, String partyName, String localConstituency, int countLike, int countDislike) {
        this.urlPhoto = urlPhoto;
        this.assemblymanName = assemblymanName;
        this.partyName = partyName;
        this.localConstituency = localConstituency;
        this.countLike = countLike;
        this.countDislike = countDislike;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
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
