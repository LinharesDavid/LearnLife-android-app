package com.learnlife.learnlife.challenges;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;
import com.androidnetworking.interfaces.StringRequestListener;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.LearnLifeApplication;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.communityChallenge.usercommunitychallenges.UserCommunityChallengeActivity;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.models.UserChallenge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

        final ArrayList<UserChallenge> challenges = new ArrayList<>();

        Log.d(TAG, "getChallenges: " + Constants.BASE_URL
                + Constants.EXTENDED_URL_USERCHALLENGES
                + SessionManager.getInstance().getUser().getId()
                + '/'
                + Constants.EXTENDED_URL_USERCHALLENGES_LIST);

        AndroidNetworking.get(Constants.BASE_URL
                            + Constants.EXTENDED_URL_USERCHALLENGES
                            + SessionManager.getInstance().getUser().getId()
                            + '/'
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
                                UserChallenge challenge = new Gson().fromJson(jsonResponse.toString(), UserChallenge.class);
                                challenges.add(challenge);
                            }
                            mainView.updateUserChallenges(challenges);
                            orderuserChallengeList(challenges);

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

    @Override
    public void finishChallenge(String idChallenge) {
        String url = Constants.BASE_URL + Constants.EXTENDED_URL_USERCHALLENGES + idChallenge + '/'+ Constants.EXTENDED_URL_USERCHALLENGES_SUCCEED;
        AndroidNetworking.put(url)
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .addHeaders(Constants.HEADER_AUTHORIZATION, SessionManager.getInstance().getUser().getToken())
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Update userchallenge succeeded");
                        mainView.updateChallengeSucceeded();
                    }

                    @Override
                    public void onError(ANError anError) {
                        String errorBody = anError.getErrorBody() != null ? anError.getErrorBody() : "error without content";
                        Log.d(TAG, "Update userchallenge failed : "+errorBody);
                        mainView.updateChallengeFailed();
                    }
                });
    }


    private void orderuserChallengeList(ArrayList<UserChallenge> userChallengeList) {

        List<UserChallenge> originalList = userChallengeList;
        if (userChallengeList == null) {
            return;
        }

        UserChallenge[] challengesArray = new UserChallenge[userChallengeList.size()];

        // List<E>.sort(Comparator c) is available since api 24,
        // we're in min 21
        for (int i = 0; i < userChallengeList.size(); i++) {
            challengesArray[i] = userChallengeList.get(i);
        }

        Arrays.sort(challengesArray, new Comparator<UserChallenge>() {
            @Override
            public int compare(UserChallenge challenge, UserChallenge t1) {
                return challenge.getState() - t1.getState();
            }
        });
        userChallengeList.clear();

        Collections.addAll(userChallengeList, challengesArray);
        ArrayList<Challenge> challenges = new ArrayList<>();
        challenges.add(new Challenge(true, sectionTitles[userChallengeList.get(0).getState()]));
        UserChallenge previous = null;
        for(UserChallenge userChallenge : userChallengeList) {
            if(previous == null)
                challenges.add(userChallenge.getChallenge());
            else {
                if(previous.getState() != userChallenge.getState()) {
                    challenges.add(new Challenge(true, sectionTitles[userChallenge.getState()]));
                }
                challenges.add(userChallenge.getChallenge());
            }

            previous = userChallenge;
        }



        if (userChallengeList.isEmpty()) return;

        mainView.updateUserChallenges(originalList);
        mainView.printChallenges(challenges);
    }
}
