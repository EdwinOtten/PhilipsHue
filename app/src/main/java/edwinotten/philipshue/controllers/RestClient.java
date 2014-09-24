package edwinotten.philipshue.controllers;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Edwin on 17-9-2014.
 */
public class RestClient {

    protected static final String API_BASE_URL = "http://192.168.1.2/api/edwinotten";

    public static JSONObject getLampData() {
        String apiResponse = get("/lights");
//        String apiResponse = "{\"1\":{\"state\": {\"on\":true,\"bri\":254,\"hue\":46920,\"sat\":254,\"xy\":[0.1691,0.0441],\"ct\":500,\"alert\":\"select\",\"effect\":\"none\",\"colormode\":\"hs\",\"reachable\":true}, \"type\": \"Extended color light\", \"name\": \"Hue Lamp 1\", \"modelid\": \"LCT001\", \"swversion\": \"66009663\", \"pointsymbol\": { \"1\":\"none\", \"2\":\"none\", \"3\":\"none\", \"4\":\"none\", \"5\":\"none\", \"6\":\"none\", \"7\":\"none\", \"8\":\"none\" }},\"2\":{\"state\": {\"on\":true,\"bri\":254,\"hue\":46920,\"sat\":254,\"xy\":[0.1691,0.0441],\"ct\":500,\"alert\":\"select\",\"effect\":\"none\",\"colormode\":\"hs\",\"reachable\":true}, \"type\": \"Extended color light\", \"name\": \"Hue Lamp 2\", \"modelid\": \"LCT001\", \"swversion\": \"66009663\", \"pointsymbol\": { \"1\":\"none\", \"2\":\"none\", \"3\":\"none\", \"4\":\"none\", \"5\":\"none\", \"6\":\"none\", \"7\":\"none\", \"8\":\"none\" }},\"3\":{\"state\": {\"on\":true,\"bri\":254,\"hue\":46920,\"sat\":254,\"xy\":[0.1691,0.0441],\"ct\":500,\"alert\":\"select\",\"effect\":\"none\",\"colormode\":\"hs\",\"reachable\":true}, \"type\": \"Extended color light\", \"name\": \"Hue Lamp 3\", \"modelid\": \"LCT001\", \"swversion\": \"66009663\", \"pointsymbol\": { \"1\":\"none\", \"2\":\"none\", \"3\":\"none\", \"4\":\"none\", \"5\":\"none\", \"6\":\"none\", \"7\":\"none\", \"8\":\"none\" }},\"4\":{\"state\": {\"on\":true,\"bri\":254,\"hue\":46920,\"sat\":254,\"xy\":[0.1382,0.0824],\"alert\":\"select\",\"effect\":\"none\",\"colormode\":\"hs\",\"reachable\":true}, \"type\": \"Color light\", \"name\": \"LivingColors 1\", \"modelid\": \"LLC012\", \"swversion\": \"66009461\", \"pointsymbol\": { \"1\":\"none\", \"2\":\"none\", \"3\":\"none\", \"4\":\"none\", \"5\":\"none\", \"6\":\"none\", \"7\":\"none\", \"8\":\"none\" }},\"5\":{\"state\": {\"on\":true,\"bri\":254,\"hue\":0,\"sat\":0,\"xy\":[0.4350,0.4050],\"alert\":\"select\",\"effect\":\"none\",\"colormode\":\"hs\",\"reachable\":true}, \"type\": \"Color light\", \"name\": \"LivingColors 2\", \"modelid\": \"LLC012\", \"swversion\": \"66009461\", \"pointsymbol\": { \"1\":\"none\", \"2\":\"none\", \"3\":\"none\", \"4\":\"none\", \"5\":\"none\", \"6\":\"none\", \"7\":\"none\", \"8\":\"none\" }}}";
        try {
            JSONObject lampData = new JSONObject(apiResponse);
            return lampData;
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    protected static String get(String action) {
        HttpClient client = new DefaultHttpClient();
        try {
            HttpGet get = new HttpGet(API_BASE_URL + action);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            return client.execute(get, responseHandler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String put(String action, String data) {
        try {
            URL url = new URL(API_BASE_URL + action);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(
                    httpCon.getOutputStream());
            out.write(data);
            out.close();

            int responseCode = httpCon.getResponseCode();
            String responseMessage = httpCon.getResponseMessage();
            return responseMessage;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (java.net.ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}