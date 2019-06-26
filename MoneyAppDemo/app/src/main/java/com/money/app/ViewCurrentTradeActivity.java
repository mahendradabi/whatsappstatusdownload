package com.money.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.money.app.adapter.SelectedCurrencyAdapter;

public class ViewCurrentTradeActivity extends AppCompatActivity {
    TextView tvup, tvdonw, tvprice, tv_time, tv_currency;
    LocalBroadcastManager localBroadcastManager;
    BroadcastReceiver receiver;
    String pair;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trade);

        tvdonw = findViewById(R.id.tv_down);
        tvup = findViewById(R.id.tv_up);
        tvprice = findViewById(R.id.tv_price);
        tv_time = findViewById(R.id.tv_time);
        tv_currency = findViewById(R.id.tv_currency);

        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getStringExtra("pair").equals(pair)) {
                    tvprice.setText(intent.getStringExtra("price"));
                    tvup.setText(intent.getDoubleExtra("up", 0)+"");
                    tvdonw.setText(intent.getDoubleExtra("down", 0)+"");
                    tv_time.setText(convertTime(intent.getLongExtra("time", 0)));
                }
            }
        };


        if (getIntent().hasExtra("pair")) {
            pair = getIntent().getStringExtra("pair");
            tv_currency.setText("Currency: "+pair);
           /* Intent startIntent = new Intent(ViewCurrentTradeActivity.this, MyService.class);
            startIntent.setAction(Constants.ACTION.START_ACTION);
            startIntent.putExtra("pair", pair);
            startService(startIntent);*/

            Intent startIntent = new Intent(ViewCurrentTradeActivity.this, MyService.class);
            startIntent.setAction(Constants.ACTION.START_ACTION);
            startIntent.putExtra("pair", pair);
            startService(startIntent);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null)
            localBroadcastManager.unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (receiver != null)
            localBroadcastManager.registerReceiver(receiver, new IntentFilter("mydata"));
    }


    private String convertTime(long durationSeconds) {
        return String.format("%02d:%02d:%02d", durationSeconds / 3600,
                (durationSeconds % 3600) / 60, (durationSeconds % 60));
    }
}
