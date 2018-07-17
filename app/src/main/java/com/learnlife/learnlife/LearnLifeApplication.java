package com.learnlife.learnlife;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;

import com.androidnetworking.AndroidNetworking;

public class LearnLifeApplication extends Application {


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
