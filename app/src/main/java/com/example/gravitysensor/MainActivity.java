package com.example.gravitysensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView xtextView,yTextView,zTextView;
    private SensorManager sensorManager;
    private Sensor mGravity;
    private boolean isGravitySensorPresent;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        xtextView = findViewById(R.id.xTextView);
        yTextView  = findViewById(R.id.yTextView);
        zTextView= findViewById(R.id.zTextView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)!= null){
            mGravity=sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
            isGravitySensorPresent = true;
        }
        else {
            xtextView.setText("This sensor is not Available");
            isGravitySensorPresent=false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        xtextView.setText(event.values[0]+"m/s2");
        yTextView.setText(event.values[1]+"m/s2");
        zTextView.setText(event.values[2]+"m/s2");
        if (event.values[2]<-9){
            getWindow().getDecorView().setBackgroundColor(Color.GREEN);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

        }else {
            getWindow().getDecorView().setBackgroundColor(Color.WHITE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)!= null)
            sensorManager.registerListener(this,mGravity,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)!= null)
            sensorManager.unregisterListener(this,mGravity);
    }
}