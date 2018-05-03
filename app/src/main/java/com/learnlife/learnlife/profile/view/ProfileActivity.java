package com.learnlife.learnlife.profile.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.view.BaseActivity;

public class ProfileActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.action_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
