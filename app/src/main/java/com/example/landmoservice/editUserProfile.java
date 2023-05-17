package com.example.landmoservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.landmoservice.Office_Member.AddCustomerActivity;
import com.example.landmoservice.Office_Member.RRList;

public class editUserProfile extends AppCompatActivity {

    Button update, cancel;
    EditText NIC,Cname,Cbd,Ccity,Cstate,Cpassword,Cmobile;
    DBHelper DB;

    String nic, name, bday, city, state, ps, mobile;
    String CID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();
        CID=bundle.getString("CID");

        DB = new DBHelper(this);

        update=findViewById(R.id.Updatebtn);
        cancel=findViewById(R.id.cancelbtn);

        NIC=findViewById(R.id.Cnic);
        Cname=findViewById(R.id.Cname);
        Cbd=findViewById(R.id.Cbd);
        Ccity=findViewById(R.id.Ccity);
        Cstate=findViewById(R.id.Cstate);
        Cpassword=findViewById(R.id.Cpassword);
        Cmobile=findViewById(R.id.Cmobile);

        NIC.setEnabled(false);
        Cname.setEnabled(false);
        Cbd.setEnabled(false);

        displaydata(CID);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean checkupdatedata = DB.updatecustomer_byCustomer(
                        NIC.getText().toString()
                        ,Ccity.getText().toString()
                        ,Cstate.getText().toString()
                        ,Cpassword.getText().toString()
                        ,Cmobile.getText().toString());
                if(checkupdatedata==true) {
                    Cmobile.setText("");
                    Cpassword.setText("");
                    Cstate.setText("");
                    NIC.setText("");
                    Cname.setText("");
                    Cbd.setText("");
                    Ccity.setText("");
                    Toast.makeText(editUserProfile.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(editUserProfile.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(editUserProfile.this, "Cleared", Toast.LENGTH_SHORT).show();

                Cmobile.setText("");
                Cpassword.setText("");
                Cstate.setText("");
                NIC.setText("");
                Cname.setText("");
                Cbd.setText("");
                Ccity.setText("");
            }
        });

    }


    private void displaydata(String nic)
    {
        Cursor cursor = DB.getdata_customer_details_usingNIC(nic);
        if(cursor.getCount()==0)
        {
            Toast.makeText(editUserProfile.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while(cursor.moveToNext())
            {
                NIC.setText(cursor.getString(0));
                Cname.setText(cursor.getString(1));
                Cbd.setText(cursor.getString(2));
                Ccity.setText(cursor.getString(3));
                Cstate.setText(cursor.getString(4));
                Cpassword.setText(cursor.getString(5));
                Cmobile.setText(cursor.getString(6));
            }
        }
    }
}