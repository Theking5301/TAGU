package com.suburbandigital.amine.tagu.Tags;

import com.suburbandigital.amine.tagu.Math.Vec2;

/**
 * Created by Amine on 10/20/2015.
 */
public class UserPosition {
    private Vec2 POSITION;
    public UserPosition(double x, double y) {
        POSITION = new Vec2(x,y);
    }
    public UserPosition(Vec2 position) {
        POSITION = position;
    }
    public double getX() {
        return POSITION.getX();
    }
    public double getY() {
        return POSITION.getY();
    }
    public Vec2 getPOSITION() {
        return POSITION;
    }
}
