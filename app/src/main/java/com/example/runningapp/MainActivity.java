package com.example.runningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView tvHeading, tvSteps, tvTime;
    Button btnStart, btnStop, btnReset, btnShowRun;
    private final double HI_Step = 13.0, LO_Step = 10.0;
    boolean highLimit = false, started = false;
    int stepCounter = 0;
    String usersTimeS;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    CountUpTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHeading = findViewById(R.id.tvHeading);
        tvSteps = findViewById(R.id.tvSteps);
        tvTime = findViewById(R.id.tvTime);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnReset = findViewById(R.id.btnReset);
        btnShowRun = findViewById(R.id.btnShowRun);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        timer = new CountUpTimer(300000000) {
            public void onTick(int second) {
                tvTime.setText(String.valueOf(second));
            }
            public void onFinish() {

            }
        };
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //mSensorManager.unregisterListener(this);
        float xCoordinate = event.values[0];
        float yCoordinate = event.values[1];
        float zCoordinate = event.values[2];

        double magnitude = round(Math.sqrt((xCoordinate*xCoordinate) + (yCoordinate*yCoordinate) + (zCoordinate*zCoordinate)), 2);

        if(started = true) {
            if ((magnitude > HI_Step) && (highLimit == false)) {
                highLimit = true;
            }
            if ((magnitude < LO_Step) && (highLimit == true)) {
                stepCounter++;
                tvSteps.setText(String.valueOf(stepCounter));
                highLimit = false;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public static double round(double value, int places) {
        if(places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    /*
     * When the app is brought to the foreground - using app on screen
     */
//    protected void onResume() {
//        super.onResume();
//        // turn on the sensor
//        mSensorManager.registerListener(this, mSensor,
//                SensorManager.SENSOR_DELAY_NORMAL);
//    }

    /*
     * App running but not on screen - in the background
     */
//    protected void onPause() {
//        super.onPause();
//        mSensorManager.unregisterListener(this);    // turn off listener to save power
//    }

    public void doStart(View view) {
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        timer.start();
        started = true;
    }

    public void doStop(View view) throws InterruptedException {
        timer.cancel();
        mSensorManager.unregisterListener(this);
    }

    public void doReset(View view) {
        timer.cancel();
        tvTime.setText("0");
        tvSteps.setText("0");
        stepCounter = 0;
        mSensorManager.unregisterListener(this);

    }

    public void switchRun(View view) {
        Intent SummaryPage = new Intent(view.getContext(), SummaryPage.class);
        usersTimeS = tvTime.getText().toString();
        SummaryPage.putExtra("usersSteps", stepCounter);
        SummaryPage.putExtra("usersTime", usersTimeS);
        startActivity(SummaryPage);
    }
}