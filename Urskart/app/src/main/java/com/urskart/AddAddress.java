package com.urskart;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.urskart.modal.AddressModel;
import com.urskart.sharedpreference.PrefKeys;
import com.urskart.sharedpreference.PreferenceManger;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddAddress extends AppCompatActivity {


    @BindView(R.id.tv_username)
    AppCompatTextView username;
    @BindView(R.id.tv_full_address)
    AppCompatTextView full_address;
    @BindView(R.id.tv_mobile)
    AppCompatTextView tv_mobile;
    @BindView(R.id.change_address)
    AppCompatTextView change_address;


    @BindView(R.id.add_address)
    AppCompatButton add_address;
    @BindView(R.id.btn_continue)
    AppCompatButton btn_continue;

    @BindView(R.id.rl_saved_address)
    RelativeLayout saved;
    @BindView(R.id.rl_address_form)
    RelativeLayout form;

    @BindView(R.id.ll_container)
    LinearLayout root;

    @BindView(R.id.zipcode)
    EditText zipcode;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.state)
    EditText state;
    @BindView(R.id.fname)
    EditText fname;
    @BindView(R.id.mobile)
    EditText mobile;

    PreferenceManger managerInstance;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shipping Address");


        managerInstance = PreferenceManger.getPreferenceManger();

        if (managerInstance != null && managerInstance.getString(PrefKeys.ADDRESS) != null) {
            saved.setVisibility(View.VISIBLE);
            form.setVisibility(View.GONE);
        } else {
            form.setVisibility(View.VISIBLE);
            saved.setVisibility(View.GONE);
        }

        updateUi();

        change_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    TransitionManager.beginDelayedTransition(root);
                saved.setVisibility(View.GONE);
                form.setVisibility(View.VISIBLE);
                updateUi();
            }
        });


        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllValidate()) {
                    Gson gson = new Gson();
                    AddressModel addressModel = new AddressModel();
                    addressModel.setZip_code(zipcode.getText().toString());
                    addressModel.setAddress(address.getText().toString());
                    addressModel.setCity(city.getText().toString());
                    addressModel.setState(state.getText().toString());
                    addressModel.setFname(fname.getText().toString());
                    addressModel.setMobile(mobile.getText().toString());
                    String s = gson.toJson(addressModel);
                    if (s != null) {
                        managerInstance.setString(PrefKeys.ADDRESS, s);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                            TransitionManager.beginDelayedTransition(root);
                        form.setVisibility(View.GONE);
                        saved.setVisibility(View.VISIBLE);
                        updateUi();

                    }
                }
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  startActivity(new Intent(AddAddress.this,ActivityPaymentOptions.class));
                finish();*/
            }
        });
    }

    private boolean isAllValidate() {
        if (TextUtils.isEmpty(address.getText().toString())) {
            address.setError("enter address");
            address.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(zipcode.getText().toString())) {
            zipcode.setError("enter zipcode");
            zipcode.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(city.getText().toString())) {
            city.setError("enter city");
            city.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(state.getText().toString())) {
            state.setError("enter state");
            state.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(fname.getText().toString())) {
            fname.setError("enter your name");
            fname.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(mobile.getText().toString())) {
            mobile.setError("enter mobile number");
            mobile.requestFocus();
            return false;
        } else if (mobile.getText().toString().length() != 10) {
            mobile.setError("enter 10 digit valid mobile number");
            mobile.requestFocus();
            return false;
        } else
            return true;

    }

    private void updateUi() {
        if (managerInstance != null && managerInstance.getString(PrefKeys.ADDRESS) != null) {
            AddressModel addressModel = new Gson().fromJson(managerInstance.getString(PrefKeys.ADDRESS), AddressModel.class);
            if (addressModel != null) {
                zipcode.setText(addressModel.getZip_code());
                address.setText(addressModel.getAddress());
                city.setText(addressModel.getCity());
                state.setText(addressModel.getState());
                fname.setText(addressModel.getFname());
                mobile.setText(addressModel.getMobile());


                username.setText(addressModel.getFname());
                full_address.setText(addressModel.getAddress() + ", "
                        + ", " + addressModel.getCity() + " " + addressModel.getState() + " " + addressModel.getZip_code());
                tv_mobile.setText(addressModel.getMobile());
            }
        } else {
           /* new Requestor(Constant.GET_PROFILE, AddAddress.this)
                    .getProfileDetails(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID));*/
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }


}
