package edwinotten.philipshue.controllers;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import edwinotten.philipshue.models.Lamp;
import edwinotten.philipshue.models.LampCollection;
import edwinotten.philipshue.models.LampState;

/**
 * Created by Edwin on 23-9-2014.
 */
public class LampCollectionUpdater extends AsyncTask<String, Void, Void> {
    protected LampCollection lampCollection;

    public LampCollectionUpdater(LampCollection lampCollection) {
        this.lampCollection = lampCollection;
    }

    @Override
    protected Void doInBackground(String... strings) {
        JSONObject newLampData = RestClient.getLampData();
        // If the previous JSON response was the same no lamps are changed, no need to update the models.
        if (this.lampCollection.previousJSON.equals(newLampData)) {
            return null;
        }

        // If the previous JSON response was different we go and rebuild our LampCollection with the new data.
        this.lampCollection.lamps.clear();

        for (int i = 1; i <= newLampData.length(); i++) {
            try {
                JSONObject newLamp = newLampData.getJSONObject(Integer.toString(i));
                Lamp lamp = new Lamp(
                        this.extractLampState(newLamp.getString("state")),
                        newLamp.getString("type"),
                        newLamp.getString("name"),
                        newLamp.getString("modelid"),
                        newLamp.getString("swversion")
                );

                // Add the new Lamp object to the collection
                this.lampCollection.lamps.put(new Integer(i), lamp);

            } catch (JSONException ex) {
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("test");
            }
        }
        // Mark the collection as updated, so it will notify it's observers
        this.lampCollection.markAsUpdated();
        return null;
    }

    protected static LampState extractLampState(String jsonState) {
        LampState state = new LampState();
        try {
            JSONObject stateData = new JSONObject(jsonState);
            state.on = stateData.getString("on").equals("true") ? true : false;
            state.bri = Integer.parseInt(stateData.getString("bri"));
            state.hue = Integer.parseInt(stateData.getString("hue"));
            state.sat = Integer.parseInt(stateData.getString("sat"));
            state.ct = Integer.parseInt(stateData.getString("ct"));
            state.alert = stateData.getString("alert");
            state.effect = stateData.getString("effect");
            state.colormode = stateData.getString("colormode");
            state.reachable = stateData.getString("reachable").equals("true") ? true : false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return state;
    }
}