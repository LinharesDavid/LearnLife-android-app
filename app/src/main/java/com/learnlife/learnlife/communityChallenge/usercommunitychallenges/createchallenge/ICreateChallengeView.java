package com.learnlife.learnlife.communityChallenge.usercommunitychallenges.createchallenge;

import android.icu.lang.UScript;

import com.androidnetworking.error.ANError;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.tags.modele.Tag;

import java.util.List;

/**
 * Created by davidlinhares on 02/07/2018.
 */

public interface ICreateChallengeView {
    void onTagsRetrieved(List<Tag> tags);
    void onTagsError(ANError error);
    void onChallengeCreated(Challenge challenge);
    void onChallengeCreationError(ANError error);
    void onSetChallengeImageSucceed(Challenge challenge);
    void onSetChallengeImageFailed(ANError error);
}
