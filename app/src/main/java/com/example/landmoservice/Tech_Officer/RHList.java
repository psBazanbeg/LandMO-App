package com.example.landmoservice.Tech_Officer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.example.landmoservice.DBHelper;
import com.example.landmoservice.R;

import java.util.ArrayList;

public class RHList extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> name, email, age,RID;
    DBHelper DB;
    MyAdapter_Tech adapter;

    String NIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhlist);



        Bundle bundle = getIntent().getExtras();
        NIC=bundle.getString("CID");

        getSupportActionBar().setTitle("Repair Request");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yeloo)));

        DB = new DBHelper(this);

        name = new ArrayList<>();
        email = new ArrayList<>();
        age = new ArrayList<>();
        RID = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);

        adapter = new MyAdapter_Tech(this, name, email, age , RID);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata(NIC);
    }

    private void displaydata(String NIC)
    {
        Cursor cursor = DB.getdata_repair_for_tech(NIC);
        if(cursor.getCount()==0)
        {
            Toast.makeText(RHList.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while(cursor.moveToNext())
            {
                name.add(cursor.getString(1));
                email.add(cursor.getString(2));
                age.add(cursor.getString(3));
                RID.add(cursor.getString(0));
            }
        }
    }
}