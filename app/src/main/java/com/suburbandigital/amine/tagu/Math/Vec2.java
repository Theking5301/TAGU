package com.suburbandigital.amine.tagu.Math;

/**
 * Created by Amine on 10/20/2015.
 */
public class Vec2 {
    private double X;
    private double Y;
    private int too;

    public Vec2(double x, double y) {
        X = x;
        Y = y;
    }

    public double getX() {
        return X;
    }
    public void setX(double x) {
        X = x;
    }
    public double getY() {
        return Y;
    }
    public void setY(double y) {
        Y = y;
    }
    public Vec2 add(Vec2 vector) {
        double x = vector.getX() + X;
        double y = vector.getY() + Y;

        return new Vec2(x, y);
    }
    public Vec2 subtract(Vec2 vector) {
        double x = vector.getX() - X;
        double y = vector.getY() - Y;

        return new Vec2(x, y);
    }
    public Vec2 componentMult(Vec2 vector) {
        double x = vector.getX() * X;
        double y = vector.getY() * Y;

        return new Vec2(x, y);
    }
    public Vec2 componentDiv(Vec2 vector) {
        double x;
        double y;
        if(X > 0 && Y > 0) {
            x = vector.getX() / X;
            y = vector.getY() / Y;
        }else{
            x = vector.getX();
            y = vector.getY();
        }
        return new Vec2(x, y);
    }
    public Vec2 scale(double scaleFactor) {
        double x = X * scaleFactor;
        double y = Y * scaleFactor;

        return new Vec2(x, y);
    }
    public Vec2 shrink(double scaleFactor) {
        double x;
        double y;
        if(scaleFactor > 0) {
            x = X / scaleFactor;
            y = Y / scaleFactor;
        }else{
            x = X;
            y = Y;
        }
        return new Vec2(x, y);
    }
    public double dot(Vec2 vector) {
        return (X * vector.getX()) + (Y * vector.getY());
    }
    public double length() {
        return Math.sqrt((X*X) + (Y*Y));
    }
    /**
     * Usefull when needing to compare vectors. Returns the longest without having to run a square root operation.
     * @return the summed square of both components.
     */
    public double shortLength() {
        return ((X * X) + (Y * Y));
    }
}
