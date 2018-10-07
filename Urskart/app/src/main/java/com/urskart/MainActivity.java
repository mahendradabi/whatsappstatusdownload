package com.urskart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.urskart.adapter.CategoryAdapter;
import com.urskart.fragments.CartFragment;
import com.urskart.fragments.HomeFragment;
import com.urskart.fragments.WishListFragment;
import com.urskart.utility.BottomNavigationViewHelper;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MyAbstractActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation)
    NavigationView navigationView;
    @BindView(R.id.navigation_right)
    NavigationView navigationView_right;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottom_navigation;
    @BindView(R.id.img_category)
    AppCompatImageView img_category;

    RecyclerView categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        ButterKnife.bind(this);
        setToolbar(toolbar);
        setTitle(R.string.urskart);


        setupBottomNavigation();


        View view = navigationView_right.getHeaderView(0);
        categoryList = view.findViewById(R.id.recyclerView);
        categoryList.setLayoutManager(new LinearLayoutManager(this));
        categoryList.setAdapter(new CategoryAdapter(MainActivity.this));

        loadFragment(new HomeFragment());


    }

    private void setupBottomNavigation() {
        BottomNavigationViewHelper.disableShiftMode(bottom_navigation);
        BottomNavigationViewHelper.removeTextLabel(bottom_navigation, R.id.action_menu);
        BottomNavigationViewHelper.removeTextLabel(bottom_navigation, R.id.action_home);
        BottomNavigationViewHelper.removeTextLabel(bottom_navigation, R.id.action_wishlist);
        BottomNavigationViewHelper.removeTextLabel(bottom_navigation, R.id.action_cart);


    }

    @Override
    public void initListeners() {
        navigationView.setNavigationItemSelectedListener(this);
        navigationView_right.setNavigationItemSelectedListener(this);

        img_category.setOnClickListener(this);

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                handleBottomNavigation(item.getItemId());
                return true;
            }
        });
    }

    private void handleBottomNavigation(int itemId) {
        switch (itemId) {
            case R.id.action_menu:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.action_wishlist:
                loadFragment(new WishListFragment());
                setTitle(R.string.wishlist);
                break;

            case R.id.action_cart:
                loadFragment(new CartFragment());
                setTitle(R.string.yourbag);
                break;

            case R.id.action_home:
                loadFragment(new HomeFragment());
                setTitle(R.string.urskart);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.mycart:
                bottom_navigation.setSelectedItemId(R.id.action_cart);
                closeDrawer(Gravity.START);
                break;

            case R.id.favorite:
                bottom_navigation.setSelectedItemId(R.id.action_wishlist);
                closeDrawer(Gravity.START);
                break;

            case R.id.myprofile:
                startActivity(new Intent(this, MyProfileActivity.class));
                closeDrawer(Gravity.START);

                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.user_profile)
            startActivity(new Intent(this, MyProfileActivity.class));
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_category:
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                break;
        }
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.flayout, fragment)
                .commit();
    }

    public void loadFragmentBack(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.flayout, fragment)
                .addToBackStack("home")
                .commit();
    }

    public void closeDrawer(int gravity) {
        mDrawerLayout.closeDrawer(gravity);
    }
}
