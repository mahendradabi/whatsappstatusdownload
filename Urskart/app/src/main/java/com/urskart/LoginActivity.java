package com.urskart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.gson.Gson;
import com.urskart.modal.AddressModel;
import com.urskart.modal.ResponseModal;
import com.urskart.modal.UserDetailsModel;
import com.urskart.modal.UserModal;
import com.urskart.servers.Constant;
import com.urskart.servers.Requestor;
import com.urskart.servers.ServerResponse;
import com.urskart.sharedpreference.PrefKeys;
import com.urskart.sharedpreference.PreferenceManger;
import com.urskart.social.GooglePlusSign;
import com.urskart.utility.DialogWindow;
import com.urskart.utility.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class LoginActivity extends GooglePlusSign implements View.OnClickListener, ServerResponse {
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

    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.ll_top)
    LinearLayout ll_top;

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

    private void verifyUser() {
        DialogWindow.hideShowTransition(ll_top,progressBar,true);
        Requestor requestor = new Requestor(Constant.LOGIN_CODE, this);
        requestor.setClassOf(UserModal.class);
        Call<String> login = Requestor.apis.login(etEmail.getText().toString(), etPassword.getText().toString());
        requestor.requestSendToServer(login);
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
            verifyUser();
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

    @Override
    public void success(Object o, int code) {
        if (progressBar != null)
            DialogWindow.hideShowTransition(ll_top,progressBar,false);
        switch (code) {
            case Constant.LOGIN_CODE:
                UserModal userModal
                        = (UserModal) o;
                if (userModal != null) {
                    UserDetailsModel userdetail = userModal.getUserdetail();
                    if (userdetail != null) {
                        PreferenceManger preferenceManger = PreferenceManger.getPreferenceManger();
                        preferenceManger.setString(PrefKeys.USERID, userdetail.getUserId());
                        preferenceManger.setBoolean(PrefKeys.ISLOGIN, true);
                        preferenceManger.setString(PrefKeys.USERNAME, userdetail.getFirstName()
                                + " " + userdetail.getLastName());
                        DialogWindow.showToast(this, userModal.getMessage());
                        launchActivity(MainActivity.class);
                        finish();


                    }
                }
                break;
        }

    }

    @Override
    public void error(Object o, int code) {
        if (progressBar != null) {
            DialogWindow.showToast(this, o.toString());
            DialogWindow.hideShowTransition(ll_top,progressBar,false);
        }
    }


}
