package com.dpc.dchacks2015.monitor;

/**
 * Created by Daniel on 8/1/2015.
 */
public class HeartRate {
    private int value;
    private long time;

    public HeartRate(int value, long time) {
        this.value = value;
        this.time = time;
    }

    public int getValue() {
        return value;
    }

    public long getTime() {
        return time;
    }
}
