package com.learnlife.learnlife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.login.view.LoginActivity;

public class SessionManager {

    private static SessionManager INSTANCE = null;

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LearnlifePref";

    private static final String IS_LOGIN = "IsLoggedIn";

    private static final String KEY_TOKEN = "token";
    private static final String KEY_ID = "_id";
    private static final String KEY_EMAIL = "lastname";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";

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

    public void createLoginSession(String token, String id, String email, String firstname, String lastname) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_ID, id);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_FIRSTNAME, firstname);
        editor.putString(KEY_LASTNAME, lastname);
        this.user = new User(token, id, email, firstname, lastname);
        editor.commit();
    }

    public void checkLogin() {
        this.user = new User(
            pref.getString(KEY_TOKEN, null),
            pref.getString(KEY_ID, null),
            pref.getString(KEY_EMAIL, null),
            pref.getString(KEY_FIRSTNAME, null),
            pref.getString(KEY_LASTNAME, null)
        );

        if (this.user.getToken() == null) {
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
        this.createLoginSession(
                user.getToken(),
                user.getId(),
                user.getEmail(),
                user.getFirstname(),
                user.getLastname());
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