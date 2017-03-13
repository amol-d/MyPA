package com.msc.idol.mypa.chat.service;

import android.app.IntentService;
import android.content.Intent;

import com.msc.idol.mypa.MyPAActivity;
import com.msc.idol.mypa.chat.model.Message;
import com.msc.idol.mypa.chat.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ChatService extends IntentService {

    private static final String POST = "post";
    private static String sampleJson = "{\"post\":[{\"from_id\":\"1619\",\"to_id\":\"1\",\"sender\":\"client\",\"sender_name\":\"Rahul Sharma\",\"client_message\":\"I am interested in Sovereign Gold Bond Scheme 2015 please ask advisor to get in touch with me\"},{\"from_id\":\"1619\",\"to_id\":\"1\",\"sender\":\"client\",\"sender_name\":\"Rahul Sharma\",\"client_message\":\"I am interested in ELSS Funds please ask advisor to get in touch with me\"},{\"from_id\":\"8\",\"to_id\":\"1619\",\"sender\":\"advisor_head\",\"sender_name\":\"Kashinath More\",\"receiver_message\":\"We tried reach you at the number registered with us, however couldn't get though, Kindly provide your alternate contact details or feasible time to get in touch with you.\"},{\"from_id\":\"1619\",\"to_id\":\"1\",\"sender\":\"client\",\"sender_name\":\"Rahul Sharma\",\"client_message\":\"Ok\"},{\"from_id\":\"8\",\"to_id\":\"1619\",\"sender\":\"advisor_head\",\"sender_name\":\"Kashinath More\",\"receiver_message\":\"With reference to conversation had with our Advisor, hope your query is resolved, kindly feel free to revert for further assistance.\"},{\"from_id\":\"1619\",\"to_id\":\"3\",\"sender\":\"client\",\"sender_name\":\"Rahul Sharma\",\"client_message\":\"hi\"},{\"from_id\":\"8\",\"to_id\":\"1619\",\"sender\":\"advisor_head\",\"sender_name\":\"Kashinath More\",\"receiver_message\":\"Test Id\"},{\"from_id\":\"1619\",\"to_id\":\"1\",\"sender\":\"\",\"sender_name\":null,\"receiver_message\":\"I am interested in Sovereign Gold Bond Scheme 2015 please ask advisor to get in touch with me\"},{\"from_id\":\"1619\",\"to_id\":\"1\",\"sender\":\"\",\"sender_name\":null,\"receiver_message\":\"I am interested in Sovereign Gold Bond Scheme 2015 please ask advisor to get in touch with me\"}],\"success\":\"1\"}";

    public ChatService() {
        super("ChatService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            ArrayList<Message> messages = new ArrayList<>();
            String msgContent = "";
            boolean isMine = true;

            JSONObject chat = new JSONObject(sampleJson);
            JSONArray jsonMessages = chat.getJSONArray(POST);
            for (int i = 0; i < jsonMessages.length(); i++) {
                JSONObject msg = jsonMessages.getJSONObject(i);
                try {
                    msgContent = msg.getString("client_message");
                    isMine = false;

                } catch (Exception e) {
                    msgContent = msg.getString("receiver_message");
                    isMine = true;
                }
                messages.add(new Message(msg.getString("from_id"),
                        msg.getString("to_id"),
                        msg.getString("sender"),
                        msg.getString("sender_name"),
                        msgContent, isMine));
            }


            //TODO we are good to go???
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(MyPAActivity.ResponseReceiver.ACTION_RESP);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra(Constants.INTENT_MSGS_EXTRA, (ArrayList) messages);
            sendBroadcast(broadcastIntent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
