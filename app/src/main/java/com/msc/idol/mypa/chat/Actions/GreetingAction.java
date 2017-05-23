package com.msc.idol.mypa.chat.actions;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by foellerich on 16.10.2015.
 *
 */
public class GreetingAction implements Action {
    @Override
    public ArrayList<String> getRecognizer() {
        return new ArrayList<>(Arrays.asList(
                "hello",
                "hi",
                "hey"
        ));
    }

    @Override
    public boolean isActionFitting(String input) {
        boolean isFitting = false;
        for (String s : getRecognizer()) {
            if (input.toLowerCase().trim().contains(s.toLowerCase().trim())) {
                isFitting = true;
                break;
            }
        }
        return isFitting;
    }

    @Override
    public String execute() {
        return "Hi";
    }
}
