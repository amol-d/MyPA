package com.msc.idol.mypa.chat.actions;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by amol.
 */

public class ActionManager {
    private ArrayList<Action> actionList = new ArrayList<>();

    public ActionManager() {
        actionList.add(new TimeAction());
        actionList.add(new GreetingAction());
        actionList.add(new WebAction());
        actionList.add(new GeneralQuestionAction());
        actionList.add(new NewsAction());
        actionList.add(new QuoteAction());
        actionList.add(new WeatherAction());
    }

    public Object execute(String input) {
        Action action = findAction(input);
        return action.execute();
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
}
