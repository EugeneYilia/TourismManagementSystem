package com.EugeneStudio.core;

public class DistanceSpot {
    private String name;
    private DistanceSpot previousDistanceSpot;
    private int distanceSum;

    public DistanceSpot() {
    }

    public DistanceSpot(DistanceSpot distanceSpot) {
        this.previousDistanceSpot = distanceSpot;
    }

    public DistanceSpot(DistanceSpot distanceSpot, int distanceSum, String name) {
        this.previousDistanceSpot = distanceSpot;
        this.distanceSum = distanceSum;
        this.name = name;
    }

    public DistanceSpot(int distanceSum, String name) {
        this.distanceSum = distanceSum;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistanceSum() {
        return distanceSum;
    }

    public void setDistanceSum(int distanceSum) {
        this.distanceSum = distanceSum;
    }

    public DistanceSpot getPreviousDistanceSpot() {
        return previousDistanceSpot;
    }

    public void setPreviousDistanceSpot(DistanceSpot previousDistanceSpot) {
        this.previousDistanceSpot = previousDistanceSpot;
    }
}
