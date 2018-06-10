package com.learnlife.learnlife.Main.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.learnlife.learnlife.R;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.challenges.ChallengesFragment;
import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.home.view.HomeFragment;
import com.learnlife.learnlife.profile.view.ProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    /***********************************************************
    *  Attributes
    **********************************************************/
    @BindView(R.id.bottomNavigationView) public BottomNavigationView bottomNavigationView;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionManager.getInstance().checkLogin();

        ButterKnife.bind(this);

        bottomNavigationView.setSelectedItemId(R.id.action_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch (id){
                    case R.id.action_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.action_challenges:
                        fragment = new ChallengesFragment();
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        break;
                }
                
                final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.ctnFragment, fragment).commit();
                return true;
            }
        });
    }
}
