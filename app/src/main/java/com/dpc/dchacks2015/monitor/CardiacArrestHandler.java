package com.dpc.dchacks2015.monitor;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.dpc.dchacks2015.R;

import java.util.ArrayList;
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
        sendPush(context);
    }

    private void sendPush(Context context) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.heart)
                .setContentTitle("Cardiac Arrest Detected!")
                .setContentText("Let me know your ok!");

        int mNotificationId = 001;

        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
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
                "Recent heart rate data: %s\n" +
                "Heart Doctor Name: %s\n" +
                "Heart Doctor Phone Number: %s\n";

        String location = "lat: " + " not implemented" + " long: " + " not implemented";
        String name = patientInfo.getName();
        String age = patientInfo.getAge() + "";
        String conditions = patientInfo.getHistory();
        String hrData = "";
        String doctorName = patientInfo.getDoctorName();
        String doctorNumber = patientInfo.getDoctorPhoneNumber();

        int size = heartRateList.size();

        // Compile the heart rate data
        // Grab the top few heart rates, those are what set it off
        hrData += "bpm: " + heartRateList.get(0).getValue() + "  time: " + heartRateList.get(0).getTime();

        // Fill in the template data
        template = String.format(template, location, name, age, conditions, hrData, doctorName, doctorNumber);

        Log.d("monitor", template);

        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> parts = sms.divideMessage(template);
        sms.sendMultipartTextMessage(NUMBER_911, null, parts, null, null);
    }

}
