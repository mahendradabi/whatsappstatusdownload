package com.urskart.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urskart.ProductDetailsActivity;
import com.urskart.R;
import com.urskart.modal.Product;
import com.urskart.servers.Constant;
import com.urskart.utility.Utility;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    Context mContex;
    List<Product> products;

    public ProductAdapter(Context mContex, List<Product> products) {
        this.products = products;
        this.mContex = mContex;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_row, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Product product = products.get(position);
        holder.tv_product_name.setText(product.getProductTitle());
        holder.tv_price.setText(Constant.CURRENCY+" "+product.getProductPrice());
        holder.tv_discount.setText(Constant.CURRENCY+" "+product.getProductDiscount());
        Utility.loadImage(product.getProductImage(),holder.img_product);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContex.startActivity(new Intent(mContex, ProductDetailsActivity.class).putExtra("id",products.get(position).getProductId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_product_name)
        AppCompatTextView tv_product_name;
        @BindView(R.id.tv_price)
        AppCompatTextView tv_price;
        @BindView(R.id.tv_discount)
        AppCompatTextView tv_discount;
        @BindView(R.id.cardview)
        CardView cardView;
        @BindView(R.id.img_product)
        AppCompatImageView img_product;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
