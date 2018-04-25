package com.hans.newslook.model.bean.zhihu;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hans.newslook.utils.Constants;

import java.util.List;


/**
 * Created by hans on 2017/5/11 16:51.
 */

public class StoriesBean implements MultiItemEntity {
    /**
     * images : ["https://pic4.zhimg.com/v2-abf262f484c3cc4d9282749f1fccc1ef.jpg"]
     * type : 0
     * id : 9412923
     * ga_prefix : 051114
     * title : 它已经诞生了一年，可「国产主机」却似乎离我们更远了
     * delegateType: 0 是数据 1是日期item.
     * dataDate 是日期item
     */
    private int _id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    private int type;
    private String dataDate;
    private int id;
    private String ga_prefix;
    private String title;
    private List<String> images;


    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


    private int itemType = Constants.ZHIHU_ITEMCONTENT;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
