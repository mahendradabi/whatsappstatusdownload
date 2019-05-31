package com.mahi.agro.abstractactivity;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public abstract class MyAbstractFragment extends Fragment {

  public abstract void initViews(View view);
  public abstract void initlistners();
  public AppCompatActivity onBackHandler;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

}
