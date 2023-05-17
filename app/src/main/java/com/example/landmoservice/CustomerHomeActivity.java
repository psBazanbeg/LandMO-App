package com.example.landmoservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomerHomeActivity extends AppCompatActivity {
Button rp, view1, view2, sv;
String CID;
    Button userAcc;
TextView login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        Bundle bundle = getIntent().getExtras();
        CID=bundle.getString("CID");

        getSupportActionBar().hide();


        rp=findViewById(R.id.button2);
        sv=findViewById(R.id.button);
        view1 = findViewById(R.id.btnView);
        view2 = findViewById(R.id.button3);
        login=findViewById(R.id.textView31);
        login.setText(CID);
        userAcc= findViewById(R.id.user_account_btn);

        userAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(CustomerHomeActivity.this, editUserProfile.class);
                intent.putExtra("CID", CID);
                startActivity(intent);
            }
        });

        rp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CustomerHomeActivity.this,AddRepairActivity.class);
                intent.putExtra("CID",CID);
                startActivity(intent);
            }
        });

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CustomerHomeActivity.this,RList.class);
                intent.putExtra("CID",CID);
                startActivity(intent);
            }
        });

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CustomerHomeActivity.this,AddServiceActivity.class);
                intent.putExtra("CID",CID);
                startActivity(intent);
            }
        });

        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CustomerHomeActivity.this,RSlist.class);
                intent.putExtra("CID",CID);
                startActivity(intent);
            }
        });

    }
}