package com.urskart.utility;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.urskart.R;


public class DialogWindow {


    public static ProgressDialog showProgressDialog(Context context, String title, String message) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        return dialog;

    }



/*
    public static AlertDialog showForgotDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Forgot Password");
        // Set up the input
        final EditText input = new EditText(context);
      //  input.setHintTextColor(context.getResources().getColor(R.color.black));
        input.setHint("Enter email address");
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();

                if (Validator.isEmailValid(m_Text)) {
                  //  Utility.showToast(context,"Password reset link sent to your registered email id");
                    final AlertDialog progressDialog = showForgotDialog(context);
               RetrofitClient.getMyClient().create(Apis.class)
                            .forgotPassword(m_Text).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                            progressDialog.dismiss();
                            if (response.code() == 200 && response.body() != null)
                                Utility.showToast(context, response.body().getMessage());

                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Utility.showToast(context, t.getMessage());
                        }
                    });
                }
                else  Utility.showToast(context,"Email not valid.");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.create();
    }*/


    public static void rateApp(Context mContex) {
        Uri uri = Uri.parse("market://details?id=" + mContex.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            mContex.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            mContex.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + mContex.getPackageName())));
        }
    }

    public static void shareApp(Context mContext) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Download " + mContext.getString(R.string.app_name) + " https://play.google.com/store/apps/details?id=" + mContext.getPackageName());
        sendIntent.setType("text/plain");
        mContext.startActivity(sendIntent);
    }

    public static void openShareIntent(Context mContex, String message) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        ComponentName componentName = sendIntent.resolveActivity(mContex.getPackageManager());
        if (componentName != null)
            mContex.startActivity(sendIntent);
    }
}
