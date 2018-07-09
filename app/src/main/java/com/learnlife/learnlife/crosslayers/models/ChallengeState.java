package com.learnlife.learnlife.crosslayers.models;

/**
 * Created by davidlinhares on 09/06/2018.
 */

public enum ChallengeState {
    PROPOSED(0),
    DECLINED(1),
    ACCEPTED(2),
    FAILED(3),
    SUCCEED(4);

    private final int intValue;

    ChallengeState(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }
}
