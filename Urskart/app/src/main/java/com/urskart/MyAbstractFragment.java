package com.urskart;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by xyz on 26-03-2018.
 */

public abstract class MyAbstractFragment extends Fragment {
    public abstract void initViews(View view);
    public abstract void initListeners();


}
