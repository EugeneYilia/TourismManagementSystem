package com.EugeneStudio.core;

public class Description {
    private String spotName;
    private String spotDescription;
    private int loveDegree;//0--10
    private boolean hasRestPlace;
    private boolean hasToilet;

    public Description() {
    }

    public Description(String spotName, String spotDescription, int loveDegree, boolean hasRestPlace, boolean hasToilet) {
        this.spotName = spotName;
        this.spotDescription = spotDescription;
        this.loveDegree = loveDegree;
        this.hasRestPlace = hasRestPlace;
        this.hasToilet = hasToilet;
    }

    public String getSpotName() {
        return spotName;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public String getSpotDescription() {
        return spotDescription;
    }

    public void setSpotDescription(String spotDescription) {
        this.spotDescription = spotDescription;
    }

    public int getLoveDegree() {
        return loveDegree;
    }

    public void setLoveDegree(int loveDegree) {
        this.loveDegree = loveDegree;
    }

    public boolean hasRestPlace() {
        return hasRestPlace;
    }

    public void setHasRestPlace(boolean hasRestPlace) {
        this.hasRestPlace = hasRestPlace;
    }

    public boolean hasToilet() {
        return hasToilet;
    }

    public void setHasToilet(boolean hasToilet) {
        this.hasToilet = hasToilet;
    }
}
