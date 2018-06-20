package com.learnlife.learnlife.login.view;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.LearnLifeApplication;
import com.learnlife.learnlife.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView mainView;

    private final String TAG = getClass().getSimpleName();



    public LoginPresenter(ILoginView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void loginUser(String email, String password) {
        JSONObject user = new JSONObject();
        try {
            user.put(Constants.REQUEST_KEY_LOGIN_EMAIL, email);
            user.put(Constants.REQUEST_KEY_LOGIN_PASSWORD, password);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        AndroidNetworking.post(Constants.BASE_URL + Constants.EXTENDED_URL_LOGIN)
                .addJSONObjectBody(user)
                .setTag("login")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Login succeeded");
                        try {

                            JSONObject user = response.getJSONObject(Constants.RESPONSE_KEY_LOGIN_USER);
                            SessionManager.getInstance().createLoginSession(
                                    response.getString(Constants.RESPONSE_KEY_USER_TOKEN),
                                    user.getString(Constants.RESPONSE_KEY_USER_ID),
                                    user.getString(Constants.RESPONSE_KEY_USER_EMAIL),
                                    user.getString(Constants.RESPONSE_KEY_USER_FIRSTNAME),
                                    user.getString(Constants.RESPONSE_KEY_USER_LASTNAME)
                            );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mainView.loginSucceed();
                    }

                    @Override
                    public void onError(ANError anError) {
                        String errorBody = anError.getErrorBody() != null ? anError.getErrorBody() : "error without content";
                        Log.d(TAG, "Login failed : " + anError.toString());
                        mainView.loginFailed(errorBody);
                    }
                });

    }

    @Override
    public void registerUser(String firstname, String lastname, String email, String password) {

        JSONObject user = new JSONObject();
        try {
            user.put(Constants.REQUEST_KEY_REGISTER_EMAIL, email);
            user.put(Constants.REQUEST_KEY_REGISTER_PASSWORD, password);
            user.put(Constants.REQUEST_KEY_REGISTER_FIRSTNAME, firstname);
            user.put(Constants.REQUEST_KEY_REGISTER_LASTNAME, lastname);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        AndroidNetworking.post(Constants.BASE_URL + Constants.EXTENDED_URL_REGISTER)
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
                        Log.d(TAG, "Register failed : " + anError.getErrorDetail() + "\n" + anError.getErrorBody());
                        mainView.loginFailed(errorBody);
                    }
                });
    }
}
