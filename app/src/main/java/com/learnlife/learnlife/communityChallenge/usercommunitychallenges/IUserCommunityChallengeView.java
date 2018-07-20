package com.learnlife.learnlife.communityChallenge.usercommunitychallenges;

import com.androidnetworking.error.ANError;
import com.learnlife.learnlife.crosslayers.models.Challenge;

import java.util.List;

/**
 * Created by davidlinhares on 02/07/2018.
 */

public interface IUserCommunityChallengeView {
    void onUserCommunityChallengeRetrieved(List<Challenge> challenges);
    void onUserCommunityChallengeError(ANError error);
}
