package heartbeatofindia.heartbeatofindia;

import android.app.ProgressDialog;
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
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;
import heartbeatofindia.heartbeatofindia.coreactivity.MyAbstractActivity;
import heartbeatofindia.heartbeatofindia.modals.User;
import heartbeatofindia.heartbeatofindia.modals.UserModal;
import heartbeatofindia.heartbeatofindia.servers.Constant;
import heartbeatofindia.heartbeatofindia.servers.Requestor;
import heartbeatofindia.heartbeatofindia.servers.ServerResponse;
import heartbeatofindia.heartbeatofindia.sharedpreference.PrefKeys;
import heartbeatofindia.heartbeatofindia.sharedpreference.PreferenceManger;
import retrofit2.Call;

public class LoginActivity extends MyAbstractActivity implements View.OnClickListener, ServerResponse {
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.register)
    AppCompatTextView register;

    @BindView(R.id.login)
    AppCompatButton login;

    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.ll_top)
    LinearLayout ll_top;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initViews();
        initListeners();
    }

    private void verifyUser() {
        dialog.show();
        Requestor requestor
                = new Requestor(Constant.LOGIN_CODE, this);
        requestor.setClassOf(UserModal.class);
        Call<String> stringCall = Requestor.apis.userLoin(etEmail.getText().toString(), etPassword.getText().toString());
        requestor.requestSendToServer(stringCall);
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


        dialog = new ProgressDialog(this);
        dialog.setMessage("Login in process...");
        dialog.setTitle("Login");

        ButterKnife.bind(this);

    }

    @Override
    public void initListeners() {
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }


    @Override
    public void success(Object o, int code) {
        if (dialog != null) dialog.dismiss();
        switch (code) {
            case Constant.LOGIN_CODE:
                UserModal userModal = (UserModal) o;
                if (userModal != null) {
                    if (userModal.isStatus()) {
                        if (userModal.getUserList() != null) {

                            User user = userModal.getUserList().get(0);
                            PreferenceManger manger
                                    = PreferenceManger.getPreferenceManger();
                            manger.setBoolean(PrefKeys.ISLOGIN, true);
                            manger.setString(PrefKeys.EMAIL, user.getEmail());
                            manger.setString(PrefKeys.USERNAME, user.getFname() + " " + user.getLname());
                            manger.setString(PrefKeys.MOBILE, user.getPhone());
                            launchActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), userModal.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

    }

    @Override
    public void error(Object o, int code) {
        if (dialog != null) dialog.dismiss();

    }


}
