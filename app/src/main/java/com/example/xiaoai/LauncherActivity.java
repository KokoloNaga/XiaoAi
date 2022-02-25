package com.example.xiaoai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;

import java.security.Permission;

public class LauncherActivity extends AppCompatActivity {
    private  final  long waitTime = 1000;
    public static final String FIRST_LAUNCHER = "first launcher";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        start();
    }

    public void start(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(isFirstLauncher()) {
                    if(isFirstLogin()) {
                        intent = new Intent(LauncherActivity.this, LoginActivity.class);
                    } else {
                        intent = new Intent(LauncherActivity.this, MainActivity.class);
                    }
                }else {
                    intent = new Intent(LauncherActivity.this, FirstLauncherActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },waitTime);
    }

    public boolean isFirstLauncher(){
        SharedPreferences sp = getSharedPreferences("ansen", Context.MODE_PRIVATE);
        return sp.getBoolean(FIRST_LAUNCHER,false);
    }

    public boolean isFirstLogin(){
        SharedPreferences sp = getSharedPreferences("now", Context.MODE_PRIVATE);

        String saveId = sp.getString("id","");

        return saveId.equals("");
    }



}