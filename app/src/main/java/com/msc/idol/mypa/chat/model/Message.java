package com.msc.idol.mypa.chat.model;

import com.msc.idol.mypa.chat.actions.Action;

import java.io.Serializable;

/**
 * Created by AMOL on 12/6/2015.
 */
public class Message implements Serializable {
    private String message;
    private boolean isMine;
    private Object action;
    public Message(String client_message, Object action, boolean isMine) {
        this.action = action;
        this.message = client_message;
        this.isMine = isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
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
