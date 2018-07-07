package com.learnlife.learnlife.profile.view.profile;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.crosslayers.models.UserChallenge;
import com.learnlife.learnlife.profile.view.profile.IProfilePresenter;
import com.learnlife.learnlife.profile.view.profile.IProfileView;

import java.util.List;

public class ProfilePresenter implements IProfilePresenter {

    private IProfileView view;

    public ProfilePresenter(IProfileView view) {
        this.view = view;
    }

    @Override
    public void getUser(User user) {
        AndroidNetworking
                .get(Constants.BASE_URL +
                        Constants.EXTENDED_URL_USERS +
                        user.getId())
                .addHeaders(Constants.HEADER_AUTHORIZATION, user.getToken())
                .build()
                .getAsObject(User.class, new ParsedRequestListener<User>() {
                    @Override
                    public void onResponse(User response) {
                        view.onUserRetrived(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println(anError.getErrorBody().toString());
                    }
                });
    }

    @Override
    public void getUserChallenges(User user) {
        AndroidNetworking.get(Constants.BASE_URL
                + Constants.EXTENDED_URL_USERCHALLENGES
                + user.getId() + "/"
                + Constants.EXTENDED_URL_USERCHALLENGES_LIST)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(UserChallenge.class, new ParsedRequestListener<List<UserChallenge>>() {
                    @Override
                    public void onResponse(List<UserChallenge> response) {
                        view.onUserChallengeRertieve(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println();
                    }
                });
    }
}
