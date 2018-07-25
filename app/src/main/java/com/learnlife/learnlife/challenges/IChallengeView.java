package com.learnlife.learnlife.challenges;

import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.models.UserChallenge;

import java.util.List;

public interface IChallengeView {

    void printChallenges(List<Challenge> challenges);
    void updateChallengeFailed();
    void updateChallengeSucceeded();
    void updateUserChallenges(List<UserChallenge> userChallenges);
}
