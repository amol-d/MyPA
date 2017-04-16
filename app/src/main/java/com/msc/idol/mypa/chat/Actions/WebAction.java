package com.msc.idol.mypa.chat.actions;

import com.msc.idol.mypa.model.web.WebResult;
import com.msc.idol.mypa.model.web.WebUtils;
import com.msc.idol.mypa.network.WebhoseIOClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by adesai on 3/15/2017.
 */

public class WebAction implements Action {
    String input;

    @Override
    public ArrayList<String> getRecognizer() {
        ArrayList<String> queries = new ArrayList<>();
        queries.add("web");
        queries.add("internet");
        return queries;
    }

    @Override
    public boolean isActionFitting(String input) {
        for (String q : getRecognizer()) {
            if (q.toLowerCase().trim().contains(input.toLowerCase().trim())) {
                this.input = input;
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<WebResult> execute() {
        WebhoseIOClient ioClient = WebhoseIOClient.getInstance(WebUtils.WEBHOSE_API_KEY);
        try {
            return ioClient.query("filterWebData", input);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setQuery(String query) {
        this.input = query;
    }
}
