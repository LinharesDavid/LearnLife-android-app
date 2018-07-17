package com.learnlife.learnlife.profile.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.models.Badge;
import com.learnlife.learnlife.tags.view.TagAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserBadgeActivity extends AppCompatActivity {
    @BindView(R.id.badges_rcv)
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;

    public static String BADGES_KEY;
    private ArrayList<Badge> badges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_badge);
        ButterKnife.bind(this);

        this.badges = new Gson().fromJson(getIntent().getExtras().getString(BADGES_KEY), new TypeToken<ArrayList<Badge>>(){}.getType());

        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new BadgeAdapter(this, badges);
        recyclerView.setAdapter(adapter);
    }

    public void setBadges(ArrayList<Badge> badges) {
        this.badges = badges;
    }
}

