package com.otea.affectstate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Settings.getInstance().setContext(getApplicationContext());

        Intent sensorial = new Intent(this,SensorialComponent.class);
        this.startService(sensorial);
    }


}
