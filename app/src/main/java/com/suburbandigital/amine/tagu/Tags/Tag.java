package com.suburbandigital.amine.tagu.Tags;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suburbandigital.amine.tagu.Math.Vec2;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Amine on 10/20/2015.
 */
public class Tag implements Serializable {
    private String NAME;
    private String DESCRIPTION;
    private String ENTITY;
    private TagType TYPE;
    private Vec2 POSITION;
    private boolean PRIVATE;
    private final long ID;

    public Tag(String name, String description, String entity, TagType type, Vec2 position) {
        NAME = name;
        DESCRIPTION = description;
        TYPE = type;
        POSITION = position;
        ENTITY = entity;
        Random rand = new Random();
        ID = rand.nextInt()%100000000;
    }

    /**
     * Creates a Tag using X and Y coordinates instead of a vector.
     */
    public Tag(String name, String description, String entity, TagType type, double lat, double longi) {
        NAME = name;
        DESCRIPTION = description;
        ENTITY = entity;
        TYPE = type;
        POSITION = new Vec2(lat,longi);
        Random rand = new Random();
        ID = rand.nextInt(0x10) + 0x10;
    }
    public long getID() {
        return ID;
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
    public String getENTITY() {
        return ENTITY;
    }
    public Vec2 getPOSITION() {
        return POSITION;
    }
    public MarkerOptions toMarkerOptions() {
        LatLng tagLL = new LatLng(this.getLat(), this.getLong());
        MarkerOptions options = new MarkerOptions().position(tagLL).title(getNAME()).snippet(getDESCRIPTION());
        return options;
    }
    /*
    * Lat = X Coord
    * */
    public double getLat() {
        return POSITION.getX();
    }
    /*
    * Long = Y Coord
    * */
    public double getLong() {
        return POSITION.getY();
    }
    @Override
    public String toString() {
        return "TAG [id=" + ID + ", name=" + NAME + ", desc=" + DESCRIPTION + ", ent=" + ENTITY + ", type=" + TYPE + ", posx=" + POSITION.getX() + "posy=" + POSITION.getY() + "]";
    }
}
