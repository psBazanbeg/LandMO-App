package com.example.landmoservice.Tech_Officer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landmoservice.AcceptedVeiwActivity2;
import com.example.landmoservice.CustomerHomeActivity;
import com.example.landmoservice.DBHelper;
import com.example.landmoservice.LoginActivity;
import com.example.landmoservice.Office_Member.AddCustomerActivity;
import com.example.landmoservice.Office_Member.OfficeHomeActivity;
import com.example.landmoservice.R;

public class RepairDitails extends AppCompatActivity {
    String RSID;
    TextView textView9,txtvnum,txtname,txtmobil,txtdate,txttime,txtaddress,dis;
    DBHelper DB;
    Button calltch,gmap,confirm;
    String txtlocation;
    static int PERMISSION_CODE= 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_ditails);
        getSupportActionBar().setTitle("Accepted Service Details");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.yeloo)));

        Bundle bundle = getIntent().getExtras();

        DB = new DBHelper(this);

        textView9=findViewById(R.id.textView9);
        txtvnum=findViewById(R.id.txtvnum);
        txtname=findViewById(R.id.txtname);
        txtmobil=findViewById(R.id.txtmobil);
        txtdate=findViewById(R.id.txtdate);
        txttime=findViewById(R.id.txttime);
        txtaddress=findViewById(R.id.editTextTextMultiLine2);
        dis=findViewById(R.id.editTextTextMultiLine);
        confirm=findViewById(R.id.confirm);

        gmap=findViewById(R.id.gmap);

        calltch=findViewById(R.id.calltech);

        RSID=bundle.getString("RSID");

        textView9.setText(RSID);
        displaydata(RSID);

        calltch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneno = txtmobil.getText().toString();
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+phoneno));
                startActivity(i);

            }
        });

        gmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("geo:"+txtlocation));
                startActivity(i);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               DB.update_confirm(
                       "Complete",RSID);

                DB.update_confirmTO(
                        "Complete",RSID);

                Intent intent =new Intent(RepairDitails.this, TechHomeActivity.class);

                startActivity(intent);


            }
        });
    }

    private void displaydata(String RID)
    {
        Cursor cursor = DB.Accept_Repair_dtails(RID);
        if(cursor.getCount()==0)
        {
            Toast.makeText(RepairDitails.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while(cursor.moveToNext())
            {
                txtvnum.setText(cursor.getString(0));
                txtname.setText(cursor.getString(1));
                txtmobil.setText(cursor.getString(2));


                txtdate.setText(cursor.getString(3));
                txttime.setText(cursor.getString(4));
                dis.setText(cursor.getString(5));
                txtaddress.setText(cursor.getString(6));
                txtlocation=cursor.getString(7);

            }
        }
    }
}