package com.example.landmoservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Currency;

public class RSlist extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> name, email, age ,RID;
    DBHelper DB;
    MyAdapter adapter;
    String CID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rlist);

        Bundle bundle = getIntent().getExtras();

        getSupportActionBar().setTitle("Service Request History");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yeloo)));
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        DB = new DBHelper(this);

        CID=bundle.getString("CID");
        name = new ArrayList<>();
        email = new ArrayList<>();
        age = new ArrayList<>();
        RID = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new MyAdapter(this, name, email, age , RID);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();
    }

    private void displaydata()
    {
        Cursor cursor = DB.getdata_service(CID);
        if(cursor.getCount()==0)
        {
            Toast.makeText(RSlist.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while(cursor.moveToNext())
            {
                name.add(cursor.getString(3));
                email.add(cursor.getString(9));
                age.add(cursor.getString(10));
                RID.add(cursor.getString(1));
            }
        }
    }
}