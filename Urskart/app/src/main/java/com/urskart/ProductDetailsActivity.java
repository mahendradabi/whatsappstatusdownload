package com.urskart;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.urskart.modal.ResponseModal;
import com.urskart.servers.Constant;
import com.urskart.servers.Requestor;
import com.urskart.servers.ServerResponse;
import com.urskart.sharedpreference.PrefKeys;
import com.urskart.sharedpreference.PreferenceManger;
import com.urskart.utility.DialogWindow;
import com.urskart.utility.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class ProductDetailsActivity extends MyAbstractActivity
        implements ViewPager.OnPageChangeListener, View.OnClickListener, ServerResponse {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.piv)
    MyPageIndicator piv;

    @BindView(R.id.btnAddToFavorite)
    AppCompatButton btnAddToFavorite;


    @BindView(R.id.btnAddCart)
    AppCompatButton btnAddCart;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String product_id;

    SliderPagerAdapter viewPagerAdapter;
    Integer[] sliderImages = {R.drawable.dummy, R.drawable.dummya, R.drawable.dummya, R.drawable.dummy};

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


        //get the product id to get product details
        Intent intent = getIntent();
        if (intent != null)
            if (intent.hasExtra("id"))
                product_id = intent.getStringExtra("id");


       /* viewPagerAdapter = new SliderPagerAdapter(this,sliderImages);
        piv.setIndicator(viewPagerAdapter.getCount());
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(viewPagerAdapter);*/


        btnAddCart.setOnClickListener(this);

    }

    @Override
    public void initListeners() {
        btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavorite();
            }
        });
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
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.share)
            DialogWindow.openShareIntent(this, "Product Name \n Download app urskar.com");
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnAddCart:
                BottomSheetDialog bottomSheetDialog
                        = new BottomSheetDialog(this);
                bottomSheetDialog.setContentView(R.layout.item_popup_options);
                bottomSheetDialog.show();
                break;
        }
    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case Constant.ADD_FAVORITE:
                ResponseModal responseModal = (ResponseModal) o;
                if (responseModal != null) {
                    DialogWindow.showToast(this, responseModal.getMessage());
                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }

    private void addToFavorite() {
        Requestor requestor = new Requestor(Constant.ADD_FAVORITE, this);
        requestor.setClassOf(ResponseModal.class);
        Call<String> stringCall = Requestor.apis.addToFavorite(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID),
                product_id);
        requestor.requestSendToServer(stringCall);
    }

    private void addToCart() {
        Requestor requestor = new Requestor(Constant.ADD_CART, this);
        requestor.setClassOf(ResponseModal.class);
        Call<String> stringCall = Requestor.apis.addToCart(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERID),
                product_id);
        requestor.requestSendToServer(stringCall);
    }
}
