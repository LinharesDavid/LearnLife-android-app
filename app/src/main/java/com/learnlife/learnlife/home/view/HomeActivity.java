package com.learnlife.learnlife.home.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.TextView;

import com.learnlife.learnlife.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    /***********************************************************
    *  Attributes
    **********************************************************/
    @BindView(R.id.txvTodayDate)
    public TextView txvTodayDate;


    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        txvTodayDate.setText("Mercredi 4 Avril 2018");
    }
}
