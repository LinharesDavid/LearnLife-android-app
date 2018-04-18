package com.learnlife.learnlife.home.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.utils.MyDateUtils;
import com.learnlife.learnlife.home.adapter.Adapter;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    /***********************************************************
    *  Attributes
    **********************************************************/
    @BindView(R.id.txvTodayDate) public TextView txvTodayDate;
    @BindView(R.id.idFling) public SwipeFlingAdapterView flingContainer;
    @BindView(R.id.txvNoMoreChallenge) public TextView txvNoMoreChallenge;

    private ArrayList<Challenge> challenges = new ArrayList<>();
    private Adapter adapter;
    private Animation animationBounce;

    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        txvTodayDate.setText(MyDateUtils.fullDate(this));
        animationBounce = AnimationUtils.loadAnimation(this, R.anim.button_bounce);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Juste pour les tests
        for(int i = 0; i < 5; i++){
            challenges.add(new Challenge().falseChallengeGenerator());
        }

        if(user != null){
            Toast.makeText(this, "USER NOT NULL", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "USER NULL", Toast.LENGTH_SHORT).show();
        }

        //Custom Adapter
        adapter = new Adapter(this, R.layout.cartouche_challenge, challenges);

        flingContainer.setAdapter(adapter);

        //Swipe Listener
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                challenges.remove(0);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {
                Toast.makeText(HomeActivity.this, "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object o) {
                Toast.makeText(HomeActivity.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {
                txvNoMoreChallenge.setVisibility(i == 0 ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onScroll(float v) {

            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int i, Object o) {
                Toast.makeText(HomeActivity.this, challenges.get(i).getIdChallenge()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /***********************************************************
     *  Buttons Events
     **********************************************************/
    public void btnDeclineClicked(View view){
        flingContainer.getTopCardListener().selectLeft();
        view.startAnimation(animationBounce);
    }

    public void btnAcceptClicked(View view){
        flingContainer.getTopCardListener().selectRight();
        view.startAnimation(animationBounce);
    }

}
