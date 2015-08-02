package com.dpc.dchacks2015.monitor;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.util.Log;

import org.json.*;

import java.util.prefs.Preferences;

/**
 * Created by Daniel on 8/1/2015.
 */
public class JSONHandler {
    private static final String TAG = "monitor";
    private static final String PREFERENCES_NAME = "prefs";
    
    public static PatientInfo readPatientInfo(Context context) {
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        String name = preferences.getString(Config.JSONConfig.NAME_KEY, "");
        String history = preferences.getString(Config.JSONConfig.HISTORY_KEY, "");
        int age = preferences.getInt(Config.JSONConfig.AGE_KEY, 0);

        return new PatientInfo(name, history, age);
    }

    public static void savePatientInfo(Context context, PatientInfo info) {
        SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(Config.JSONConfig.NAME_KEY, info.getName());
        editor.putString(Config.JSONConfig.HISTORY_KEY, info.getHistory());
        editor.putInt(Config.JSONConfig.AGE_KEY, info.getAge());

        editor.apply();
    }
}
