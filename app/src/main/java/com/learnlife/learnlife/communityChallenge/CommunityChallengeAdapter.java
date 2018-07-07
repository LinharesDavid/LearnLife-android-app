package com.learnlife.learnlife.communityChallenge;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.models.UserVote;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunityChallengeAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Challenge> challenges;
    private List<String> challengesVoted = new ArrayList<String>();

    public CommunityChallengeAdapter(Context context , List<Challenge> challenges, List<UserVote> votes) {
        this.context = context;
        this.challenges = challenges;
        for (UserVote vote : votes)
            challengesVoted.add(vote.getChallengeId());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.community_challenge_item, parent, false);
        ComunityChallengeViewHolder holder = new ComunityChallengeViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Challenge challenge = challenges.get(position);
        ComunityChallengeViewHolder viewHolder = (ComunityChallengeViewHolder) holder;

        viewHolder.name.setText(challenge.getName());
        viewHolder.details.setText(challenge.getDetails());
        if(challengesVoted.contains(challenge.get_id())) {
            viewHolder.voteButton.setVisibility(View.INVISIBLE);
            viewHolder.voteButton.setClickable(false);
        }
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    class ComunityChallengeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.challengeName_txv)
        TextView name;
        @BindView(R.id.challengeDetails_txv)
        TextView details;
        @BindView(R.id.challengeImage_imv)
        ImageView thumbnail;
        @BindView(R.id.vote_btn)
        Button voteButton;

        public ComunityChallengeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
