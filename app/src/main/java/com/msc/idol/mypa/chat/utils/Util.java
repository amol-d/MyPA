package com.msc.idol.mypa.chat.utils;

import com.msc.idol.mypa.chat.model.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


/**
 * Created by AMOL on 12/6/2015.
 */
public class Util {
    private static String new_message_received = "{\"from_id\":\"1619\",\"to_id\":\"1\",\"sender\":\"client\",\"sender_name\":\"Rahul Sharma\",\"client_message\":\"I am interested in Sovereign Gold Bond Scheme 2015 please ask advisor to get in touch with me\"}";
    private static String new_message_sent = "{\"from_id\":\"1619\",\"to_id\":\"1\",\"sender\":\"client\",\"sender_name\":\"Rahul Sharma\",\"receiver_message\":\"I am interested in Sovereign Gold Bond Scheme 2015 please ask advisor to get in touch with me\"}";

    public static Message getNewMessage() {
        try {
            JSONObject newchat = null;
            int number = new Random().nextInt();
            String msgContent;
            boolean isMine = true;
            if (number % 2 == 0) {
                newchat = new JSONObject(new_message_received);
            } else {
                newchat = new JSONObject(new_message_sent);
            }
            try {
                msgContent = newchat.getString("client_message");
                isMine = false;

            } catch (Exception e) {
                msgContent = newchat.getString("receiver_message");
                isMine = true;
            }
            return new Message(newchat.getString("from_id"),
                    newchat.getString("to_id"),
                    newchat.getString("sender"),
                    newchat.getString("sender_name"),
                    msgContent, isMine);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
