package com.msc.idol.mypa.chat.actions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.msc.idol.mypa.AssistantResponseReceiver;
import com.msc.idol.mypa.MyPAApp;

import java.util.ArrayList;

/**
 * Created by amol.
 */

public class ActionManager {
    private ArrayList<Action> actionList = new ArrayList<>();

    public ActionManager() {
        actionList.add(new GeneralQuestionAction());
        actionList.add(new GreetingAction());
        actionList.add(new TimeAction());
        actionList.add(new NewsAction());
        actionList.add(new QuoteAction());
        actionList.add(new WeatherAction());
        actionList.add(new WebAction());
    }

    public Object execute(Activity context, String input, AssistantResponseReceiver receiver) {

        //Action action = findAction(input);
        //return action.execute();
        new HandlerRequestLoader(context, input, receiver).execute();
        return null;
    }

    private Action findAction(String inputString) {
        for (Action action : actionList) {
            if (action.isActionFitting(inputString)) {
                return action;
            }
        }
        if (!TextUtils.isEmpty(inputString)) {
            WebAction webAction = new WebAction();
            webAction.setQuery(inputString);
            return webAction;
        } else {
            return new DefaultAction();
        }
    }
//
//    private boolean contains(String input, ArrayList<String> needles) {
//        boolean returns = true;
//        for (String needle : needles) {
//            if (!input.contains(needle)) {
//                returns = false;
//            }
//        }
//        return returns;
//    }

    private class DefaultAction implements Action {

        @Override
        public ArrayList<String> getRecognizer() {
            return null;
        }

        @Override
        public boolean isActionFitting(String input) {
            return true;
        }

        @Override
        public String execute() {
            return "I did not catch what you just said";
        }
    }

    class HandlerRequestLoader extends AsyncTask<Void, Void, Object> {

        private Activity context;
        private String input;
        private AssistantResponseReceiver receiver;
        private ProgressDialog progressDialog;

        public HandlerRequestLoader(Activity context, String input, AssistantResponseReceiver receiver) {
            this.context = context;
            this.input = input;
            this.receiver = receiver;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(this.context, "Loading", "Please wait...", true);
        }

        @Override
        protected Object doInBackground(Void... voids) {

            Action action = findAction(input);
            return action.execute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
            this.receiver.responseReceived(o);
        }
    }
}
