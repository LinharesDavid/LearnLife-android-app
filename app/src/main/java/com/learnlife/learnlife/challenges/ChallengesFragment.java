package com.learnlife.learnlife.challenges;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnlife.learnlife.R;

public class ChallengesFragment extends Fragment {

    public ChallengesFragment(){
        //
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_challenges, container, false);
    }
}
