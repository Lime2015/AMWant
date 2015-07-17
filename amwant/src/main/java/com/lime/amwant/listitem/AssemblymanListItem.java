package com.lime.amwant.listitem;

/**
 * Created by SeongSan on 2015-06-30.
 */
public class AssemblymanListItem {
    private int idPhoto;
    private String assemblymanName;
    private String partyName;
    private String localConstituency;
    private int countLike;
    private int countDislike;

    public AssemblymanListItem(){}

    public AssemblymanListItem(int idPhoto, String assemblymanName, String partyName, String localConstituency, int countLike, int countDislike) {
        this.idPhoto = idPhoto;
        this.assemblymanName = assemblymanName;
        this.partyName = partyName;
        this.localConstituency = localConstituency;
        this.countLike = countLike;
        this.countDislike = countDislike;
    }

    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
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
