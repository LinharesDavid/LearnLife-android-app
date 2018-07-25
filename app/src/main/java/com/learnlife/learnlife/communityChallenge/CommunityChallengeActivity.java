package com.learnlife.learnlife.communityChallenge;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.androidnetworking.error.ANError;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.communityChallenge.usercommunitychallenges.UserCommunityChallengeActivity;
import com.learnlife.learnlife.communityChallenge.usercommunitychallenges.UserCommunityChallengePresenter;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.crosslayers.models.UserVote;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommunityChallengeActivity extends AppCompatActivity implements ICommunityChallengeView {
    @BindView(R.id.communityChallengesRcv)
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;
    ICommunityChallengePresenter presenter;
    private List<Challenge> challenges;
    private List<UserVote> votes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_challenge);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter = new CommunityChallengePresenter(this);
        this.setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        presenter.getCommunityChallenges();
        presenter.getUserVotes();
    }

    private void setAdapter() {
        adapter = new CommunityChallengeAdapter(this, challenges, votes);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.userChallengeBtn)
    public void onUserChallengeButton() {
        startActivity(new Intent(this, UserCommunityChallengeActivity.class));
    }

    @Override
    public void onCommunityChallengesRetrieved(List<Challenge> challenges) {
        this.challenges = challenges;
        if(this.votes != null) {
            this.setAdapter();
        }
    }

    @Override
    public void onCommunityChallengesError(ANError error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.challenge);
        builder.setMessage(R.string.network_error_unknown);
        builder.setPositiveButton(R.string.retry, (dialog, which) -> presenter.getCommunityChallenges());
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> finish());
    }

    @Override
    public void onUserVotesRetrieved(List<UserVote> userVotes) {
        this.votes = userVotes;
        if(this.challenges != null) {
            this.setAdapter();
        }
    }

    @Override
    public void onUserVotesError(ANError error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.challenge);
        builder.setMessage(R.string.network_error_unknown);
        builder.setPositiveButton(R.string.retry, (dialog, which) -> presenter.getUserVotes());
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> finish());
    }

    @Override
    public void onVoteClicked(Challenge challenge) {
        presenter.setUserVote(challenge);
    }

    @Override
    public void onVoteSucceed() {
        presenter.getUserVotes();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }
}
