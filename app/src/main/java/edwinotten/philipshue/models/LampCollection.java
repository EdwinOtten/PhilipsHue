package edwinotten.philipshue.models;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

import edwinotten.philipshue.controllers.LampCollectionUpdater;

/**
 * Created by Edwin on 23-9-2014.
 */
public class LampCollection extends Observable {

    public HashMap<Integer, Lamp> lamps = new HashMap<Integer, Lamp>();
    public long lastUpdate = 0;
    private static final LampCollection instance = new LampCollection();
    public JSONObject previousJSON = new JSONObject();

    public static LampCollection getInstance() {
        return instance;
    }

    private LampCollection() {
        this.update();
    }

    public void update() {
        long now = System.currentTimeMillis() / 1000L;
        if (this.lastUpdate == 0L || now - this.lastUpdate > 30L) {
            LampCollectionUpdater lampCollectionUpdater = new LampCollectionUpdater(this);
            lampCollectionUpdater.execute();
        }
    }

    public void markAsUpdated() {
        setChanged();
        notifyObservers();
    }

    public String[] getLampNames() {
        String[] lampNames = new String[this.lamps.size()];
        Iterator it = lamps.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            Lamp lamp = (Lamp) pairs.getValue();
            lampNames[i] = lamp.name;
            i++;
        }
        return lampNames;
    }
}