package com.msc.idol.mypa.chat.actions;

import java.util.ArrayList;

/**
 *
 */
public interface Action {
    ArrayList<String> getRecognizer();
    boolean isActionFitting(String input);
    Object execute();
}
