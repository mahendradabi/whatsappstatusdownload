package com.urskart.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.urskart.R;
import com.urskart.myinterface.OnEmptyList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {


    List<Integer> list;
    OnEmptyList onEmptyList;

    public WishListAdapter(OnEmptyList onEmptyList) {
        this.onEmptyList = onEmptyList;
        list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wishlist, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeProduct(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_remove)
        AppCompatImageView img_remove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void removeProduct(int position) {
        try {
            list.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
            if (getItemCount() == 0)
                onEmptyList.onListEmpty();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
