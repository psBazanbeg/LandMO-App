package com.example.landmoservice;

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

public class AcceptedVeiwActivity2 extends AppCompatActivity {
    String RSID;
    TextView textView9,txtvnum,txtname,txtmobil,txtdate,txttime;
    DBHelper DB;
    Button calltch,gmap;
    String txtlocation;
    static int PERMISSION_CODE= 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_veiw2);

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
    }

    private void displaydata(String RID)
    {
        Cursor cursor = DB.Accept_service(RID);
        if(cursor.getCount()==0)
        {
            Toast.makeText(AcceptedVeiwActivity2.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
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
                txtlocation=cursor.getString(5);

            }
        }
    }
}