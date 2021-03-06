package com.urskart.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urskart.BannerModel;
import com.urskart.MainActivity;
import com.urskart.MyAbstractFragment;
import com.urskart.R;
import com.urskart.adapter.CartAdapter;
import com.urskart.adapter.CategoryAdapter;
import com.urskart.adapter.SliderPagerAdapter;
import com.urskart.customviews.MyPageIndicator;
import com.urskart.modal.Category;
import com.urskart.modal.CategoryList;
import com.urskart.servers.Constant;
import com.urskart.servers.Requestor;
import com.urskart.servers.ServerResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class HomeFragment extends MyAbstractFragment implements ViewPager.OnPageChangeListener, View.OnClickListener,
        ServerResponse {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.piv)
    MyPageIndicator piv;
    @BindView(R.id.btn_shop_now)
    AppCompatButton btn_shop_now;

    @BindView(R.id.recyclerView)
    RecyclerView categoryList;

    SliderPagerAdapter viewPagerAdapter;
    List<Category> sliderImages;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        initListeners();
        return view;
    }

    @Override
    public void initViews(View view) {
        ButterKnife.bind(this, view);

        categoryList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        loadBanners();
        loadCategory();
    }


    private void setupViewpager() {
        if (sliderImages != null && viewPager != null) {
            viewPagerAdapter = new SliderPagerAdapter(getActivity(), sliderImages);
            piv.setIndicator(viewPagerAdapter.getCount());
            viewPager.addOnPageChangeListener(this);
            viewPager.setAdapter(viewPagerAdapter);
            btn_shop_now.setOnClickListener(this);
        }
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_shop_now:
                ((MainActivity) getActivity()).loadFragmentBack(
                        ProductListFragment.getProductListInstance("Shop Now")
                );
                break;
        }
    }

    @Override
    public void success(Object o, int code) {
        switch (code) {
            case Constant.GET_BANNERS:
                BannerModel bannerModel = (BannerModel) o;
                if (bannerModel != null) {
                    sliderImages = bannerModel.getBannerList();
                    if (sliderImages != null) {
                        setupViewpager();
                    }
                }
                break;

            case Constant.GET_CATEGORY:
                CategoryList catModal = (CategoryList) o;
                if (catModal != null && catModal.getCategories() != null && categoryList != null) {
                    categoryList.setAdapter(new CategoryAdapter(getActivity(), catModal.getCategories(),CategoryAdapter.CATEGORYVIEW_HOME));
                }

                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }
    private void loadCategory()
    {
        Requestor requestor=new Requestor(Constant.GET_CATEGORY,this);
        requestor.setClassOf(CategoryList.class);
        Call<String> category = Requestor.apis.getCategory();
        requestor.requestSendToServer(category);
    }

    private void loadBanners() {
        Requestor requestor = new Requestor(Constant.GET_BANNERS, this);
        requestor.setClassOf(BannerModel.class);
        Call<String> banners = Requestor.apis.getBanners();
        requestor.requestSendToServer(banners);

    }
}
