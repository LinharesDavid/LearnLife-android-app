package com.learnlife.learnlife.profile.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.LearnLifeApplication;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.crosslayers.models.Badge;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.models.ChallengeState;
import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.crosslayers.models.UserChallenge;
import com.learnlife.learnlife.crosslayers.utils.CircleTransform;
import com.learnlife.learnlife.tags.view.TagActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends Fragment {

    @BindView(R.id.imvProfile) ImageView profileImageView;
    @BindView(R.id.txvFailed) TextView failedTextView;
    @BindView(R.id.txvSucceeded) TextView succeedTextView;
    @BindView(R.id.txvFirstname) TextView firstnameTextView;
    @BindView(R.id.txvLastname) TextView lastnameTextView;
    @BindView(R.id.txvEmail) TextView emailTextView;
    @BindView(R.id.btnEdit) ImageView editButton;
    @BindView(R.id.logoutBtn) Button logoutButton;
    @BindView(R.id.btnBadges) Button badgeButton;
    @BindView(R.id.btnTags) Button tagButton;

    private List<UserChallenge> challenges;
    private User user = SessionManager.getInstance().getUser();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setButtonsClickable(true, badgeButton, logoutButton, tagButton);
        editButton.setClickable(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getUser();
        this.getChallenges();
        this.setUserProfileImageView();
        this.fillEditTexts();
    }

    @OnClick(R.id.btnTags)
    void onTagsButtonClicked() {
        tagButton.setClickable(false);
        Intent intent = new Intent(getActivity(), TagActivity.class);
        intent.putExtra(this.getClass().getName(), true);
        startActivity(intent);
    }

    @OnClick(R.id.btnBadges)
    void onBadgesButtonClicked() {
        badgeButton.setClickable(false);
        Intent intent = new Intent(getActivity(), UserBadgeActivity.class);
        intent.putExtra(UserBadgeActivity.BADGES_KEY, new Gson().toJson(user.getBadges()));
        startActivity(intent);
    }

    @OnClick(R.id.btnEdit)
    void onEditButtonClicked() {
        editButton.setClickable(false);
        startActivity(new Intent(getActivity(), ProfileUserActivity.class));
    }

    @OnClick(R.id.logoutBtn)
    void onLogoutButtonClicked() {
        logoutButton.setClickable(false);
        SessionManager.getInstance().logoutUser();
    }

    private void setButtonsClickable(boolean clickable, Button... buttons) {
        for (Button button : buttons)
            setButtonClickable(button, clickable);
    }

    private void setButtonClickable(Button button, boolean clickable) {
        button.setClickable(clickable);
    }

    private void getChallenges() {
        AndroidNetworking.get(Constants.BASE_URL
                + Constants.EXTENDED_URL_USERCHALLENGES
                + SessionManager.getInstance().getUser().getId() + "/"
                + Constants.EXTENDED_URL_USERCHALLENGES_LIST)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(UserChallenge.class, new ParsedRequestListener<List<UserChallenge>>() {
                    @Override
                    public void onResponse(List<UserChallenge> response) {
                        setChallenges(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println();
                    }
                });
    }

    private void getUser() {
        AndroidNetworking
                .get(Constants.BASE_URL +
                   Constants.EXTENDED_URL_USERS +
                        user.getId())
                .addHeaders(Constants.HEADER_AUTHORIZATION, user.getToken())
                .build()
                .getAsObject(User.class, new ParsedRequestListener<User>() {
                    @Override
                    public void onResponse(User response) {
                        user = response;
                        SessionManager.getInstance().updateUser(response);
                        fillEditTexts();
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println(anError.getErrorBody().toString());
                    }
                });
    }

    private void setUserProfileImageView() {
        Glide.with(this)
                .load(R.drawable.test_image)//todo : load user profile image
                .apply(RequestOptions.bitmapTransform(new CircleTransform(getContext())))
                .into(profileImageView);
    }

    private void fillEditTexts() {
        this.firstnameTextView.setText(user.getFirstname());
        this.lastnameTextView.setText(user.getLastname());
        this.emailTextView.setText(user.getEmail());
    }

    private void setChallenges(List<UserChallenge> challenges) {
        this.challenges = challenges;
        updateView();
    }

    private void updateView() {
        int failed = 0;
        int succeed = 0;
        for (UserChallenge challenge: challenges) {
            if(challenge.getState() == ChallengeState.FAILED.getIntValue()) failed++;
            else if (challenge.getState() == ChallengeState.SUCCEED.getIntValue()) succeed++;
        }

        this.failedTextView.setText(getString(R.string.profile_challenges_failed, failed));
        this.succeedTextView.setText(getString(R.string.profile_challenges_succeed, succeed));
    }
}
