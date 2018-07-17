package com.learnlife.learnlife.tags.view;

import android.support.v7.widget.RecyclerView;

import com.learnlife.learnlife.tags.modele.Tag;

/**
 * Created by Adama on 10/06/2018.
 */

public interface ITagPresenter {
    void displayAllTags(String urlAllTag);
    void affectTagToUser(Tag.JsonTag jsonTag);
}
