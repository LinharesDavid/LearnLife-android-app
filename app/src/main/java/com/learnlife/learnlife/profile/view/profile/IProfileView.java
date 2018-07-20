package com.learnlife.learnlife.profile.view.profile;

import com.androidnetworking.error.ANError;
import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.crosslayers.models.UserChallenge;

import java.util.List;

/**
 * Created by davidlinhares on 02/07/2018.
 */

public interface IProfileView {
    void onUserRetrived(User user);
    void onUserChallengeRertieve(List<UserChallenge> userChallenges);
    void onUserThumbnailSucceed(User user);
}
