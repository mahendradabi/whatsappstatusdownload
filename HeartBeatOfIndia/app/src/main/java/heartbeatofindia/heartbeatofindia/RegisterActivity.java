package heartbeatofindia.heartbeatofindia;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;
import heartbeatofindia.heartbeatofindia.modals.ResponseModal;
import heartbeatofindia.heartbeatofindia.servers.Constant;
import heartbeatofindia.heartbeatofindia.servers.Requestor;
import heartbeatofindia.heartbeatofindia.servers.ServerResponse;
import retrofit2.Call;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, ServerResponse {
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etLastName)
    EditText etLastName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etCOnfirmPassword;

    @BindView(R.id.register)
    AppCompatButton register;
    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.ll_top)
    ScrollView ll_top;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Registering user ....");
        dialog.setTitle("Register");
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                registerUser();
                break;

        }
    }


    private void registerUser() {
        if (isValidate()) {
            dialog.show();
            Requestor requestor
                    = new Requestor(Constant.REGISTER_CODE, this);
            requestor.setClassOf(ResponseModal.class);
            Call<String> stringCall = Requestor.apis.registerUser(etName.getText().toString(),
                    etLastName.getText().toString(),
                    etEmail.getText().toString(),
                    etMobile.getText().toString(),
                    etPassword.getText().toString());
            requestor.requestSendToServer(stringCall);
        }
    }

    private boolean isValidate() {
        if (TextUtils.isEmpty(etName.getText().toString())) {
            etName.setError("Enter your name");
            etName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etLastName.getText().toString())) {
            etLastName.setError("Enter last name");
            etLastName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etMobile.getText().toString())) {
            etMobile.setError("Enter your mobile number");
            etMobile.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Enter your email");
            etEmail.requestFocus();
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

    @Override
    public void success(Object o, int code) {
        if (dialog != null) dialog.dismiss();
        if (code == Constant.REGISTER_CODE) {
            ResponseModal modal = (ResponseModal) o;
            if (modal != null) {
                if (modal.isStatus()) {
                    Toast.makeText(getApplicationContext(), modal.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), modal.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void error(Object o, int code) {
        if (dialog != null) dialog.dismiss();

    }
}
