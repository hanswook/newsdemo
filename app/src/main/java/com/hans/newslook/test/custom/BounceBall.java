package com.hans.newslook.test.custom;

/**
 * @author Hans
 * @create 2019/1/7
 * @Describe
 */
public class BounceBall implements Cloneable {


    private float aX;
    private float ay;
    private float vX;
    private float vY;
    private float x;
    private float y;
    private float radius;
    private int color;


    @Override
    protected BounceBall clone() throws CloneNotSupportedException {
        BounceBall bounceBall = null;
        bounceBall = (BounceBall) super.clone();
        return bounceBall;
    }


    public float getaX() {
        return aX;
    }

    public float getAy() {
        return ay;
    }

    public float getvX() {
        return vX;
    }

    public float getvY() {
        return vY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public int getColor() {
        return color;
    }
}
