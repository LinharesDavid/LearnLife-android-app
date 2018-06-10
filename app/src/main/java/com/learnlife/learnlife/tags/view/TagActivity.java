package com.learnlife.learnlife.tags.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.learnlife.learnlife.LearnLifeApplication;
import com.learnlife.learnlife.main.view.MainActivity;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.utils.Dialog;
import com.learnlife.learnlife.login.view.LoginActivity;
import com.learnlife.learnlife.tags.modele.Tag;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TagActivity extends AppCompatActivity implements ITagView {


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
    private TagPresenter presenter;
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


        presenter = new TagPresenter(this);

        String urlAllTags = LearnLifeApplication.BASE_URL + "/tags";
        presenter.displayAllTags(urlAllTags);
    }
    //endregion



    //region UI Events
    @OnClick(R.id.btnValider) public void btnValiderClicked(){
        Tag.JsonTag jsonTag = new Tag.JsonTag(tagsChosen.toArray(new String[tagsChosen.size()]));
        String urlRouteUpdateTag = LearnLifeApplication.BASE_URL + "/users/"+userId;

        presenter.affectTagToUser(urlRouteUpdateTag, jsonTag);

    }

    @Override
    public void getTagFailed() {
        Dialog.showErrorMessageDialog(TagActivity.this, getString(R.string.tag_get_msg));
    }

    @Override
    public void getTagSucceed(List<Tag> response) {
        adapter = new TagAdapter(response, TagActivity.this);
        rcvMain.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void updateUserTagSucceed() {
        Intent intent = new Intent(TagActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateUserTagFailed() {
        Dialog.showErrorMessageDialog(TagActivity.this, getString(R.string.tag_update_msg));
    }
    //endregion
}
