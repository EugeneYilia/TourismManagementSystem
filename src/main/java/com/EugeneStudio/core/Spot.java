package com.EugeneStudio.core;

public class Spot {
    private String sourcePlace;
    private String destinationPlace;
    private int distance;

    public Spot() {
    }

    public Spot(String sourcePlace, String destinationPlace, int distance) {
        this.sourcePlace = sourcePlace;
        this.destinationPlace = destinationPlace;
        this.distance = distance;
    }

    public String getSourcePlace() {
        return sourcePlace;
    }

    public String getDestinationPlace() {
        return destinationPlace;
    }

    public int getDistance() {
        return distance;
    }

    public void setSourcePlace(String sourcePlace) {
        this.sourcePlace = sourcePlace;
    }

    public void setDestinationPlace(String destinationPlace) {
        this.destinationPlace = destinationPlace;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
