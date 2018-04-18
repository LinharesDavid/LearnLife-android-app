package com.learnlife.learnlife.crosslayers.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.learnlife.learnlife.R;

import butterknife.OnClick;

public class LearnLifeButton extends AppCompatImageButton {

    public LearnLifeButton(Context context) {
        super(context);
    }

    public LearnLifeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LearnLifeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @OnClick
    public void onClick() {
        Log.e("kohijl", "kjhzdkhvlskdjhvmqsdhjv");
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.button_bounce);
        this.startAnimation(anim);
        Toast.makeText(getContext(), "bonjour", Toast.LENGTH_LONG).show();
    }
}
