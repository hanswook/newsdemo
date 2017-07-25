package com.demo.newsdemo.bean;

/**
 * Created by hans on 2017/5/11 16:52.
 */

public class TopStoriesBean {
    /**
     * image : https://pic1.zhimg.com/v2-6b8c3f499f0d027b23407381f67422fc.jpg
     * type : 0
     * id : 9412923
     * ga_prefix : 051114
     * title : 它已经诞生了一年，可「国产主机」却似乎离我们更远了
     */

    private String image;
    private int type;
    private int id;
    private String ga_prefix;
    private String title;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
