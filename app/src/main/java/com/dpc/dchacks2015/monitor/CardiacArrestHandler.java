package com.dpc.dchacks2015.monitor;

import android.content.Context;
import android.location.Location;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Daniel on 8/1/2015.
 */
public class CardiacArrestHandler {
    private static final String NUMBER_911 = "7033623714";

    public CardiacArrestHandler(Context context, List<HeartRate> heartRates) {
        // Read the current patient info
        PatientInfo patientInfo = JSONHandler.readPatientInfo(context);
        Location location = getCurrentLocation();

        sendDispatchMessage(context, location,  patientInfo, heartRates);
    }

    private Location getCurrentLocation() {
        return null;
    }

    private void sendDispatchMessage(Context context, Location loc, PatientInfo patientInfo, List<HeartRate> heartRateList) {
        String template = "This is an auto generated dispatch message from a heart patient using the app BeatBit who may have just undergone a sudden cardiac arrest." + "\n" +
                "Here is some information on the patient. \n \n" +
                "Location: %s\n\n" +
                "Name: %s\n\n" +
                "Age: %s\n\n" +
                "Heart conditions: %s\n" +
                "Recent heart rate data: %s\n";

        String location = "lat: " + " not implemented" + " long: " + " not implemented";
        String name = patientInfo.getName();
        String age = patientInfo.getAge() + "";
        String conditions = patientInfo.getHistory();
        String hrData = "";

        int size = heartRateList.size();

        // Compile the heart rate data
        // Grab the top few heart rates, those are what set it off
        hrData += "bpm: " + heartRateList.get(0).getValue() + "  time: " + heartRateList.get(0).getTime();

        // Fill in the template data
        template = String.format(template, location, name, age, conditions, hrData);

        Log.d("monitor", template);


        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(NUMBER_911, null, template, null, null);
    }

}
