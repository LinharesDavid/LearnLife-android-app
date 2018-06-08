package com.learnlife.learnlife.tags.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.google.gson.JsonObject;
import com.learnlife.learnlife.LearnLifeApplication;
import com.learnlife.learnlife.Main.view.MainActivity;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.utils.Dialog;
import com.learnlife.learnlife.login.view.LoginActivity;
import com.learnlife.learnlife.tags.modele.Tag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TagActivity extends AppCompatActivity {


    //region Attributes
    @BindView(R.id.rcvMain) RecyclerView rcvMain;
    @BindView(R.id.btnValider) Button btnValider;
    @BindView(R.id.txvFirstConnexion) TextView txvFirstConnexion;

    private TagAdapter adapter;
    private ChipsLayoutManager chipsLayoutManager;
    private String userFirstName;
    private String userId;
    public List<String> tagsChosen;
    private final String Tag = getClass().getSimpleName();
    //endregion

    //region Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        ButterKnife.bind(this);

        tagsChosen = new ArrayList<>();
        userId = getIntent().getStringExtra(LoginActivity.EXTRA_IDUSER);
        userFirstName = getIntent().getStringExtra(LoginActivity.EXTRA_NAMEUSER);
        if(userFirstName != null)
            txvFirstConnexion.setText(getResources().getString(R.string.first_connexionName, userFirstName));
        else
            txvFirstConnexion.setText(getResources().getString(R.string.first_connexionNameFailed));



        rcvMain.setHasFixedSize(true);
        chipsLayoutManager = ChipsLayoutManager.newBuilder(this)
                //set vertical gravity for all items in a row. Default = Gravity.CENTER_VERTICAL
                .setChildGravity(Gravity.NO_GRAVITY)
                .setScrollingEnabled(true)
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .build();
        rcvMain.setLayoutManager(chipsLayoutManager);
        rcvMain.addItemDecoration(new SpacingItemDecoration(getResources().getDimensionPixelOffset(R.dimen.chips_horizontal_margin), getResources().getDimensionPixelOffset(R.dimen.chips_horizontal_margin)));


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
    //endregion



    //region UI Events
    @OnClick(R.id.btnValider) public void btnValiderClicked(){
        Tag.JsonTag jsonTag = new Tag.JsonTag(tagsChosen.toArray(new String[tagsChosen.size()]));
        String urlRouteUpdateTag = LearnLifeApplication.BASE_URL + "/users/"+userId;

        AndroidNetworking.put(urlRouteUpdateTag)
                .addBodyParameter(jsonTag)
                .setTag("tag")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //prbTag.setVisibility(View.GONE); ProgressBar à ajouter
                        Log.d(Tag, "Tag update succeeded");

                        Intent intent = new Intent(TagActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(ANError anError) {
                        //prbTag.setVisibility(View.GONE); ProgressBar à ajouter

                        Dialog.showErrorMessageDialog(TagActivity.this, getString(R.string.tag_update_msg));

                        String errorBody = anError.getErrorBody() != null ? anError.getErrorBody() : "error without content";
                        Log.d(Tag, "Update Tag user failed : "+errorBody);
                    }
                });

        }
    //endregion
}
