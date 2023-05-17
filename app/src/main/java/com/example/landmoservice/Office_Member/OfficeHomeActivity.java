package com.example.landmoservice.Office_Member;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.landmoservice.R;

public class OfficeHomeActivity extends AppCompatActivity {
Button select1,repairbtn,servicbtn,addemp,history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_home);

        getSupportActionBar().hide();

      select1=findViewById(R.id.addCustomer);
      repairbtn=findViewById(R.id.Rshedule);
      servicbtn=findViewById(R.id.servicebtn);
      addemp=findViewById(R.id.addemp);
      history=findViewById(R.id.history);


      select1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent =new Intent(OfficeHomeActivity.this, AddCustomerActivity.class);

              startActivity(intent);
          }
      });

      repairbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent =new Intent(OfficeHomeActivity.this, RRList.class);

              startActivity(intent);
          }
      });

      servicbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent =new Intent(OfficeHomeActivity.this, RRSlist.class);

              startActivity(intent);
          }
      });

      addemp.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent =new Intent(OfficeHomeActivity.this, AddEmployee.class);

              startActivity(intent);
          }
      });

      history.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Intent intent =new Intent(OfficeHomeActivity.this, History.class);
              startActivity(intent);
          }
      });

    }


}