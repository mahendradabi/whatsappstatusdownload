package com.money.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.money.app.R;
import com.money.app.ViewCurrentTradeActivity;

public class SelectedCurrencyAdapter extends RecyclerView.Adapter<SelectedCurrencyAdapter.MyViewHolder> {

    Context mContex;

    OnStopListner onStopListner;

    public interface OnStopListner {
        public void onStopClick(String currency);
    }

    public void setOnStopListner(OnStopListner onStopListner) {
        this.onStopListner = onStopListner;
    }

    public SelectedCurrencyAdapter(Context mContex) {
        this.mContex = mContex;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_selected_currency, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton button_stop;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            button_stop = itemView.findViewById(R.id.button_stop);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContex.startActivity(new Intent(mContex, ViewCurrentTradeActivity.class));
                }
            });

            button_stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onStopListner != null)
                        onStopListner.onStopClick("Stop clicked " + getAdapterPosition());
                }
            });
        }
    }
}
