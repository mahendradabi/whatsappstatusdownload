package com.mahi.agro;


import android.os.Bundle;

import com.mahi.agro.abstractactivity.MyAbstractActivity;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends MyAbstractActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
        listners();
    }

    @Override
    public void initview() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @Override
    public void listners() {

    }

    @OnClick(R.id.login_button)
    public void openHome() {
        launchActivity(MainActivity.class);
    }
}
