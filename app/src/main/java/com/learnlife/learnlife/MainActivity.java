package com.learnlife.learnlife;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.learnlife.learnlife.challenges.ChallengesActivity;
import com.learnlife.learnlife.home.view.HomeActivity;
import com.learnlife.learnlife.profile.view.ProfileActivity;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

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

        ButterKnife.bind(this);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.ctnFragment, new HomeActivity(), "Home")
                .commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch (id){
                    case R.id.action_home:
                        fragment = new HomeActivity();
                        break;
                    case R.id.action_challenges:
                        fragment = new ChallengesActivity();
                        break;
                    case R.id.action_profile:
                        fragment = new ProfileActivity();
                        break;
                }
                final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.ctnFragment, fragment).commit();
                return true;
            }
        });
    }


    public void setSelectedNavigationItem(int id){
        bottomNavigationView.setSelectedItemId(id);
    }
}
