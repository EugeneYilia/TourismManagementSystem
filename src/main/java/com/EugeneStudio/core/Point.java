package com.EugeneStudio.core;

public class Point {
    private String spotName;
    private int edgeNumber;

    public Point() {
    }

    public Point(String spotName) {
        this.spotName = spotName;
    }

    public Point(String spotName, int edgeNumber) {
        this.spotName = spotName;
        this.edgeNumber = edgeNumber;
    }

    public String getSpotName() {
        return spotName;
    }

    public int getEdgeNumber() {
        return edgeNumber;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public void setEdgeNumber(int edgeNumber) {
        this.edgeNumber = edgeNumber;
    }
}
