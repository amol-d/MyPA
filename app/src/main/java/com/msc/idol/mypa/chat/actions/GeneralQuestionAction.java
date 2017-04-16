package com.msc.idol.mypa.chat.actions;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by adesai on 3/17/2017.
 */

public class GeneralQuestionAction implements Action {
    int matchingIndex;
    @Override
    public ArrayList<String> getRecognizer() {
        return new ArrayList<>(Arrays.asList("What can you do", "what makes you happy", "Where do you live"));
    }

    @Override
    public boolean isActionFitting(String input) {
        boolean isFitting = false;
        ArrayList<String> queries = getRecognizer();
        for (int i = 0; i < queries.size(); i++) {
            if (input.toLowerCase().trim().contains(queries.get(i).toLowerCase().trim())) {
                matchingIndex = i;
                isFitting = true;
                break;
            }
        }
        return isFitting;
    }

    @Override
    public String execute() {
        String reply = "";
        switch (matchingIndex) {
            case 0:
                reply = "I can help you with weather updates/news/quotes and many more.";
                break;
            case 1:
                reply = "Helping you out makes me happy.";
                break;
            case 2:
                reply = "In your phone! :)";
                break;
        }
        return reply;
    }
}
