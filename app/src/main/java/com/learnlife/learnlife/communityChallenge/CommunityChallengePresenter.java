package com.learnlife.learnlife.communityChallenge;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.crosslayers.models.UserVote;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by davidlinhares on 02/07/2018.
 */

public class CommunityChallengePresenter implements ICommunityChallengePresenter {

    private ICommunityChallengeView view;

    public CommunityChallengePresenter(ICommunityChallengeView view) {
        this.view = view;
    }

    @Override
    public void getCommunityChallenges() {
        String test = Constants.BASE_URL
                + Constants.EXTENDED_URL_CHALLENGES_COMMUNITY;
        AndroidNetworking.get(Constants.BASE_URL
                + Constants.EXTENDED_URL_CHALLENGES_COMMUNITY)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(Challenge.class, new ParsedRequestListener<List<Challenge>>() {
                    @Override
                    public void onResponse(List<Challenge> response) {
                        view.onCommunityChallengesRetrieved(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println();
                    }
                });
    }

    @Override
    public void getUserVotes() {
        User user = SessionManager.getInstance().getUser();
        AndroidNetworking.get(Constants.BASE_URL
                + Constants.EXTENDED_URL_USERVOTES + user.getId())
                .addHeaders(Constants.HEADER_AUTHORIZATION, user.getToken())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(UserVote.class, new ParsedRequestListener<List<UserVote>>() {
                    @Override
                    public void onResponse(List<UserVote> response) {
                        view.onUserVotesRetrieved(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println();
                    }
                });
    }

    @Override
    public void setUserVote(Challenge challenge) {
        User user = SessionManager.getInstance().getUser();
        UserVote vote = new UserVote(user.getId(), challenge.get_id());
        JSONObject body;
        try {
            body = new JSONObject(new Gson().toJson(vote));
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        AndroidNetworking.post(Constants.BASE_URL
                + Constants.EXTENDED_URL_USERVOTES)
                .addHeaders(Constants.HEADER_AUTHORIZATION, user.getToken())
                .addJSONObjectBody(body)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(UserVote.class, new ParsedRequestListener<UserVote>() {
                    @Override
                    public void onResponse(UserVote response) {
                        view.onVoteSucceed();
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println();
                    }
                });
    }
}
