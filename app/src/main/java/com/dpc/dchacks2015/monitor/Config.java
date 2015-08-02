package com.dpc.dchacks2015.monitor;

/**
 * Created by Daniel on 8/1/2015.
 */
public class Config {

    public class JSONConfig {
        public static final String HEART_RATE_KEY = "heartrate";
        public static final String TIME_KEY = "time";
        public static final String DOCTOR_NAME_KEY = "doctorName";
        public static final String DOCTOR_PHONE_NUMBER_KEY = "doctorphonenumber";
        public static final String HISTORY_KEY = "history";
        public static final String NAME_KEY = "name";
        public static final String AGE_KEY = "age";
    }

    public class IOEvents {
        public static final String EVENT_HEART_BEAT = "heartbeat";
    }

    public class APIEndpoints {
        public static final String BASE_ENDPOINT = "http://192.168.56.1:3003/";
    }
}
