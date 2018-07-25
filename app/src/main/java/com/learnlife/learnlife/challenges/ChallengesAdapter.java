package com.learnlife.learnlife.challenges;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.models.UserChallenge;

import java.util.List;

public class ChallengesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_SECTION = 1;
    private ChallengesFragment fragment;

    private List<Challenge> challenges;

    private List<UserChallenge> userChallenges;

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public ChallengesAdapter(ChallengesFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_challenge, parent, false);
            ChallengeViewHolder challengeViewHolder = new ChallengeViewHolder(itemView);
            challengeViewHolder.imbFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = challengeViewHolder.getAdapterPosition();
                    fragment.challengeSelected(challenges.get(pos));
                }
            });
            return challengeViewHolder;
        } else if (viewType == VIEW_TYPE_SECTION) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);
            return new SectionViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChallengeViewHolder) {
            Challenge challenge = challenges.get(position);
            ((ChallengeViewHolder) holder).txvTitle.setText(challenge.getName());
            ((ChallengeViewHolder) holder).txvContent.setText(challenge.getDetails());
        } else if (holder instanceof SectionViewHolder) {
            ((SectionViewHolder) holder).txvSectionTitle.setText(challenges.get(position).getSectionTitle());
        }
    }

    private int getChallengeSectionTotal() {
        int count = 0;
        for (Challenge c : challenges) {
            if (c.isSection()) count++;
        }

        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (challenges != null && challenges.get(position).isSection()) {
            return VIEW_TYPE_SECTION;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return challenges == null ? 0 : challenges.size();
    }

    public List<UserChallenge> getUserChallenges() {
        return userChallenges;
    }

    public void setUserChallenges(List<UserChallenge> userChallenges) {
        this.userChallenges = userChallenges;
    }

    class ChallengeViewHolder extends RecyclerView.ViewHolder {

        TextView txvTitle;
        TextView txvContent;
        ImageButton imbFinish;

        ChallengeViewHolder(View itemView) {
            super(itemView);
            txvTitle = itemView.findViewById(R.id.txv_item_title);
            txvContent = itemView.findViewById(R.id.txv_item_content);
            imbFinish = itemView.findViewById(R.id.imbFinish);
        }
    }

    class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView txvSectionTitle;

        SectionViewHolder(View itemView) {
            super(itemView);
            txvSectionTitle = itemView.findViewById(R.id.txv_section_title);
        }
    }
}
