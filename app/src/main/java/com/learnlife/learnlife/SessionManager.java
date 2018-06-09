package com.learnlife.learnlife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.learnlife.learnlife.login.view.LoginActivity;

public class SessionManager {

    private static SessionManager INSTANCE = null;

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AndroidHivePref";

    private static final String IS_LOGIN = "IsLoggedIn";

    private static final String KEY_ID = "lastname";
    private static final String KEY_EMAIL = "lastname";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";

    public static String USER_ID;
    public static String USER_EMAIL;
    public static String USER_FIRSTNAME;
    public static String USER_LASTNAME;

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

    public void createLoginSession(String id, String email, String firstname, String lastname) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_ID, id);
        USER_ID = id;
        editor.putString(KEY_EMAIL, email);
        USER_EMAIL = email;
        editor.putString(KEY_FIRSTNAME, firstname);
        USER_FIRSTNAME = firstname;
        editor.putString(KEY_LASTNAME, lastname);
        USER_LASTNAME = lastname;
        editor.commit();
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(i);
        }

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