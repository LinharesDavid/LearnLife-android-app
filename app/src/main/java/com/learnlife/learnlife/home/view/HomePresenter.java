package com.learnlife.learnlife.home.view;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.crosslayers.models.UserChallenge;

import java.util.List;

public class HomePresenter implements IHomePresenter{
    private IHomeView homeView;
    private final String TAG = getClass().getSimpleName();

    public HomePresenter(IHomeView homeView){
        this.homeView = homeView;
    }

    @Override
    public void displayUserChallenge() {
        String idUser = SessionManager.getInstance().getUser().getId();
        AndroidNetworking.get(Constants.BASE_URL+Constants.EXTENDED_URL_USERCHALLENGES+idUser)
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .addHeaders(Constants.HEADER_AUTHORIZATION, SessionManager.getInstance().getUser().getToken())
                .build()
                .getAsObjectList(UserChallenge.class, new ParsedRequestListener<List<UserChallenge>>() {
                    @Override
                    public void onResponse(List<UserChallenge> response) {
                        Log.d(TAG, "get all userchallenge succeeded");
                        homeView.getChallengeSucceed(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        String errorBody = anError.getErrorBody() != null ? anError.getErrorBody() : "error without content";
                        Log.d(TAG, "Display UserChallenge failed : "+errorBody);
                        homeView.getChallengeFailed();
                    }
                });
    }

    @Override
    public void updateUserChallenge() {

    }
}
