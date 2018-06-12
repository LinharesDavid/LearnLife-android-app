package com.learnlife.learnlife.home.view;

import com.learnlife.learnlife.crosslayers.models.Challenge;

import java.util.List;

public interface IHomeView {
    void getChallengeFailed();
    void getChallengeSucceed(List<Challenge> responses);
    void updateUserChallengeFailed();
    void updateUserChallengeSucceed();
}
