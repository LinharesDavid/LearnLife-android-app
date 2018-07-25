package com.learnlife.learnlife.profile.view.profile;

import android.net.Uri;

import com.learnlife.learnlife.crosslayers.models.User;

import java.io.File;

/**
 * Created by davidlinhares on 02/07/2018.
 */

public interface IProfilePresenter {
    void getUser(User user);
    void getUserChallenges(User user);
    void setUserThumnail(User user, File file);
}
