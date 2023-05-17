package com.example.landmoservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.media.effect.EffectFactory;
import android.os.Bundle;

public class RepairRequestListActivity extends AppCompatActivity {
CardView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_request_list);

        back=findViewById(R.id.backg);


    }
}