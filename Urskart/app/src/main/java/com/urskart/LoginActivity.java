package com.urskart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.urskart.sharedpreference.PrefKeys;
import com.urskart.sharedpreference.PreferenceManger;
import com.urskart.social.GooglePlusSign;
import com.urskart.utility.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends GooglePlusSign implements View.OnClickListener {
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.register)
    AppCompatTextView register;

    @BindView(R.id.login)
    AppCompatButton login;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferenceManger.getPreferenceManger().getBoolean(PrefKeys.ISLOGIN)) {
            launchActivity(MainActivity.class);
            finish();
        }
        initViews();
        initListeners();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                userLogin();
                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void userLogin() {
        if (isValidate()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        }
    }

    private boolean isValidate() {
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Enter your email or mobile");
            etEmail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Enter password");
            etPassword.requestFocus();
            return false;
        } else if (!Validator.isEmailValid(etEmail.getText().toString())) {
            etEmail.setError("Enter valid email ID");
            etEmail.requestFocus();
            return false;
        } else
            return true;
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setupGoogleLogin();

    }

    @Override
    public void initListeners() {
        register.setOnClickListener(this);
        login.setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }

    @Override
    protected void updateUI(GoogleSignInAccount account) {
        Log.d("mygoogle", "" + account.toJson());
        if (account != null) {

            String displayName = account.getDisplayName();
            String email = account.getEmail();
            String id = account.getId();
            PreferenceManger preferenceManger = PreferenceManger.getPreferenceManger();
            preferenceManger.setString(PrefKeys.EMAIL, email);
            preferenceManger.setString(PrefKeys.USERNAME, displayName);
            preferenceManger.setBoolean(PrefKeys.ISLOGIN, true);
            launchActivity(MainActivity.class);
            finish();

        }
    }
}
