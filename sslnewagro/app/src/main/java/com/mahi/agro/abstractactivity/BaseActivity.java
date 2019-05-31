package com.mahi.agro.abstractactivity;


import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


public class BaseActivity extends AppCompatActivity {


    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void showBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;

    }

    /*
     * launch activity withous inent data
     * */
    public void launchActivity(Class classToLaunch) {
        startActivity(new Intent(this, classToLaunch));
    }


    public void loadFragment(Fragment fragment, int containerId) {
        getSupportFragmentManager().beginTransaction().replace(containerId, fragment).commit();
    }

    public void loadFragment(Fragment fragment, int containerId, String stack) {
        getSupportFragmentManager().beginTransaction().replace(containerId, fragment).addToBackStack(stack).commit();

    }


}
