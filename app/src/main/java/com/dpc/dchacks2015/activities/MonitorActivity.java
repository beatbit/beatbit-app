package com.dpc.dchacks2015.activities;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Visibility;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dpc.dchacks2015.R;
import com.dpc.dchacks2015.fragments.CardiacArrestDetectedDialog;
import com.dpc.dchacks2015.monitor.CardiacArrestHandler;
import com.dpc.dchacks2015.monitor.HeartBeatClient;
import com.dpc.dchacks2015.monitor.HeartBeatListener;
import com.dpc.dchacks2015.monitor.HeartRate;
import com.dpc.dchacks2015.monitor.MonitorService;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class MonitorActivity extends ActionBarActivity implements HeartBeatListener {
    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private RelativeLayout bpmLayout;
    private TextView bpmTxtv;
    private Animation fadeOut;
    private Animation fadeIn;
    private List<HeartRate> heartRates;
    private MonitorService service;
    private BroadcastReceiver broadcastReceiver;
    private HeartBeatClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        bpmLayout = (RelativeLayout) findViewById(R.id.rl_bpmLayout);
        bpmTxtv = (TextView) findViewById(R.id.txtv_hr);

        final Button monitoring = ((Button) findViewById(R.id.btn_monitoring));
        monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startMonitoring = getString(R.string.startMonitoring);
                String stopMonitoring = getString(R.string.stopMonitoring);

                if(monitoring.getText().equals(startMonitoring)) {
                    monitoring.setText(stopMonitoring);
                    startMonitor();
                }
                else {
                    monitoring.setText(startMonitoring);
                    stopMonitor();
                }
            }
        });

        graph = (GraphView) findViewById(R.id.graph);

        DataPoint[] data = {
                new DataPoint(0, 40),
                new DataPoint(1, 41),
                new DataPoint(3, 41),
                new DataPoint(10, 42),
                new DataPoint(50, 55),
                new DataPoint(75, 65),
                new DataPoint(90, 60),
                new DataPoint(100, 57)
        };

        heartRates = new LinkedList<HeartRate>();

        int white = getResources().getColor(android.R.color.white);

        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Heart Rate");

        series = new LineGraphSeries<DataPoint>(data);

        series.setColor(getResources().getColor(R.color.colorPrimary));

        GridLabelRenderer renderer = graph.getGridLabelRenderer();

        renderer.setHorizontalLabelsColor(white);
        renderer.setVerticalLabelsColor(white);
        renderer.setVerticalAxisTitleColor(white);
        renderer.setVerticalLabelsColor(white);
        renderer.setHorizontalAxisTitleColor(white);
        renderer.setGridColor(white);
        renderer.setPadding(15);


        graph.addSeries(series);

        onHeartBeat(new HeartRate(88, 200));

        for(DataPoint point : data) {
            heartRates.add(new HeartRate((int)point.getY(), (long)point.getX()));
        }

        try {
            client = new HeartBeatClient(new HeartBeatListener() {
                @Override
                public void onHeartBeat(HeartRate heartRate) {
                    Log.d("monitor", heartRate.getValue() + ": heartrate");
                }
            });

            client.connect();

        } catch(Exception e) {
            Log.e("monitor", Log.getStackTraceString(e));
        }

    }

    private void stopMonitor() {

    }

    private void startMonitor() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.dpc.dchacks2015.monitor.MonitorService");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                new CardiacArrestDetectedDialog(MonitorActivity.this);
            }
        };

        registerReceiver(broadcastReceiver, intentFilter);

        new Runnable() {
            @Override
            public void run() {
                startService(new Intent(MonitorActivity.this, MonitorService.class));
            }
        }.run();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.disconnect();
        unregisterReceiver(broadcastReceiver);
        stopService(new Intent(this, MonitorService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_monitor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onHeartBeat(HeartRate heartRate) {
        heartRates.add(heartRate);

        // Play the animation
        heartBeatAnimation(heartRate);
    }

    private void heartBeatAnimation(final HeartRate heartRate) {
        // Create a fadeout animation
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(300);

        bpmLayout.setAnimation(fadeOut);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                bpmTxtv.setText(heartRate.getValue() + "");

                series.appendData(new DataPoint(heartRate.getTime(), heartRate.getValue()), true, 40);

                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setDuration(300);
                bpmLayout.setAnimation(fadeIn);

                fadeIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        onHeartBeat(new HeartRate(heartRate.getValue() + 1, heartRate.getTime() + 1));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                fadeIn.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fadeOut.start();
    }
}
