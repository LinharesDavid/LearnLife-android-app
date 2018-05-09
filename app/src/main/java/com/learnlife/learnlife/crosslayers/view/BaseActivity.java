package com.learnlife.learnlife.crosslayers.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.learnlife.learnlife.R;
import com.learnlife.learnlife.home.view.HomeActivity;
import com.learnlife.learnlife.profile.view.ProfileActivity;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    public  abstract int getLayoutId();
    public abstract int getNavigationMenuItemId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_home:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.action_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            default:
                break;
        }

        finish();
        return true;
    }


    /**
     * ça va permettre de pas voir l'animation de base au passage d'une activity à une autre
     */
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    private void updateNavigationBarState(){
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    private void selectBottomNavigationBarItem(int itemId){
        Menu menu = navigationView.getMenu();
        for(int i = 0, size = menu.size(); i < size; i++){
            MenuItem item = menu.getItem(i);
            if(item.getItemId() == itemId){
                item.setChecked(true);
                break;
            }
        }
    }
}
