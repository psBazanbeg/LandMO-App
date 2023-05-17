package com.example.landmoservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landmoservice.Office_Member.AddCustomerActivity;
import com.example.landmoservice.Office_Member.OfficeHomeActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddRepairActivity extends AppCompatActivity implements LocationListener {
    Button button_location,submit;
    TextView textView_location,link;
    LocationManager locationManager;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    public String RID;

    TextView A,B,C,D,E,F;
    String CID;

    String status="Processing...";
    DBHelper DB;

    String[] items =  {"Excavators",
            "Backhoe",
            "Excavator",
            "Bulldozers",
            "Graders",
            "Wheel Tractor",
            "Trenchers",
            "Loaders",
            "Tower Cranes",
            };
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repair);

        Bundle bundle = getIntent().getExtras();
        calendar = Calendar.getInstance();
        getSupportActionBar().hide();

        autoCompleteTxt = findViewById(R.id.Vtype);
        textView_location = findViewById(R.id.text_location);
        textView_location.setEnabled(false);
        button_location = findViewById(R.id.button_location);
        link = findViewById(R.id.link);
        link.setEnabled(false);
        submit=findViewById(R.id.Sendbtn);



        DB = new DBHelper(this);

        CID=bundle.getString("CID");
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());

        A=findViewById(R.id.Cnic);
        B=findViewById(R.id.Vtype);
        C=findViewById(R.id.vdisc);
        D=findViewById(R.id.Cmobile);
        E=findViewById(R.id.text_location);
        F=findViewById(R.id.link);


        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });


        //Runtime permissions
        if (ContextCompat.checkSelfPermission(AddRepairActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddRepairActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }


        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create method
                getLocation();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Cursor res = DB.repair_Id();

                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){

                    buffer.append(res.getString(1));
                }

                RID=buffer.toString();
                System.out.println(RID);

                  int lenth = RID.length();
                  System.out.println(lenth);
                String txt = RID.substring(0, 2);
                String num = RID.substring(2, lenth);
                int d = Integer.parseInt(num);
                d++;
                String snum = Integer.toString(d);
                RID = txt + snum;

                System.out.println(RID);

                System.out.println(CID+" "
                        +A.getText().toString()+" "
                        +B.getText().toString()+" "
                        +C.getText().toString()+" "
                        +D.getText().toString()+" "
                        +E.getText().toString()+" "
                        +F.getText().toString()+" "
                        +date+" "
                        +status);


                Boolean insertrepair = DB.insert_customer_repair_request( RID
                        ,CID
                        ,A.getText().toString()
                        ,B.getText().toString()
                        ,C.getText().toString()
                        ,D.getText().toString()
                        ,E.getText().toString()
                        ,F.getText().toString()
                        ,date
                        ,status);
                if(insertrepair==true) {
                    Toast.makeText(AddRepairActivity.this, "New Repair Inserted", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(AddRepairActivity.this, RList.class);

                    startActivity(intent);
                }
                else {
                    Toast.makeText(AddRepairActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,AddRepairActivity.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(AddRepairActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            textView_location.setText(address);
            link.setText("https://www.google.com/maps/?q="+location.getLatitude()+","+location.getLongitude());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
