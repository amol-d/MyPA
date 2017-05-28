package com.msc.idol.mypa.model.quote;

import io.realm.RealmObject;

/**
 * Created by adesai on 3/14/2017.
 */
public class Quote extends RealmObject {
    long id;
    private String title, content, link;

    public Quote(){}
    public Quote(String title, String content, String link) {
        this.title = title;
        this.content = content;
        this.link = link;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
