package com.suburbandigital.amine.tagu.Tags;

/**
 * Created by Amine on 10/20/2015.
 */
public class UserPosition {
    private String NAME;
    private double X;
    private double Y;

    public UserPosition(String name, String description, TagType type, double x, double y) {
        NAME = name;
        X = x;
        Y = y;
    }
    public String getNAME() {
        return NAME;
    }
    public double getX() {
        return X;
    }
    public double getY() {
        return Y;
    }
}
