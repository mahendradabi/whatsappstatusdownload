package com.money.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.money.app.adapter.SelectedCurrencyAdapter;

public class SelectedCurrencyActivity extends AppCompatActivity implements SelectedCurrencyAdapter.OnStopListner {
    RecyclerView recyclerView;
    AppCompatButton startNew;
    AppCompatSpinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_currency);
        recyclerView = findViewById(R.id.recyclerView);
        startNew = findViewById(R.id.start_new);
        spinner = findViewById(R.id.spinner);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final SelectedCurrencyAdapter selectedCurrencyAdapter = new SelectedCurrencyAdapter(this);
        selectedCurrencyAdapter.setOnStopListner(this);
        recyclerView.setAdapter(selectedCurrencyAdapter);

        startNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getSelectedItemPosition() > 0) {
                    Intent intent = new Intent(SelectedCurrencyActivity.this, ViewCurrentTradeActivity.class);
                    intent.putExtra("pair", spinner.getSelectedItem().toString());
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "Select Currency", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onStopClick(String currency) {
        Toast.makeText(this, currency, Toast.LENGTH_SHORT).show();
    }
}
