package com.learnlife.learnlife.profile.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.communityChallenge.CommunityChallengeActivity;
import com.learnlife.learnlife.communityChallenge.CommunityChallengeAdapter;
import com.learnlife.learnlife.crosslayers.models.ChallengeState;
import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.crosslayers.models.UserChallenge;
import com.learnlife.learnlife.crosslayers.utils.CircleTransform;
import com.learnlife.learnlife.profile.view.profile.IProfileView;
import com.learnlife.learnlife.profile.view.profile.ProfilePresenter;
import com.learnlife.learnlife.tags.view.TagActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends Fragment implements IProfileView {

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
    @BindView(R.id.btnChallenges) Button challengesButton;

    private List<UserChallenge> challenges;
    private User user = SessionManager.getInstance().getUser();
    private ProfilePresenter presenter;

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
        setButtonsClickable(true, badgeButton, logoutButton, tagButton, challengesButton);
        editButton.setClickable(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.presenter = new ProfilePresenter(this);
        presenter.getUser(user);
        presenter.getUserChallenges(user);
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

    @OnClick(R.id.btnChallenges)
    void onChallengesButtonClicked() {
        challengesButton.setClickable(false);
        Intent intent = new Intent(getActivity(), CommunityChallengeActivity.class);
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

    @Override
    public void onUserRetrived(User user) {
        SessionManager.getInstance().updateUser(user);
        this.user = user;
        fillEditTexts();
    }

    @Override
    public void onUserChallengeRertieve(List<UserChallenge> userChallenges) {
        setChallenges(userChallenges);
    }
}
