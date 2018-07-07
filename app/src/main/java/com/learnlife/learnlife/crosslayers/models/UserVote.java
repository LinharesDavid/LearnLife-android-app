package com.learnlife.learnlife.crosslayers.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by davidlinhares on 02/07/2018.
 */

public class UserVote {
    @SerializedName("_id")
    private String id;
    @SerializedName("user")
    private String userId;
    @SerializedName("challenge")
    private String challengeId;

    public UserVote(String id, String userId, String challengeId) {
        this.id = id;
        this.userId = userId;
        this.challengeId = challengeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }
}
