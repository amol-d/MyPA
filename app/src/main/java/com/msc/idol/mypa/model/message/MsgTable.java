package com.msc.idol.mypa.model.message;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by amdam_000 on 5/28/2017.
 */

public class MsgTable extends RealmObject {
    @PrimaryKey
    long id;
    private String message;
    private boolean isMine;

    public MsgTable() {
    }

    public MsgTable(String client_message, boolean isMine) {
        this.message = client_message;
        this.isMine = isMine;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMine(boolean mine) {
        isMine = mine;
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