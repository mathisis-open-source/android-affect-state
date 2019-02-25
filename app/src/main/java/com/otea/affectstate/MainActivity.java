package com.otea.affectstate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    TextView txtView4,txtView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Settings.getInstance().setContext(getApplicationContext());
        txtView4 = (TextView)findViewById(R.id.textView4);
        txtView2 = (TextView)findViewById(R.id.textView2);

        Intent sensorial = new Intent(this,SensorialComponent.class);
        this.startService(sensorial);



        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                changeStrings(Settings.getInstance().getLastAffect(),Settings.getInstance().getLastAffectTime());



            }
        }, 0, 5000);//put here time 1000 milliseconds=1 second

    }
    public void changeStrings(String a,String b){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                txtView4.setText(b);
                txtView2.setText(a);

            }
        });

    }


}
