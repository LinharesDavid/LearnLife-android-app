package com.learnlife.learnlife.communityChallenge;

import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.models.User;

/**
 * Created by davidlinhares on 02/07/2018.
 */

public interface ICommunityChallengePresenter {
    void getCommunityChallenges();
    void getUserVotes();
    void setUserVote(Challenge challenge);
}
