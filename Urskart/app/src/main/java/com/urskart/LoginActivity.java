package com.urskart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.urskart.utility.Validator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.register)
    AppCompatTextView register;

    @BindView(R.id.login)
    AppCompatButton login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        register.setOnClickListener(this);
        login.setOnClickListener(this);

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
}
