package com.EugeneStudio.core;

public class Vertex {
    private String spotName;
    private int degree;

    public Vertex() {
    }

    public Vertex(String spotName, int degree) {
        this.spotName = spotName;
        this.degree = degree;
    }

    public String getSpotName() {
        return spotName;
    }

    public int getDegree() {
        return degree;
    }

    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
