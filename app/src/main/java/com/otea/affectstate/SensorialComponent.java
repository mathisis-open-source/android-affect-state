package com.otea.affectstate;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class SensorialComponent extends Service {
    private boolean isRunning;
    private SensorManager mSensorManager;
    private SensorEventListener mSensorListener;
    int count_acc=0;
    int count_gyr=0;
    String accelerometer_string="";
    String gyroscope_string = "";

    public SensorialComponent() {

        mSensorManager = (SensorManager) Settings.getInstance().getContext().getSystemService(Context.SENSOR_SERVICE);
        isRunning=true;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                mSensorListener = new SensorEventListener() {
                    @Override
                    public void onAccuracyChanged(Sensor arg0, int arg1) {
                    }

                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        Sensor sensor = event.sensor;
                        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                            if(isRunning==true) {

                                count_acc++;
                                accelerometer_string = "{\"time\":\""+Settings.getInstance().getMatchedDate()+"\",\"x\":"+event.values[0]+",\"y\":" + event.values[1] + ",\"z\":" + event.values[2] + "}";
                                try {
                                    postAccelerometerThing(accelerometer_string);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                            if(isRunning==true) {

                                count_gyr++;
                                gyroscope_string = "{\"time\":\""+Settings.getInstance().getMatchedDate()+"\",\"x\":"+event.values[0]+",\"y\":"+event.values[1]+",\"z\":"+event.values[2]+"}";
                                try {
                                    postGyroscopeThing(gyroscope_string);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    }
                };
                mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
                mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);

            }
        }).start();

        return Service.START_STICKY;
    }


    public void postGyroscopeThing(final String thestring) throws Exception {
        if(isRunning)
            Settings.getInstance().setGyroscopeSensorialData(thestring);
    }
    public void postAccelerometerThing(final String thestring) throws Exception {
        if(isRunning)
            Settings.getInstance().setAccelerometerSensorialData(thestring);
    }
    public void postTouchThing(final String thestring) throws Exception {
        if(isRunning)
            Settings.getInstance().set2DTouchData(thestring);
    }
    @Override
    public void onDestroy() {
        isRunning=false;
        mSensorManager.unregisterListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        mSensorManager.unregisterListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE));
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
