package heartbeatofindia.heartbeatofindia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import heartbeatofindia.heartbeatofindia.coreactivity.MyAbstractActivity;
import heartbeatofindia.heartbeatofindia.modals.PostAdModel;
import heartbeatofindia.heartbeatofindia.modals.ResponseModal;
import heartbeatofindia.heartbeatofindia.modals.User;
import heartbeatofindia.heartbeatofindia.modals.UserModal;
import heartbeatofindia.heartbeatofindia.servers.Constant;
import heartbeatofindia.heartbeatofindia.servers.Requestor;
import heartbeatofindia.heartbeatofindia.servers.ServerResponse;
import heartbeatofindia.heartbeatofindia.sharedpreference.PrefKeys;
import heartbeatofindia.heartbeatofindia.sharedpreference.PreferenceManger;
import retrofit2.Call;

public class ActivityAdPost extends MyAbstractActivity implements View.OnClickListener, ServerResponse {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.spinner_option)
    Spinner spinner_option;
    @BindView(R.id.button_pick_image)
    AppCompatButton button_pick_image;
    @BindView(R.id.button_upload_ad)
    AppCompatButton button_upload_ad;
    @BindView(R.id.ad_img)
    AppCompatImageView ad_img;
    @BindView(R.id.et_url)
    AppCompatEditText et_url;

    File file;
    String[] split1;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        initListeners();
    }


    @Override
    public void initViews() {
        setContentView(R.layout.activity_ad_post);
        ButterKnife.bind(this);

        setToolbar(toolbar);
        showBackButton();

        Requestor requestor = new Requestor(Constant.AD, this);
        requestor.setClassOf(PostAdModel.class);
        requestor.requestSendToServer(Requestor.apis.addCategory());

        dialog = new ProgressDialog(this);
        dialog.setTitle("AD");
        dialog.setMessage("Uploading please wait...");

    }

    @Override
    public void initListeners() {

        button_pick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ActivityAdPost.this)
                        .setMaxSize(1)
                        .start();
            }
        });

        button_upload_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (split1 != null)
                    if (et_url.getText().toString().length() == 0) {
                        et_url.setError("Enter ulr");
                        et_url.requestFocus();
                        return;
                    } else if (file == null) {
                        Toast.makeText(getApplicationContext(), "Select ad image", Toast.LENGTH_LONG);
                        return;
                    } else {
                        dialog.show();
                        Requestor requestor = new Requestor(Constant.LOGIN_CODE, ActivityAdPost.this);
                        requestor.setClassOf(ResponseModal.class);
                        Call<String> user_pic = Requestor.apis.inserAd(
                                Requestor.getRequestBody(split1[spinner_option.getSelectedItemPosition()]),
                                Requestor.getRequestBody(et_url.getText().toString()),
                                Requestor.prepareFilePart("user_pic", file));
                        requestor.requestSendToServer(user_pic);
                    }
            }
        });

    }


    @Override
    public void success(Object o, int code) {
        if(dialog!=null)dialog.dismiss();

        switch (code) {
            case Constant.AD:
                PostAdModel model = (PostAdModel) o;
                if (model != null) {
                    String select_id = model.getSelect_id();
                    String select_option = model.getSelect_option();

                    String[] split = select_option.split("&&&");
                    split1 = select_id.split("&&&");


                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ActivityAdPost.this,
                            android.R.layout.simple_list_item_1, split);
                    spinner_option.setAdapter(adapter);


                    for (int i = 0; i < split.length; i++) {
                        Log.d("aaa", split[i] + " " + split1[i]);

                    }

                }
                break;

            case Constant.LOGIN_CODE:
                ResponseModal modal = (ResponseModal) o;
                if (modal != null) {
                    Toast.makeText(getApplicationContext(), modal.getMessage(), Toast.LENGTH_LONG).show();
                    et_url.setText("");
                    file=null;
                    ad_img.setImageURI(null);

                }
                break;
        }

    }

    @Override
    public void error(Object o, int code) {
if(dialog!=null)dialog.dismiss();
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            if (images != null) {
                file = new File(images.get(0).getPath());
                if (file != null)
                    ad_img.setImageURI(Uri.fromFile(file));
            }
            // do your logic here...
        }
    }
}
