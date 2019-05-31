package com.mahi.agro.abstractactivity;

import android.content.Context;
import android.content.SharedPreferences;

public final class PreferenceManger  {
  public static PreferenceManger preferenceManger;
  public static SharedPreferences sharedPreferences;

  public static PreferenceManger getPreferenceManger() {
    return preferenceManger;
  }

  public static void initPreference(Context context) {
    if (preferenceManger == null) {
      sharedPreferences = context.getSharedPreferences(PrefKeys.PREFERENCENAME, Context.MODE_PRIVATE);
      preferenceManger = new PreferenceManger();
    }
  }

  public void setString(String key, String value) {
    sharedPreferences.edit().putString(key, value).commit();
  }

  public String getString(String key) {
    return sharedPreferences.getString(key, null);
  }

  public void setInt(String key, int value) {
    sharedPreferences.edit().putInt(key, value).commit();
  }

  public int getInt(String key) {
    return sharedPreferences.getInt(key,0);
  }

  public void setBoolean(String key, boolean b){
    sharedPreferences.edit().putBoolean(key,b).commit();
  }

  public boolean getBoolean(String key) {
    return  sharedPreferences.getBoolean(key,false);
  }

  public void resetPreference()
  {
    sharedPreferences.edit().clear().commit();
  }

  public void clearSession()
  {
    sharedPreferences.edit().clear().commit();
  }

}
