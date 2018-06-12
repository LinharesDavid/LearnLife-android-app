package com.learnlife.learnlife.home.view;

import com.learnlife.learnlife.crosslayers.models.UserChallenge;

import java.util.List;

public interface IHomeView {
    void getChallengeFailed();
    void getChallengeSucceed(List<UserChallenge> responses);
    void updateUserChallengeFailed();
    void updateUserChallengeSucceed();
}
