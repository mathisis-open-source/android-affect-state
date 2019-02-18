package com.otea.affectstate;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by manos on 18/2/19.
 */

public class SenderTask extends AsyncTask{
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public String json_to_post;
    OkHttpClient client = new OkHttpClient();
    public SenderTask(String json_to_post){
        this.json_to_post = json_to_post;
    }
    String post(String url) throws IOException {
        RequestBody body = RequestBody.create(JSON, json_to_post);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("X-User-Path", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiJBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUEiLCJvdGhlcl9hdXRoX3JvbGUiOiJBZG1pbmlzdHJhdG9yIiwiYXV0aF9kYXRhIjoiZGVtb0BkZW1vLm5ldCIsImp0aSI6IjBjNjUyZWVhLWVhOGYtNDY5NC1iZTU0LWM4Y2QxODk0ZDdhMyIsImlhdCI6MTQ4OTA2NDQ5MywiZXhwIjoxNDg5MDY4MDkzfQ.l0mI8-YO5NV0XJrrOC2QMVeTlbV_PwE6Os7zL-zTcdQ")
                .header("X-User-Token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiJBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUEiLCJvdGhlcl9hdXRoX3JvbGUiOiJBZG1pbmlzdHJhdG9yIiwiYXV0aF9kYXRhIjoiZGVtb0BkZW1vLm5ldCIsImp0aSI6IjBjNjUyZWVhLWVhOGYtNDY5NC1iZTU0LWM4Y2QxODk0ZDdhMyIsImlhdCI6MTQ4OTA2NDQ5MywiZXhwIjoxNDg5MDY4MDkzfQ.l0mI8-YO5NV0XJrrOC2QMVeTlbV_PwE6Os7zL-zTcdQ")
                .header("Content-Type","application/json")
                .header("Accept","application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
        catch (Exception e){
            System.out.println(e.toString());
            return null;
        }
    }



    @Override
    protected Object doInBackground(Object... arg0) {
        connect();
        return null;
    }
    private void connect() {
        try {
            System.out.println("Sender Task Connect....");
            String response = this.post(Settings.getInstance().server_url);
            System.out.println(response);
        } catch (Exception e) {
            Log.d("HTTPCLIENT", e.getLocalizedMessage());
        }
    }

}
