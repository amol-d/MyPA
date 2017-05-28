package com.msc.idol.mypa.model.news;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by adesai on 3/14/2017.
 */

public class News extends RealmObject implements Serializable {
    long id;
    String title, desc, url, imageUrl;

    public News(){}
    public News(String title, String desc, String url, String imageUrl) {
        this.title = title;
        this.desc = desc;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
