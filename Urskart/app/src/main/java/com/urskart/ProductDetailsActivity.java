package com.urskart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.urskart.adapter.SliderPagerAdapter;
import com.urskart.customviews.MyPageIndicator;
import com.urskart.utility.DialogWindow;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsActivity extends MyAbstractActivity
implements ViewPager.OnPageChangeListener, View.OnClickListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.piv)
    MyPageIndicator piv;


    @BindView(R.id.btnAddCart)
    AppCompatButton btnAddCart;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    SliderPagerAdapter viewPagerAdapter;
    Integer[] sliderImages={R.drawable.dummy,R.drawable.dummya,R.drawable.dummya,R.drawable.dummy};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        setToolbar(toolbar);
        showBackButton();
        setTitle("Product Decription");

        viewPagerAdapter = new SliderPagerAdapter(this,sliderImages);
        piv.setIndicator(viewPagerAdapter.getCount());
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(viewPagerAdapter);
        btnAddCart.setOnClickListener(this);

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        piv.setSelectedPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.share)
            DialogWindow.openShareIntent(this,"Product Name \n Download app urskar.com");
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu,menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

            case R.id.btnAddCart:
               BottomSheetDialog bottomSheetDialog
                       =new BottomSheetDialog(this);
               bottomSheetDialog.setContentView(R.layout.item_popup_options);
               bottomSheetDialog.show();
                break;
        }
    }
}
