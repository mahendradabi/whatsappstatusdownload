package com.money.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.money.app.models.Ask;
import com.money.app.models.DataModel;
import com.money.app.models.Price;
import com.money.app.servers.Constant;
import com.money.app.servers.Requestor;
import com.money.app.servers.ServerResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {
    TextView tvup, tvdonw, tvprice, tv_time;
    TextView tvupTwo, tvdonwTwo, tvpriceTwo, tv_timeTwo;

    LocalBroadcastManager localBroadcastManager;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button startButton = findViewById(R.id.button1);
        Button stopButton = findViewById(R.id.button2);
        tvdonw = findViewById(R.id.tv_down);
        tvup = findViewById(R.id.tv_up);
        tvprice = findViewById(R.id.tv_price);
        tv_time = findViewById(R.id.tv_time);


        final Button startButtonTwo = findViewById(R.id.button1_two);
        Button stopButtonTwo = findViewById(R.id.button2_two);
        tvdonwTwo = findViewById(R.id.tv_down_two);
        tvupTwo = findViewById(R.id.tv_up_two);
        tvpriceTwo = findViewById(R.id.tv_price_two);
        tv_timeTwo = findViewById(R.id.tv_time_two);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent startIntent = new Intent(MainActivity.this, MyService.class);
                startIntent.setAction(Constants.ACTION.START_ACTION);
                startIntent.putExtra("pair", "USD_JPY");
                startService(startIntent);
                startButton.setEnabled(false);

            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopIntent = new Intent(MainActivity.this, MyService.class);
                stopIntent.setAction(Constants.ACTION.STOP_ACTION);
                stopIntent.putExtra("pair", "USD_JPY");
                startService(stopIntent);

            }
        });
        startButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent startIntent = new Intent(MainActivity.this, MyService.class);
                startIntent.setAction(Constants.ACTION.START_ACTION);
                startIntent.putExtra("pair", "GBP_CAD");

                startService(startIntent);
                startButtonTwo.setEnabled(false);
            }
        });
        stopButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopIntent = new Intent(MainActivity.this, MyService.class);
                stopIntent.setAction(Constants.ACTION.STOP_ACTION);
                stopIntent.putExtra("pair", "GBP_CAD");
                startService(stopIntent);
            }
        });

        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getStringExtra("pair").equals("USD_JPY")) {
                    tvprice.setText(intent.getStringExtra("price"));
                    tvup.setText(doubleString(intent.getDoubleExtra("up", 0)));
                    tvdonw.setText(doubleString(intent.getDoubleExtra("down", 0)));
                    tv_time.setText(convertTime(intent.getLongExtra("time", 0)));
                } else {
                    tvpriceTwo.setText(intent.getStringExtra("price"));
                    tvupTwo.setText(doubleString(intent.getDoubleExtra("up", 0)));
                    tvdonwTwo.setText(doubleString(intent.getDoubleExtra("down", 0)));
                    tv_timeTwo.setText(convertTime(intent.getLongExtra("time", 0)));
                }

                // tv_time.setText(intent.getIntExtra("time", 0) + " Seconds");
            }
        };

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

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private String convertTime(long durationSeconds) {
        return String.format("%02d:%02d:%02d", durationSeconds / 3600,
                (durationSeconds % 3600) / 60, (durationSeconds % 60));
    }

    private String doubleString(double input) {
        return String.format("%.5f", input);
    }
}
