package com.learnlife.learnlife;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class LearnLifeApplication extends Application {

    public static final String BASE_URL = "http://192.168.1.29:8080";

    private static LearnLifeApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AndroidNetworking.initialize(getApplicationContext());
    }

    public static LearnLifeApplication getInstance() {
        return instance;
    }
}
