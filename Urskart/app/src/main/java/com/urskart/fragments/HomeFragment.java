package com.urskart.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urskart.MyAbstractFragment;
import com.urskart.R;
import com.urskart.adapter.CartAdapter;
import com.urskart.adapter.SliderPagerAdapter;
import com.urskart.customviews.MyPageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends MyAbstractFragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.piv)
    MyPageIndicator piv;

    SliderPagerAdapter viewPagerAdapter;

    Integer[] sliderImages={R.drawable.dummya,R.drawable.dummy,R.drawable.dummya,R.drawable.dummy};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        initViews(view);
        initListeners();
       return view;
    }

    @Override
    public void initViews(View view) {
        ButterKnife.bind(this,view);
        viewPagerAdapter = new SliderPagerAdapter(getActivity(),sliderImages);
        piv.setIndicator(viewPagerAdapter.getCount());
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(viewPagerAdapter);
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
}
