package com.company;

import java.util.Comparator;

public class Distance implements Comparable<Distance> {
    private double distance;
    private int label;

    public Distance(double distance, int label) {
        this.distance = distance;
        this.label = label;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "distance=" + distance +
                ", label=" + label +
                '}';
    }

    @Override
    public int compareTo(Distance o) {
        if(this.distance >= o.getDistance()) {
            return 1;
        } else if (this.distance <= o.getDistance()) {
            return -1;
        }
        return 0;
    }
}
