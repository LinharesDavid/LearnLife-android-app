package com.learnlife.learnlife.communityChallenge.usercommunitychallenges;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.communityChallenge.CommunityChallengeAdapter;
import com.learnlife.learnlife.communityChallenge.usercommunitychallenges.createchallenge.CreateChallengeActivity;
import com.learnlife.learnlife.crosslayers.models.Challenge;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserCommunityChallengeActivity extends AppCompatActivity implements IUserCommunityChallengeView {
    private IUserCommunityChallengePresenter presenter;
    @BindView(R.id.userChallengesRcv)
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_community_challenge);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.presenter = new UserCommunityChallengePresenter(this);
        this.setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        presenter.getUserChallenges(SessionManager.getInstance().getUser());
    }

    @OnClick(R.id.createChallengeBtn)
    public void onCreateChallengeButtonClicked() {
        startActivity(new Intent(this, CreateChallengeActivity.class));
    }

    @Override
    public void onUserCommunityChallengeRetrieved(List<Challenge> challenges) {
        adapter = new UserCommunityChallengeAdapter(this, challenges);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onUserCommunityChallengeError(ANError error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.challenge);
        builder.setMessage(R.string.network_error_unknown);
        builder.setPositiveButton(R.string.retry, (dialog, which) -> presenter.getUserChallenges(SessionManager.getInstance().getUser()));
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> finish());
    }
}
