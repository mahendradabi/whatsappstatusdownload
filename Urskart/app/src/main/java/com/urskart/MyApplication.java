package com.urskart;

import android.app.Application;
;import com.urskart.sharedpreference.PreferenceManger;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManger.initPreference(getApplicationContext());

    }
}
