package com.dpc.dchacks2015.monitor;

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import org.json.*;

/**
 * Created by Daniel on 8/1/2015.
 */
public class HeartBeatClient {
    private static final String TAG = "monitor";
    private Socket io;

    public HeartBeatClient(final HeartBeatListener listener) throws Exception {
        io = IO.socket(Config.APIEndpoints.BASE_ENDPOINT);

        io.connect();

        io.emit("msga", "msga");
//        io.on(Config.IOEvents.EVENT_HEART_BEAT, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                try {
//                    JSONObject obj = new JSONObject((String) args[0]);
//                    listener.onHeartBeat(new HeartRate(obj.getInt(Config.JSONConfig.heart_rate_key), obj.getLong(Config.JSONConfig.TIME_KEY)));
//                } catch(JSONException e) {
//                    Log.e(TAG, Log.getStackTraceString(e));
//                }
//            }
//        });
    }

    public void connect() {

    }

    public void disconnect() {
        io.disconnect();
        io.off();
    }
}
