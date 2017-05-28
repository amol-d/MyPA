package com.msc.idol.mypa.model.web;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by adesai on 3/14/2017.
 */

public class WebResult extends RealmObject implements Serializable {
    long id;
    private String title, author, text, url, language;

    public WebResult(){}
    public WebResult(String title, String author, String text, String url, String language) {
        this.title = title;
        this.author = author;
        this.text = text;
        this.url = url;
        this.language = language;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
