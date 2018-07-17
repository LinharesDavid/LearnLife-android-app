package com.learnlife.learnlife.profile.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.models.Badge;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BadgeAdapter extends RecyclerView.Adapter {

    private final Context context;
    private ArrayList<Badge> badges;

    public BadgeAdapter(Context context , ArrayList<Badge> badges) {
        this.badges = badges;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.badge_item, parent, false);
        BadgeViewHolder holder = new BadgeViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Badge badge = badges.get(position);
        BadgeViewHolder viewHolder =  (BadgeViewHolder)holder;
        viewHolder.name.setText(badge.getName());
        Glide.with(context).load(Constants.BASE_URL + badge.getthumbnailUrl()).into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return badges.size();
    }

    class BadgeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.bageName_txv)
        TextView name;
        @BindView(R.id.badgeImage_imv)
        ImageView thumbnail;

        public BadgeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
