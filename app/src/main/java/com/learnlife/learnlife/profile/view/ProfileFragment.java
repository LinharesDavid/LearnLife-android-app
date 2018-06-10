package com.learnlife.learnlife.profile.view;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.reflect.TypeToken;
import com.learnlife.learnlife.Constants;
import com.learnlife.learnlife.LearnLifeApplication;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.SessionManager;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.crosslayers.models.ChallengeState;
import com.learnlife.learnlife.crosslayers.models.UserChallenge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Predicate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    @BindView(R.id.imvProfile) ImageView profileImageView;
    @BindView(R.id.btnBadges) Button badgeButton;
    @BindView(R.id.btnTags) Button tagButton;
    @BindView(R.id.txvFailed) TextView failedTextView;
    @BindView(R.id.txvSucceeded) TextView succeedTextView;

    private List<UserChallenge> challenges;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AndroidNetworking.get(Constants.BASE_URL
                    + Constants.EXTENDED_URL_USERCHALLENGES
                    + SessionManager.getInstance().getUser().getId()
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

                    }
                });
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
