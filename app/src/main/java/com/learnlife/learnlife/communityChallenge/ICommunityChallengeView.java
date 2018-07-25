package com.learnlife.learnlife.communityChallenge;

import com.androidnetworking.error.ANError;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.models.UserVote;

import java.util.List;

/**
 * Created by davidlinhares on 02/07/2018.
 */

public interface ICommunityChallengeView {
    void onCommunityChallengesRetrieved(List<Challenge> challenges);
    void onCommunityChallengesError(ANError error);
    void onUserVotesRetrieved(List<UserVote> votes);
    void onUserVotesError(ANError error);
    void onVoteClicked(Challenge challenge);
    void onVoteSucceed();
}
