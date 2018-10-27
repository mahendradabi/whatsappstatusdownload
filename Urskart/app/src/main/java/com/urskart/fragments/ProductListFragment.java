package com.urskart.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urskart.MyAbstractFragment;
import com.urskart.R;
import com.urskart.adapter.ProductAdapter;
import com.urskart.adapter.WishListAdapter;
import com.urskart.modal.Product;
import com.urskart.modal.ProductListModal;
import com.urskart.servers.Constant;
import com.urskart.servers.Requestor;
import com.urskart.servers.ServerResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class ProductListFragment extends MyAbstractFragment implements ServerResponse {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.tv_empty)
    AppCompatTextView tv_empty;

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
        loadProductList(category);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //  recyclerView.setAdapter(new ProductAdapter(getActivity(),category));
    }

    private void loadProductList(String category) {
        progressBar.setVisibility(View.VISIBLE);
        Requestor requestor = new Requestor(Constant.GET_PRODUCTS, this);
        requestor.setClassOf(ProductListModal.class);
        Call<String> products = Requestor.apis.getProducts(category);
        requestor.requestSendToServer(products);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void success(Object o, int code) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        switch (code) {
            case Constant.GET_PRODUCTS:
                ProductListModal modal = (ProductListModal) o;
                if (modal != null) {
                    List<Product> products = modal.getProducts();
                    if (products != null && recyclerView != null) {
                        tv_empty.setVisibility(View.GONE);
                        recyclerView.setAdapter(new ProductAdapter(getActivity(), products));
                    }else tv_empty.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }
}
