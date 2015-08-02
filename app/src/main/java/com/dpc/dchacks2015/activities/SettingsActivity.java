package com.dpc.dchacks2015.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dpc.dchacks2015.R;
import com.dpc.dchacks2015.fragments.CardiacArrestDetectedDialog;
import com.dpc.dchacks2015.monitor.JSONHandler;
import com.dpc.dchacks2015.monitor.PatientInfo;


public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Load in previously saved data
        PatientInfo savedPatientInfo = JSONHandler.readPatientInfo(this);

        final EditText name = (EditText) findViewById(R.id.edt_yourName);
        final EditText age = (EditText) findViewById(R.id.edt_yourAge);
        final EditText heartCondition = (EditText) findViewById(R.id.edt_condition);
        final EditText doctorName = (EditText) findViewById(R.id.edt_doctorName);
        final EditText doctorNumber = (EditText) findViewById(R.id.edt_doctorPhone);

        // Make the condition edit text scroll
        heartCondition.setMovementMethod(new ScrollingMovementMethod());

        // Populate the fields with any saved data
        name.setText(savedPatientInfo.getName());
        age.setText(savedPatientInfo.getAge() != 0 ? savedPatientInfo.getAge() + "" : "");
        heartCondition.setText(savedPatientInfo.getHistory());
        doctorName.setText(savedPatientInfo.getDoctorName());
        doctorNumber.setText(savedPatientInfo.getDoctorPhoneNumber());

        ((Button) findViewById(R.id.btn_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                int a = Integer.parseInt(age.getText().toString());
                String h = heartCondition.getText().toString();
                String dn = doctorName.getText().toString();
                String dp = doctorNumber.getText().toString();

                // Save the patient info
                JSONHandler.savePatientInfo(SettingsActivity.this, new PatientInfo(n, h, a, dp, dn));
            }
        });

        //new CardiacArrestDetectedDialog(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
