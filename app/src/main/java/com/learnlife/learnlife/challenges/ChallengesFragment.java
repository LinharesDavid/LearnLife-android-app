package com.learnlife.learnlife.challenges;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.models.Challenge;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChallengesFragment extends Fragment implements IChallengeView {
    private static final String TAG = "ChallengesFragment";

    @BindView(R.id.rcv_challenges)
    RecyclerView rcvChallenges;

    ChallengesAdapter challengesAdapter;

    private IChallengePresenter presenter;

    public ChallengesFragment() {
        //
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_challenges, container, false);

        ButterKnife.bind(this, view);

        presenter = new ChallengePresenter(this, getContext());

        rcvChallenges.setLayoutManager(new LinearLayoutManager(getContext()));
        challengesAdapter = new ChallengesAdapter(null);
        rcvChallenges.setAdapter(challengesAdapter);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.getChallenges();


    }

    @Override
    public void printChallenges(List<Challenge> userChallenges) {
        challengesAdapter.setChallenges(userChallenges);
        challengesAdapter.notifyDataSetChanged();
    }
}
