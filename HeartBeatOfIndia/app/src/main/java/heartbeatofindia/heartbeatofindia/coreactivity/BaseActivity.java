package heartbeatofindia.heartbeatofindia.coreactivity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;



/**
 * Created by xyz on 22-02-2018.
 */

public class BaseActivity extends AppCompatActivity {
    /*
     * set the default toolbar with back button
     *
     * */


    public void setToolbar(android.support.v7.widget.Toolbar toolbar) {
        setSupportActionBar(toolbar);
//      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void showBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*
     * override method to back button
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void launchActivity(Intent intent) {
        if (intent == null)
            throw new RuntimeException("intent is null!");
        this.startActivity(intent);
    }

    public void launchActivity(Class<?> launchClass) {
        if (launchClass==null)
            throw new RuntimeException("launchClass is null!" );
        this.startActivity(new Intent(this, launchClass));
    }


    public void loadFragment(Fragment fragment,int containerId) {
        getSupportFragmentManager()
                .beginTransaction().replace(containerId, fragment)
                .commit();
    }

    public void loadFragmentLayoutBack(Fragment fragment,int containerId,boolean addToBack)
    {
        FragmentTransaction fmt = getSupportFragmentManager().beginTransaction();
        if (addToBack)
            fmt.addToBackStack("subcategory");


        fmt.replace(containerId, fragment)
                .commit();
    }

    public void loadFragmentBack(Fragment fragment,int containerId) {
        getSupportFragmentManager()
                .beginTransaction().replace(containerId, fragment)
                .addToBackStack("home")
                .commit();
    }

}
