package com.ahaar.driver.abstractactivity;

import android.app.Application;



public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManger.initPreference(getApplicationContext());

    }

}
