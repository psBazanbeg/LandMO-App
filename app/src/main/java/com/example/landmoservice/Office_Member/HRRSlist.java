package com.example.landmoservice.Office_Member;

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

public class HRRSlist extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> name, email, age,RID;
    DBHelper DB;
    MyAdapter_Office adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hrrslist);

        getSupportActionBar().setTitle("Service History");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yeloo)));

        DB = new DBHelper(this);

        name = new ArrayList<>();
        email = new ArrayList<>();
        age = new ArrayList<>();
        RID = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);

        adapter = new MyAdapter_Office(this, name, email, age , RID);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();
    }

    private void displaydata()
    {
        Cursor cursor = DB.getdata_servic_for();
        if(cursor.getCount()==0)
        {
            Toast.makeText(HRRSlist.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
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