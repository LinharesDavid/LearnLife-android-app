package com.learnlife.learnlife.challenges;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.learnlife.learnlife.LearnLifeApplication;
import com.learnlife.learnlife.crosslayers.models.Challenge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ChallengePresenter implements IChallengePresenter {

    private static final String TAG = "ChallengePresenter";

    private IChallengeView mainView;

    public ChallengePresenter(IChallengeView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void getChallenges() {

        final ArrayList<Challenge> challenges = new ArrayList<>();

        AndroidNetworking.get(LearnLifeApplication.BASE_URL + "/userChallenges/" + LearnLifeApplication.idUser + "/list")
                .addHeaders("Authorization", LearnLifeApplication.token)
                .setTag("getAllUserChallenges")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse: response arrived");
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonResponse = response.getJSONObject(i);
                                JSONObject jsonChallenge = jsonResponse.getJSONObject("challenge");
                                Challenge challenge = new Challenge(
                                        jsonChallenge.getString("_id"),
                                        jsonChallenge.getString("name"),
                                        jsonChallenge.getString("details"),
                                        jsonChallenge.getString("imageUrl"),
                                        jsonResponse.getInt("state"));
                                challenges.add(challenge);
                            }
                            JSONObject j = response.getJSONObject(0);
                            Log.d(TAG, "onResponse: " + j.getString("challenge"));
                            orderChallengeList(challenges);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                    }
                });
    }


    private void orderChallengeList(ArrayList<Challenge> challengeList) {

        Log.d(TAG, "orderChallengeList() called with: challengeList = [" + challengeList + "]");

        if (challengeList == null) {
            return;
        }

        Challenge[] challengesArray = new Challenge[challengeList.size()];

        // List<E>.sort(Comparator c) is available since api 24,
        // we're in min 21
        for (int i = 0; i < challengeList.size(); i++) {
            challengesArray[i] = challengeList.get(i);
        }

        Arrays.sort(challengesArray, new Comparator<Challenge>() {
            @Override
            public int compare(Challenge challenge, Challenge t1) {
                return challenge.getState() - t1.getState();
            }
        });
        challengeList.clear();
        for (Challenge challenge : challengesArray) {
            Log.d(TAG, "orderChallengeList: " + challenge.toString());
            challengeList.add(challenge);
        }

        challengeList.add(0, new Challenge(true, "Section " + (challengeList.get(0).getState() + 1)));

        for (int i = 1; i < challengeList.size() - 1; i++) {
            int state = challengeList.get(i).getState();
            int state1 = challengeList.get(i + 1).getState();
            if (state == -1) continue;
            if (state1 != state) {
                challengeList.add(i + 1, new Challenge(true, "Section " + (challengeList.get(i + 1).getState() + 1)));
            }
        }

        for (Challenge challenge : challengeList) {
            Log.d(TAG, "orderChallengeList: " + challenge.isSection());
        }
        mainView.printChallenges(challengeList);
    }
}
