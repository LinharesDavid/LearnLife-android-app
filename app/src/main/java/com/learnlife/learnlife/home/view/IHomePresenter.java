package com.learnlife.learnlife.home.view;

import com.learnlife.learnlife.crosslayers.models.User;

public interface IHomePresenter {
    void displayUserChallenge();
    void updateUserChallenge(int state, String idChallenge);
    void retrieveUser(User user);
}
