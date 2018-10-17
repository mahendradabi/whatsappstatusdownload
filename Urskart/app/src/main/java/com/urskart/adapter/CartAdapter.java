package com.urskart.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.urskart.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
List<Integer> items=new ArrayList<>();

    public CartAdapter()
    {
        items.add(1);
        items.add(1);
        items.add(1);
        items.add(1);
        items.add(1);
        items.add(1);
        items.add(1);
        items.add(1);
        items.add(1);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cart_row,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.ll_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_remove)
        LinearLayout ll_remove;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private void removeItem(int position)

    {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,getItemCount());
    }
}
