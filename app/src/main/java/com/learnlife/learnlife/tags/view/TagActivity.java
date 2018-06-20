package com.learnlife.learnlife.tags.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.main.view.MainActivity;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.crosslayers.utils.Dialog;
import com.learnlife.learnlife.profile.view.ProfileFragment;
import com.learnlife.learnlife.tags.modele.Tag;

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
    private ArrayList<Tag> userTags;
    public List<String> tagsChosen;
    private final String Tag = getClass().getSimpleName();
    private TagPresenter presenter;
    private boolean isFromProfileFragment = false;
    //endregion

    //region Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        ButterKnife.bind(this);

        tagsChosen = new ArrayList<>();
        userId = SessionManager.getInstance().getUser().getId();
        userFirstName = SessionManager.getInstance().getUser().getFirstname();
        isFromProfileFragment = getIntent().getBooleanExtra(ProfileFragment.class.getName(), false);
        if(isFromProfileFragment) {
            txvFirstConnexion.setText(getResources().getString(R.string.tag_selection));
            userTags = SessionManager.getInstance().getUser().getTags();
        } else {
            if(userFirstName != null)
                txvFirstConnexion.setText(getResources().getString(R.string.first_connexionName, userFirstName));
            else
                txvFirstConnexion.setText(getResources().getString(R.string.first_connexionNameFailed));
        }



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

        presenter.displayAllTags(Constants.BASE_URL + Constants.EXTENDED_URL_ALL_TAGS);
    }

    //endregion
    private ArrayList<String> getUserTagsNames(ArrayList<Tag> tags) {
        ArrayList<String> tagNames = new ArrayList<>();
        for (Tag t : tags)
            tagNames.add(t.getName());

        return tagNames;
    }


    //region UI Events
    @OnClick(R.id.btnValider) public void btnValiderClicked(){
        Tag.JsonTag jsonTag = new Tag.JsonTag(tagsChosen.toArray(new String[tagsChosen.size()]));
        presenter.affectTagToUser(jsonTag);
    }

    @Override
    public void getTagFailed() {
        Dialog.showErrorMessageDialog(TagActivity.this, getString(R.string.tag_get_msg));
    }

    @Override
    public void getTagSucceed(List<Tag> response) {
        adapter = new TagAdapter(response, TagActivity.this);
        rcvMain.setAdapter(adapter);
        adapter.setUserTags(userTags);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateUserTagSucceed() {
        if(isFromProfileFragment)
            finish();
        else {
            Intent intent = new Intent(TagActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void updateUserTagFailed() {
        Dialog.showErrorMessageDialog(TagActivity.this, getString(R.string.tag_update_msg));
    }
    //endregion
}
