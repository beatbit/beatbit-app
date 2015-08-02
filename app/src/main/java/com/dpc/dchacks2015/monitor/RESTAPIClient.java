package com.dpc.dchacks2015.monitor;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Daniel on 8/1/2015.
 */
public class RESTAPIClient {
    private static final String TAG = "monitor";

    public void connectToAPI() {
        HttpURLConnection connection = establishConnection(Config.APIEndpoints.BASE_ENDPOINT);

        connection.disconnect();
    }

    private HttpURLConnection establishConnection(String url) {
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
        } catch(IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        return connection;
    }
}
