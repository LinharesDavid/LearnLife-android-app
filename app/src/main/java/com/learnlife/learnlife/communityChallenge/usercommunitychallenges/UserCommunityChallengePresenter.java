package com.learnlife.learnlife.communityChallenge.usercommunitychallenges;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.crosslayers.models.UserVote;

import java.util.List;

/**
 * Created by davidlinhares on 02/07/2018.
 */

public class UserCommunityChallengePresenter implements IUserCommunityChallengePresenter {
    private IUserCommunityChallengeView view;

    public UserCommunityChallengePresenter(IUserCommunityChallengeView view) {
        this.view = view;
    }

    @Override
    public void getUserChallenges(User user) {
        AndroidNetworking.get(Constants.BASE_URL
                + Constants.EXTENDED_URL_CHALLENGES_COMMUNITY + user.getId())
                .addHeaders(Constants.HEADER_AUTHORIZATION, user.getToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(Challenge.class, new ParsedRequestListener<List<Challenge>>() {
                    @Override
                    public void onResponse(List<Challenge> response) {
                        view.onUserCommunityChallengeRetrieved(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println();
                    }
                });
    }
}
