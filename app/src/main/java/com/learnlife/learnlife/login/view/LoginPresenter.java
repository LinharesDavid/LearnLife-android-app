package com.learnlife.learnlife.login.view;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.learnlife.learnlife.LearnLifeApplication;
import com.learnlife.learnlife.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView mainView;

    private final String TAG = getClass().getSimpleName();

    private static final String RESPONSE_KEY_TOKEN = "token";
    private static final String RESPONSE_KEY_USER_ID = "_id";
    private static final String RESPONSE_KEY_USER_EMAIL = "email";
    private static final String RESPONSE_KEY_USER_FIRSTNAME = "firstname";
    private static final String RESPONSE_KEY_USER_LASTNAME = "lastname";

    public LoginPresenter(ILoginView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void loginUser(String email, String password) {
        JSONObject user = new JSONObject();
        try {
            user.put("email", email);
            user.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        AndroidNetworking.post(LearnLifeApplication.BASE_URL + "/auth/login")
                .addJSONObjectBody(user)
                .setTag("login")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Login succeeded");
                        try {

                            JSONObject user = response.getJSONObject("user");
                            SessionManager.getInstance().createLoginSession(
                                    response.getString(RESPONSE_KEY_TOKEN),
                                    user.getString(RESPONSE_KEY_USER_ID),
                                    user.getString(RESPONSE_KEY_USER_EMAIL),
                                    user.getString(RESPONSE_KEY_USER_FIRSTNAME),
                                    user.getString(RESPONSE_KEY_USER_LASTNAME)
                            );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mainView.loginSucceed();
                    }

                    @Override
                    public void onError(ANError anError) {
                        String errorBody = anError.getErrorBody() != null ? anError.getErrorBody() : "error without content";
                        Log.d(TAG, "Login failed : " + errorBody);
                        mainView.loginFailed(errorBody);
                    }
                });

    }

    @Override
    public void registerUser(String firstname, String lastname, String email, String password) {

        JSONObject user = new JSONObject();
        try {
            user.put("email", email);
            user.put("password", password);
            user.put("firstname", firstname);
            user.put("lastname", lastname);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        AndroidNetworking.post(LearnLifeApplication.BASE_URL + "/users")
                .addJSONObjectBody(user)
                .setTag("register")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mainView.loginSucceed();
                    }

                    @Override
                    public void onError(ANError anError) {
                        String errorBody = anError.getErrorBody() != null ? anError.getErrorBody() : "error without content";
                        Log.d(TAG, "Register failed : " + errorBody);
                        mainView.loginFailed(errorBody);
                    }
                });
    }
}
