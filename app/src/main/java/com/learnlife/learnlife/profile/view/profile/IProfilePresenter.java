package com.learnlife.learnlife.profile.view.profile;

import com.learnlife.learnlife.crosslayers.models.User;

/**
 * Created by davidlinhares on 02/07/2018.
 */

public interface IProfilePresenter {
    void getUser(User user);
    void getUserChallenges(User user);
}
