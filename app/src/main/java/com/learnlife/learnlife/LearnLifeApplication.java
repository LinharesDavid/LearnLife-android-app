package com.learnlife.learnlife;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;

import com.androidnetworking.AndroidNetworking;

public class LearnLifeApplication extends Application {

    public static final String BASE_URL = "http://192.168.100.83:8080";

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidNetworking.initialize(getApplicationContext());
    }
}
