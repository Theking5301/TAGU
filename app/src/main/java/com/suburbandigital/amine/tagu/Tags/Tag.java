package com.suburbandigital.amine.tagu.Tags;

import com.suburbandigital.amine.tagu.Math.Vec2;

/**
 * Created by Amine on 10/20/2015.
 */
public class Tag {
    private String NAME;
    private String DESCRIPTION;
    private TagType TYPE;
    private Vec2 POSITION;


    public Tag(String name, String description, TagType type, Vec2 position) {
        NAME = name;
        DESCRIPTION = description;
        TYPE = type;
        POSITION = position;
    }

    /**
     * Creates a Tag using X and Y coordinates instead of a vector.
     */
    public Tag(String name, String description, TagType type, double x, double y) {
        NAME = name;
        DESCRIPTION = description;
        TYPE = type;
        POSITION = new Vec2(x,y);
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
    public Vec2 getPOSITION() {
        return POSITION;
    }
    public double getX() {
        return POSITION.getX();
    }
    public double getY() {
        return POSITION.getY();
    }
}
