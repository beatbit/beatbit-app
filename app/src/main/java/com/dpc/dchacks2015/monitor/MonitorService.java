package com.dpc.dchacks2015.monitor;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import com.dpc.dchacks2015.fragments.CardiacArrestDetectedDialog;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Daniel on 8/1/2015.
 */
public class MonitorService extends Service implements HeartBeatListener {
    private static final String TAG = "monitor";
    private HeartBeatClient heartBeatClient;
    private CardiacArrestDetector cardiacArrestDetector;
    private List<HeartRate> heartRates;
    private int hr = 15;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        heartRates = new LinkedList<HeartRate>();
        cardiacArrestDetector = new CardiacArrestDetector();

        try {
         //   heartBeatClient = new HeartBeatClient(this);
        } catch(Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        //heartBeatClient.connect();

        new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                onHeartBeat(new HeartRate(--hr, hr + 1));
            }

            @Override
            public void onFinish() {

            }
        }.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //heartBeatClient.disconnect();
    }

    @Override
    public void onHeartBeat(HeartRate heartRate) {
        heartRates.add(heartRate);

        Log.d(TAG, hr+"");

        if(cardiacArrestDetector.process(heartRate)) {
            new CardiacArrestHandler(this, heartRates);
            // Notify the monitor activity
            Intent intent = new Intent();
            intent.setAction("com.dpc.dchacks2015.monitor.MonitorService");
            sendBroadcast(intent);
        }

        // TODO: Pop off the back, data is too old to matter
    }
}
