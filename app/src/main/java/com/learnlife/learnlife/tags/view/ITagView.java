package com.learnlife.learnlife.tags.view;

import com.learnlife.learnlife.tags.modele.Tag;

import java.util.List;

/**
 * Created by Adama on 10/06/2018.
 */

public interface ITagView {
    void getTagFailed();
    void getTagSucceed(List<Tag> response);
    void updateUserTagSucceed();
    void updateUserTagFailed();
}
