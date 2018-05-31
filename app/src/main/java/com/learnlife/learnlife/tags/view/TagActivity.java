package com.learnlife.learnlife.tags.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.learnlife.learnlife.LearnLifeApplication;
import com.learnlife.learnlife.Main.view.MainActivity;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.tags.modele.Tag;

import org.json.JSONArray;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TagActivity extends AppCompatActivity {

    @BindView(R.id.rcvMain) RecyclerView rcvMain;
    @BindView(R.id.btnValider) Button btnValider;

    private TagAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        ButterKnife.bind(this);

        rcvMain.setHasFixedSize(true);
        rcvMain.setLayoutManager(new LinearLayoutManager(this));
        rcvMain.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        AndroidNetworking.get(LearnLifeApplication.BASE_URL + "/tags")
                .setTag("tags")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(Tag.class, new ParsedRequestListener<List<Tag>>() {

                    @Override
                    public void onResponse(List<Tag> response) {
                        adapter = new TagAdapter(response, TagActivity.this);
                        rcvMain.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(TagActivity.this, "Error parsing Tag", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @OnClick(R.id.btnValider) public void btnValiderClicked(){
        startActivity(new Intent(this, MainActivity.class));
    }
}
