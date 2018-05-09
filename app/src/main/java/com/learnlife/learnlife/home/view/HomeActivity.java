package com.learnlife.learnlife.home.view;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.learnlife.learnlife.MainActivity;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.utils.MyDateUtils;
import com.learnlife.learnlife.home.adapter.Adapter;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends Fragment {

    /***********************************************************
    *  Attributes
    **********************************************************/
    @BindView(R.id.txvTodayDate) public TextView txvTodayDate;
    @BindView(R.id.idFling) public SwipeFlingAdapterView flingContainer;
    @BindView(R.id.txvNoMoreChallenge) public TextView txvNoMoreChallenge;
    @BindView(R.id.imbDecline) public ImageButton imbDecline;
    @BindView(R.id.imbAccept) public ImageButton imbAccept;


    private ArrayList<Challenge> challenges = new ArrayList<>();
    private Adapter adapter;
    private Animation animationBounce;


    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.activity_home, container, false);

        ButterKnife.bind(this, view);

        //Juste pour les tests
        for(int i = 0; i < 5; i++){
            challenges.add(new Challenge().falseChallengeGenerator());
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txvTodayDate.setText(MyDateUtils.fullDate(getContext()));
        animationBounce = AnimationUtils.loadAnimation(getContext(), R.anim.button_bounce);

        //Custom Adapter
        adapter = new Adapter(getContext(), R.layout.cartouche_challenge, challenges);

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
                Toast.makeText(getContext(), "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object o) {
                Toast.makeText(getContext(), "Right!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), challenges.get(i).getIdChallenge()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /***********************************************************
     *  Buttons Events
     **********************************************************/

    @OnClick(R.id.imbDecline)
    public void btnDeclineClicked(){
        flingContainer.getTopCardListener().selectLeft();
        imbDecline.startAnimation(animationBounce);
    }

    @OnClick(R.id.imbAccept)
    public void btnAcceptClicked(){
        flingContainer.getTopCardListener().selectRight();
        imbAccept.startAnimation(animationBounce);
    }

}