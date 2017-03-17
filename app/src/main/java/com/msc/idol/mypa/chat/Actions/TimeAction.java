package com.msc.idol.mypa.chat.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * Created by foellerich on 16.10.2015.
 *
 */
class TimeAction implements Action {
    public ArrayList<String> getRecognizer() {
        return new ArrayList<>(Arrays.asList(
                "What is time",
                "What's the time",
                "time"
        ));
    }

    @Override
    public boolean isActionFitting(String input) {
        boolean isFitting = true;
        for (String s : getRecognizer()) {
            if (!input.toLowerCase().contains(s.toLowerCase())) {
                isFitting = false;
                break;
            }
        }
        return isFitting;
    }

    public String execute() {
        String date = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date());
        return "Time is " + date + "";
    }
}
