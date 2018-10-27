package com.urskart.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urskart.MainActivity;
import com.urskart.R;
import com.urskart.fragments.ProductListFragment;
import com.urskart.modal.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    Context mContex;
    MainActivity mainActivity;
    List<Category> categories;
    public CategoryAdapter(Context mContex, List<Category> categories) {
        this.mContex = mContex;
        mainActivity = (MainActivity) mContex;
        this.categories=categories;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category_name, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.categoryName.setText(categories.get(position).getCategoryName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.closeDrawer(Gravity.RIGHT);
                mainActivity.loadFragment(ProductListFragment
                        .getProductListInstance(categories.get(position).getCategoryId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category_name)
        AppCompatTextView categoryName;
        @BindView(R.id.cardview)
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
