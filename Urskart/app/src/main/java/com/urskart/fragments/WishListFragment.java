package com.urskart.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.urskart.MyAbstractFragment;
import com.urskart.R;
import com.urskart.adapter.WishListAdapter;
import com.urskart.myinterface.OnEmptyList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WishListFragment extends MyAbstractFragment implements OnEmptyList {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        initViews(view);
        initListeners();
        return view;
    }

    @Override
    public void initViews(View view) {
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new WishListAdapter(this));
    }

    @Override
    public void initListeners() {

    }


    @Override
    public void onListEmpty() {
        ll_empty.setVisibility(View.VISIBLE);
    }
}
