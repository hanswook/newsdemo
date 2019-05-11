package com.hans.newslook.test.custom;

/**
 * @author Hans
 * @create 2019/1/7
 * @Describe
 */
public class Ball implements Cloneable {


    public float aX;
    public float ay;
    public float vX;
    public float vY;
    public float x;
    public float y;
    public float radius;
    public int color;


    @Override
    protected Ball clone() throws CloneNotSupportedException {
        Ball bounceBall = null;
        bounceBall = (Ball) super.clone();
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
