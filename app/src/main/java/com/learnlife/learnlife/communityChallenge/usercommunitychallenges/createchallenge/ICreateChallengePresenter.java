package com.learnlife.learnlife.communityChallenge.usercommunitychallenges.createchallenge;

import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.models.User;

/**
 * Created by davidlinhares on 02/07/2018.
 */

public interface ICreateChallengePresenter {
    void getTags();
    void createChallenge(User user, Challenge challenge);
}
