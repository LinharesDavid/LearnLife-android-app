package com.learnlife.learnlife;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class LearnLifeApplication extends Application {

    public static final String BASE_URL = "http://192.168.1.29:8080";

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidNetworking.initialize(getApplicationContext());
    }
}
