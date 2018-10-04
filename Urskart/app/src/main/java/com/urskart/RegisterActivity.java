package com.urskart;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etDob)
    EditText etDob;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etCOnfirmPassword;

    @BindView(R.id.register)
    AppCompatButton register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);

        etDob.setKeyListener(null);
        etDob.setOnClickListener(this);

        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                registerUser();
                break;
            case R.id.etDob:
                selectDOB();
                break;
        }
    }

    private void selectDOB() {
        Calendar calendar=Calendar.getInstance();

        DatePickerDialog datePickerDialog=  new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
               etDob.setText(String.format("%02d",dayOfMonth)+"-"+String.format("%02d",month)+"-"+year);

           }
       },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

     datePickerDialog.show();

    }

    private void registerUser() {
        if (isValidate())
        {

        }
    }

    private boolean isValidate() {
        if (TextUtils.isEmpty(etName.getText().toString())) {
            etName.setError("Enter your name");
            etName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etMobile.getText().toString())) {
            etMobile.setError("Enter your mobile number");
            etMobile.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Enter your email");
            etEmail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etDob.getText().toString())) {
            etDob.setError("Enter your date of birth");
            etDob.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Enter password");
            etPassword.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etCOnfirmPassword.getText().toString())) {
            etCOnfirmPassword.setError("Enter your confirm Password");
            etCOnfirmPassword.requestFocus();
            return false;
        } else
            return true;
    }
}
