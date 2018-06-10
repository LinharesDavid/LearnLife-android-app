package com.learnlife.learnlife.challenges;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.LearnLifeApplication;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.crosslayers.models.Challenge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ChallengePresenter implements IChallengePresenter {

    private static final String TAG = "ChallengePresenter";

    private IChallengeView mainView;
    private String[] sectionTitles = new String[5];

    public ChallengePresenter(IChallengeView mainView, Context context) {
        this.mainView = mainView;

        sectionTitles[0] = context.getString(R.string.sectionTitleProposed);
        sectionTitles[1] = context.getString(R.string.sectionTitleDeclined);
        sectionTitles[2] = context.getString(R.string.sectionTitleAccepted);
        sectionTitles[3] = context.getString(R.string.sectionTitleFailed);
        sectionTitles[4] = context.getString(R.string.sectionTitleSucceed);
    }

    @Override
    public void getChallenges() {

        final ArrayList<Challenge> challenges = new ArrayList<>();

        Log.d(TAG, "getChallenges: " + Constants.BASE_URL
                + Constants.EXTENDED_URL_USERCHALLENGES
                + SessionManager.getInstance().getUser().getId()
                + Constants.EXTENDED_URL_USERCHALLENGES_LIST);

        AndroidNetworking.get(Constants.BASE_URL
                            + Constants.EXTENDED_URL_USERCHALLENGES
                            + SessionManager.getInstance().getUser().getId() + "/"
                            + Constants.EXTENDED_URL_USERCHALLENGES_LIST)
                .addHeaders(Constants.HEADER_AUTHORIZATION, SessionManager.getInstance().getUser().getToken())
                .setTag("getAllUserChallenges")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse: response arrived:"+response.toString());
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonResponse = response.getJSONObject(i);
                                JSONObject jsonChallenge = jsonResponse.getJSONObject(Constants.RESPONSE_KEY_USERCHALLENGES_CHALLENGE);
                                Challenge challenge = new Challenge(
                                        jsonChallenge.getString(Constants.RESPONSE_KEY_USERCHALLENGES_ID),
                                        jsonChallenge.getString(Constants.RESPONSE_KEY_USERCHALLENGES_NAME),
                                        jsonChallenge.getString(Constants.RESPONSE_KEY_USERCHALLENGES_DETAILS),
                                        jsonChallenge.getString(Constants.RESPONSE_KEY_USERCHALLENGES_IMAGE),
                                        jsonResponse.getInt(Constants.RESPONSE_KEY_USERCHALLENGES_STATE));
                                challenges.add(challenge);
                            }
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

        Collections.addAll(challengeList, challengesArray);

        challengeList.add(0, new Challenge(true, sectionTitles[challengeList.get(0).getState()]));

        for (int i = 1; i < challengeList.size() - 1; i++) {
            int state = challengeList.get(i).getState();
            int state1 = challengeList.get(i + 1).getState();
            if (state == -1) continue;
            if (state1 != state) {
                challengeList.add(i + 1, new Challenge(true, sectionTitles[challengeList.get(i + 1).getState()]));
            }
        }
        mainView.printChallenges(challengeList);
    }
}
