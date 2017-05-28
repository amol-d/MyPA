package com.msc.idol.mypa.model.message;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by amdam_000 on 5/27/2017.
 */

public class Message {
    long id;
    private String message;
    private boolean isMine;
    private Object action;

    public Message() {}
    public Message(String client_message, Object action, boolean isMine) {
        this.action = action;
        this.message = client_message;
        this.isMine = isMine;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getData() {
        return action;
    }

    public void setAction(Object action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }

}
