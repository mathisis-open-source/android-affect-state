package com.otea.affectstate;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import android.provider.Settings.Secure;
import android.widget.EditText;


/**
 * Created by manos on 18/2/19.
 */

public class Settings {
    private String android_id = "";
    private Context thiscontext;
    private String gyroscope_sensorial_data = "", touch_sensorial_data = "", accelerometer_sensorial_data = "";
    private Integer count_sensorial_length = 0;
    public String server_url = "https://stage.mathisis-project.eu/api/airlib/api/mobileStandalone/";
    public String last_affect,last_affect_time;

    public Context getContext() {
        return thiscontext;
    }

    public void setContext(Context co) {

        this.thiscontext = co;
        this.android_id = Secure.getString(thiscontext.getContentResolver(), Secure.ANDROID_ID);
    }

    public String getDeviceID() {
        return this.android_id;
    }

    private static final Settings holder = new Settings();

    public static Settings getInstance() {
        return holder;
    }

    public String getMatchedDate() {
//        Long curr = System.currentTimeMillis();
//        Long timestamp_diff = curr;
//        Date date = new Date(timestamp_diff);
//        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        String dateFormatted = formatter.format(date);
//        return dateFormatted;

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        return nowAsISO;

    }

    public void setGyroscopeSensorialData(String sensorialData) throws Exception {


        if (this.gyroscope_sensorial_data == "" && sensorialData != null)
            this.gyroscope_sensorial_data = sensorialData;
        else
            this.gyroscope_sensorial_data += "," + sensorialData;

        handleSensorialData();
    }

    public void setAccelerometerSensorialData(String sensorialData) throws Exception {

        if (this.accelerometer_sensorial_data == "")
            this.accelerometer_sensorial_data = sensorialData;
        else
            this.accelerometer_sensorial_data += "," + sensorialData;
        handleSensorialData();
    }

    public void set2DTouchData(String sensorialData) throws Exception {
        Log.i("Custom Log", sensorialData);
        if (this.touch_sensorial_data == "")
            this.touch_sensorial_data = sensorialData;
        else
            this.touch_sensorial_data += "," + sensorialData;

        handleSensorialData();
    }

    public void handleSensorialData() throws Exception {

        count_sensorial_length++;
        if (count_sensorial_length > 10) {
            sendSensorialData();
            getCurrentStatus();
            count_sensorial_length = 0;
        }
    }

    public void sendSensorialData() throws Exception {
        String json = "{\n" +
                "\"device_id\": \"" + android_id + "\",\n" +
                "\"timestamp\": \"" + getMatchedDate() + "\",\n" +
                "\"features\": [[" + gyroscope_sensorial_data + "]," +
                "[" + accelerometer_sensorial_data + "]\n" +
                "]\n" +
                "}";
        gyroscope_sensorial_data = "";
        accelerometer_sensorial_data = "";
        SenderTask poster = new SenderTask(json);
        poster.execute();

    }

    public void updateCurrentAffect(String affect, String matchedDate) {
        this.last_affect = affect;
        this.last_affect_time = matchedDate;

    }
    public String getLastAffect(){
        return this.last_affect;
    }
    public String getLastAffectTime(){
        return this.last_affect_time;
    }
    public void getCurrentStatus() throws Exception {
        String json = "{}";
        GetterTask getter = new GetterTask(json);
        getter.execute();
    }


}
