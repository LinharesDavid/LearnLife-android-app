package com.learnlife.learnlife.communityChallenge.usercommunitychallenges.createchallenge;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.Gson;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.tags.modele.Tag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by davidlinhares on 02/07/2018.
 */

public class CreateChallengePresenter implements ICreateChallengePresenter {
    private ICreateChallengeView view;

    public CreateChallengePresenter(ICreateChallengeView view) {
        this.view = view;
    }

    @Override
    public void getTags() {
        AndroidNetworking.get(Constants.BASE_URL + Constants.EXTENDED_URL_TAGS)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(Tag.class, new ParsedRequestListener<List<Tag>>() {
                    @Override
                    public void onResponse(List<Tag> response) {
                        view.onTagsRetrieved(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        view.onTagsError(anError);
                    }
                });
    }

    @Override
    public void createChallenge(User user, Challenge challenge) {
        JSONObject body = null;
        try {
            body = new JSONObject(new Gson().toJson(challenge));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post(Constants.BASE_URL + Constants.EXTENDED_URL_CHALLENGES)
                .setPriority(Priority.MEDIUM)
                .addJSONObjectBody(body)
                .build()
                .getAsObject(Challenge.class, new ParsedRequestListener<Challenge>() {
                    @Override
                    public void onResponse(Challenge response) {
                        view.onChallengeCreated(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        view.onChallengeCreationError(anError);
                    }
                });
    }
}
