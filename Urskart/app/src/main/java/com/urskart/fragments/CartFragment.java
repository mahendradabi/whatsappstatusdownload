package com.urskart.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urskart.MyAbstractFragment;
import com.urskart.R;
import com.urskart.adapter.CartAdapter;
import com.urskart.adapter.WishListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartFragment extends MyAbstractFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cart,container,false);
        initViews(view);
        initListeners();
       return view;
    }

    @Override
    public void initViews(View view) {
        ButterKnife.bind(this,view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CartAdapter());
    }

    @Override
    public void initListeners() {

    }
}