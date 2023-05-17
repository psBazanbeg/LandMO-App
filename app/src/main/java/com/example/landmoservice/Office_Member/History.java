package com.example.landmoservice.Office_Member;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.landmoservice.R;

public class History extends AppCompatActivity {

    Button Rhistory,Shistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().hide();

        Rhistory=findViewById(R.id.Rshedule);
        Shistory=findViewById(R.id.Shistory);

        Rhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(History.this, HRRList.class);
                startActivity(intent);
            }
        });

        Shistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(History.this, HRRSlist.class);
                startActivity(intent);
            }
        });

    }
}