package com.suburbandigital.amine.tagu.Tags;

/**
 * Created by Amine on 10/20/2015.
 */
public class Tag {
    private String NAME;
    private String DESCRIPTION;
    private TagType TYPE;
    private double X;
    private double Y;

    public Tag(String name, String description, TagType type, double x, double y) {
        NAME = name;
        DESCRIPTION = description;
        TYPE = type;
        X = x;
        Y = y;
    }
    public String getNAME() {
        return NAME;
    }
    public String getDESCRIPTION() {
        return DESCRIPTION;
    }
    public TagType getTYPE() {
        return TYPE;
    }
    public double getX() {
        return X;
    }
    public double getY() {
        return Y;
    }
}
