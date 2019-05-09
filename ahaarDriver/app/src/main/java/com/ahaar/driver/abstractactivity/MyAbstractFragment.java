package com.ahaar.driver.abstractactivity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public abstract class MyAbstractFragment extends Fragment {

  public abstract void initViews(View view);
  public abstract void initlistners();
  public AppCompatActivity onBackHandler;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

}
