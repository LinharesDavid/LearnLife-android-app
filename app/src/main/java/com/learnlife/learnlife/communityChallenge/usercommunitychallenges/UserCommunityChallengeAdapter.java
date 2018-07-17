package com.learnlife.learnlife.communityChallenge.usercommunitychallenges;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.models.Challenge;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidlinhares on 02/07/2018.
 */

public class UserCommunityChallengeAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Challenge> challenges;

    public UserCommunityChallengeAdapter(Context context , List<Challenge> challenges) {
        this.context = context;
        this.challenges = challenges;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.community_challenge_item, parent, false);
        UserComunityChallengeViewHolder holder = new UserComunityChallengeViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Challenge challenge = challenges.get(position);
        UserComunityChallengeViewHolder viewHolder = (UserComunityChallengeViewHolder) holder;

        viewHolder.voteButton.setVisibility(View.INVISIBLE);
        viewHolder.name.setText(challenge.getName());
        viewHolder.details.setText(challenge.getDetails());
        Glide.with(context).load(Constants.BASE_URL + challenge.getImageUrl()).into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    class UserComunityChallengeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.challengeName_txv)
        TextView name;
        @BindView(R.id.challengeDetails_txv)
        TextView details;
        @BindView(R.id.challengeImage_imv)
        ImageView thumbnail;
        @BindView(R.id.vote_btn)
        Button voteButton;

        public UserComunityChallengeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
