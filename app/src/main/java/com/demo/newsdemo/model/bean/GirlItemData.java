package com.demo.newsdemo.model.bean;

/**
 * Created by hans on 2017/8/13 17:08.
 */

public class GirlItemData {
    private String title;
    private String url;
    private String id;
    private int width;
    private int height;
    private String subtype;

    public GirlItemData() {
    }

    public GirlItemData(String title, String url, String id, int width, int height, String subtype) {
        this.title = title;
        this.url = url;
        this.id = id;
        this.width = width;
        this.height = height;
        this.subtype = subtype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }
}
