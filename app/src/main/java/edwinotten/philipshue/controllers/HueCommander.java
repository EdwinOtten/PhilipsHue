package edwinotten.philipshue.controllers;

import android.os.AsyncTask;

/**
 * Created by Edwin on 17-9-2014.
 */
public class HueCommander extends AsyncTask<String, Void, Void> {
    protected String action;
    protected String data;

    public HueCommander(String action, String data) {
        this.action = action;
        this.data = data;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String test = RestClient.put(this.action, this.data);
        return null;
    }
}