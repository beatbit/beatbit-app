package com.dpc.dchacks2015.monitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 8/1/2015.
 */
public class CardiacArrestDetector {
    private List<HeartRate> processed;

    public boolean process(HeartRate heartRate) {
        if(processed == null) {
            processed = new ArrayList<HeartRate>();
        }

        // This one has been processed, save it off for long term storage
        processed.add(heartRate);

        // TODO: Actually implement a real solution
        return heartRate.getValue() < 10;
    }

}
