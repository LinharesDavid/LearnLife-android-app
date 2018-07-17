package com.learnlife.learnlife.crosslayers.models;

/**
 * Created by davidlinhares on 09/06/2018.
 */

public class UserChallenge {
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    private String _id;
    private String user;
    private Challenge challenge;
    private int state;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
