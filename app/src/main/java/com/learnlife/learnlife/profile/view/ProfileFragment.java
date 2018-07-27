package com.learnlife.learnlife.profile.view;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.util.IOUtils;
import com.google.gson.Gson;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.communityChallenge.CommunityChallengeActivity;
import com.learnlife.learnlife.crosslayers.models.ChallengeState;
import com.learnlife.learnlife.crosslayers.models.User;
import com.learnlife.learnlife.crosslayers.models.UserChallenge;
import com.learnlife.learnlife.crosslayers.utils.CircleTransform;
import com.learnlife.learnlife.profile.view.profile.IProfileView;
import com.learnlife.learnlife.profile.view.profile.ProfilePresenter;
import com.learnlife.learnlife.tags.view.TagActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public static final int PICK_IMAGE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
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

    @OnClick(R.id.imvProfile)
    void onImageViewProfileClicked() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            if (data == null) return;
            Glide.with(this)
                    .load(data.getData())
                    .apply(RequestOptions.bitmapTransform(new CircleTransform(getContext())))
                    .into(profileImageView);
            File file = null;
            try {
                file = getFileFromUri(data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(file != null)
                presenter.setUserThumnail(user, file);
        }
    }

    private File getFileFromUri(Uri uri) throws IOException {
        InputStream inputStream;
        File file;

        inputStream = getContext().getContentResolver().openInputStream(uri);
        file = File.createTempFile("image", null, getContext().getCacheDir());
        FileOutputStream outputStream = new FileOutputStream(file);
        IOUtils.copyStream(inputStream, outputStream);
        inputStream.close();
        outputStream.close();

        return file;
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
                .load(Constants.BASE_URL + user.getthumbnailUrl())
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
        setUserProfileImageView();
    }

    @Override
    public void onUserChallengeRertieve(List<UserChallenge> userChallenges) {
        setChallenges(userChallenges);
    }

    @Override
    public void onUserThumbnailSucceed(User user) {
        SessionManager.getInstance().updateUser(user);
    }
}
