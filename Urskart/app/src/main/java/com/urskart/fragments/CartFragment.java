package com.urskart.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.urskart.AddAddress;
import com.urskart.MyAbstractFragment;
import com.urskart.R;
import com.urskart.adapter.CartAdapter;
import com.urskart.adapter.WishListAdapter;
import com.urskart.myinterface.OnEmptyList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartFragment extends MyAbstractFragment implements OnEmptyList {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.ll_empty)
    LinearLayout ll_empty;
    @BindView(R.id.btn_buy_now)
    AppCompatButton btn_buy_now;
    @BindView(R.id.nestedScroll)
    NestedScrollView mNestedScrollView;
    LinearLayoutManager mLayoutManager;

    int visibleItemCount;
    int totalItemCount;
    int pastVisiblesItems;

    int pno=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        initViews(view);
        initListeners();
        return view;
    }

    @Override
    public void initViews(View view) {
        ButterKnife.bind(this, view);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(new CartAdapter(this));
    }

    @Override
    public void initListeners() {
        btn_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddAddress.class));
            }
        });


        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {

                        visibleItemCount = mLayoutManager.getChildCount();
                        totalItemCount = mLayoutManager.getItemCount();
                        pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                        if (isLoadData()) {
                            pno++;
                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                Log.d("loading", "inserting data..."+pno);
//                        Load Your Data
                            }
                        }
                    }
                }
            }
        });

    }

    private boolean isLoadData() {
        return true;
    }

    @Override
    public void onListEmpty() {
        ll_empty.setVisibility(View.VISIBLE);
    }
}
