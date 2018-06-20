package com.learnlife.learnlife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learnlife.learnlife.crosslayers.models.Badge;
import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.login.view.LoginActivity;
import com.learnlife.learnlife.tags.modele.Tag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    private static SessionManager INSTANCE = null;

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LearnlifePref";

    private static final String IS_LOGIN = "IsLoggedIn";

    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER = "user";

    private User user = null;

    private SessionManager() {
        this.context = LearnLifeApplication.getInstance();
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    /** Point d'accès pour l'instance unique du singleton */
    public static SessionManager getInstance()
    {
        if (INSTANCE == null)
        {   INSTANCE = new SessionManager();
        }
        return INSTANCE;
    }

    public void createLoginSession(User user, String token) {
        editor.putBoolean(IS_LOGIN, true);
        user.setToken(token);
        editor.putString(KEY_USER, new Gson().toJson(user));
        this.user = user;
        editor.commit();
    }

    public void checkLogin() {

        this.user = new Gson().fromJson(pref.getString(KEY_USER, null), User.class);

        if (this.user == null) {
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }


    }

    public User getUser() {
        return user;
    }

    public void updateUser(User user) {
        this.createLoginSession(user, user.getToken());
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, null);
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}