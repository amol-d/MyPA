package com.msc.idol.mypa.chat.model;

import java.io.Serializable;

/**
 * Created by AMOL on 12/6/2015.
 */
public class Message implements Serializable {
    private String from_id;
    private String to_id;
    private String sender;
    private String sender_name;
    private String message;
    private boolean isMine;

    public Message(String from_id, String to_id, String sender, String sender_name, String client_message, boolean isMine) {
        this.from_id = from_id;
        this.to_id = to_id;
        this.sender = sender;
        this.sender_name = sender_name;
        this.message = client_message;
        this.isMine = isMine;
    }

    ;

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
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

    public enum MessageSender {
        ME,
        CLIENT
    }
}
