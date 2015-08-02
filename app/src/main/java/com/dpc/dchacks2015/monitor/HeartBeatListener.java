package com.dpc.dchacks2015.monitor;

import com.dpc.dchacks2015.monitor.HeartRate;

/**
 * Created by Daniel on 8/1/2015.
 */
public interface HeartBeatListener {
    void onHeartBeat(HeartRate heartRate);
}
