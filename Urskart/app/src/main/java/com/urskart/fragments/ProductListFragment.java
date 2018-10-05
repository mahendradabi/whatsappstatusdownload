package com.urskart.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urskart.MyAbstractFragment;
import com.urskart.R;
import com.urskart.adapter.ProductAdapter;
import com.urskart.adapter.WishListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListFragment extends MyAbstractFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;

    public static Fragment getProductListInstance(String categoryName) {
        Bundle bundle = new Bundle();
        bundle.putString("category", categoryName);
        Fragment fragment = new ProductListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_common_recylerview, container, false);
        initViews(view);
        initListeners();
        return view;
    }

    @Override
    public void initViews(View view) {
        ButterKnife.bind(this, view);

        String category = getArguments().getString("category");
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new ProductAdapter(getActivity(),category));
    }

    @Override
    public void initListeners() {

    }
}
