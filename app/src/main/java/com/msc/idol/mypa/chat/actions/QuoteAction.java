package com.msc.idol.mypa.chat.actions;

import com.msc.idol.mypa.model.quote.Quote;
import com.msc.idol.mypa.network.QuoteClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by adesai on 3/15/2017.
 */

public class QuoteAction implements Action {
    String input;

    @Override
    public ArrayList<String> getRecognizer() {
        ArrayList<String> queries = new ArrayList<>();
        queries.add("inspiration");
        queries.add("quote");
        queries.add("inspire");
        return queries;
    }

    @Override
    public boolean isActionFitting(String input) {
        for (String q : getRecognizer()) {
            if (q.toLowerCase().contains(input.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Quote execute() {
        QuoteClient quoteClient = new QuoteClient();
        try {
            return quoteClient.getQuote();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
