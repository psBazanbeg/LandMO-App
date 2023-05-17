package com.example.landmoservice.Tech_Officer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.landmoservice.CustomerHomeActivity;
import com.example.landmoservice.R;
import com.example.landmoservice.editUserProfile;

public class TechHomeActivity extends AppCompatActivity {
Button Rshedule,workhistory;
    Button userAccount;
    String CID;
    TextView Uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_home);

        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        CID=bundle.getString("CID");

        Rshedule=findViewById(R.id.Rshedule);
        workhistory=findViewById(R.id.rshedule);
        Uid=findViewById(R.id.uid);
        userAccount=findViewById(R.id.techpedit);

        Uid.setText(CID);

        Rshedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(TechHomeActivity.this, RHList.class);
                intent.putExtra("CID", CID);
                startActivity(intent);
            }
        });

        workhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(TechHomeActivity.this, RHList.class);
                intent.putExtra("CID", CID);
                startActivity(intent);
            }
        });

        userAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(TechHomeActivity.this, EditTechOffice.class);
                intent.putExtra("nic",CID);
                startActivity(intent);
            }
        });
    }
}