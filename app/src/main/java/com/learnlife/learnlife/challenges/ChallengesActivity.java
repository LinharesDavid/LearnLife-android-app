package com.learnlife.learnlife.challenges;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.view.BaseActivity;

public class ChallengesActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_challenges;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.action_challenges;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);
    }
}
