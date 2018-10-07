package com.urskart;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyProfileActivity extends MyAbstractActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        setToolbar(toolbar);
        setTitle(getString(R.string.profile));
        showBackButton();
    }

    @Override
    public void initListeners() {

    }
}
